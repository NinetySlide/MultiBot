/*
 * Copyright 2016 NinetySlide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ninetyslide.bots.fb.multibot.bots;

import com.ninetyslide.bots.fb.multibot.common.Constants;
import com.ninetyslide.libs.botforge.adapter.SendMessageAdapter;
import com.ninetyslide.libs.botforge.adapter.UserProfileApiAdapter;
import com.ninetyslide.libs.botforge.core.BotContext;
import com.ninetyslide.libs.botforge.core.message.incoming.IncomingTextMessage;
import com.ninetyslide.libs.botforge.core.message.incoming.Postback;
import com.ninetyslide.libs.botforge.core.message.incoming.ReceivedMessage;
import com.ninetyslide.libs.botforge.core.message.outgoing.OutgoingMessage;
import com.ninetyslide.libs.botforge.core.message.outgoing.widget.Button;

import static com.ninetyslide.bots.fb.multibot.utils.StringUtils.*;
import static com.ninetyslide.libs.botforge.adapter.SendMessageAdapter.sendMessage;
import static com.ninetyslide.libs.botforge.adapter.SendMessageAdapter.sendTextMessage;

/**
 * Class implementing a HelloBot.
 *
 * @author Marcello Morena
 */
public class HelloBot {

    /*
    Use this command to set the Greeting Text

    curl -X POST -H "Content-Type: application/json" -d '{
      "setting_type":"greeting",
      "greeting":{
        "text":"I am HelloBot, part of the MultiBot family. I only know how to say hello. If you like me, tell me so :D"
      }
    }' "https://graph.facebook.com/v2.6/me/thread_settings?access_token=PAGE_ACCESS_TOKEN"
     */

    /*
    Use this command to set the Get Started Button

    curl -X POST -H "Content-Type: application/json" -d '{
      "setting_type":"call_to_actions",
      "thread_state":"new_thread",
      "call_to_actions":[
        {
          "payload":"get_started"
        }
      ]
    }' "https://graph.facebook.com/v2.6/me/thread_settings?access_token=PAGE_ACCESS_TOKEN"
     */

    /*
    Use this command to set the persistent menu:

    curl -X POST -H "Content-Type: application/json" -d '{
      "setting_type" : "call_to_actions",
      "thread_state" : "existing_thread",
      "call_to_actions":[
        {
          "type":"postback",
          "title":"Help",
          "payload":"help"
        }
      ]
    }' "https://graph.facebook.com/v2.6/me/thread_settings?access_token=PAGE_ACCESS_TOKEN"
     */

    // Received messages related constants
    private final static String[] RECEIVED_GREETING_MESSAGES = { "hello", "hi", "good morning", "good afternoon", "good evening", "good night" };
    private final static String RECEIVED_LIKE_MESSAGE = "i like you";
    private final static String RECEIVED_HELP_MESSAGE = "help";

    // Help related constants
    private final static String BUTTON_HELP_TEMPLATE_TITLE = "I am HelloBot, part of the MultiBot family, a showcase implementation made using the BotForge framework. I only know how to say hello. If you like me, tell me so :D";
    private final static String BUTTON_BOTFORGE_TITLE = "BotForge on GitHub";
    private final static String BUTTON_BOTFORGE_URL = "https://github.com/NinetySlide/BotForge";
    private final static String BUTTON_MULTIBOT_TITLE = "MultiBot on GitHub";
    private final static String BUTTON_MULTIBOT_URL = "https://github.com/NinetySlide/MultiBot";

    // Responses related constants
    private final static String RESPONSE_UNKNOWN_MESSAGE = "Sorry, I only know how to say hello :(";
    private final static String RESPONSE_GREETING_TEMPLATE_MESSAGE = "Hello World! Hello %s!";
    private final static String RESPONSE_LIKE_MESSAGE = "Thanks, I like you too, but... you can do better ;)";
    private final static String RESPONSE_LIKE_BUTTON_MESSAGE = "Thanks mate, I like you too! ;)";

    // Postbacks related constants
    private final static String POSTBACK_PAYLOAD_HELP = "help";
    private final static String POSTBACK_PAYLOAD_GET_STARTED = "get_started";

    /**
     * Handle the messages received from the user.
     *
     * @param context The context of HelloBot.
     * @param message The message received from the user.
     */
    public static void onMessageReceived(BotContext context, ReceivedMessage message) {

        String userId = message.getSenderId();

        if (message.getIncomingMessageType() == ReceivedMessage.IncomingMessageType.TEXT) {

            String messageText = ((IncomingTextMessage) message).getText();

            // Respond to Greeting message
            if (isGreetingMessage(messageText)) {
                SendMessageAdapter.sendTextMessage(context, getGreetingMessage(context, userId), userId);
                return;
            }

            // Respond to I like you message
            if (isLikeMessage(messageText)) {
                SendMessageAdapter.sendTextMessage(context, RESPONSE_LIKE_MESSAGE, userId);
                return;
            }

            // Respond to Help message
            if (isHelpMessage(messageText)) {
                sendHelpMessage(context, userId);
                return;
            }

        }

        // Respond to Like button message
        if (isLikeButtonMessage(message)) {
            SendMessageAdapter.sendTextMessage(context, RESPONSE_LIKE_BUTTON_MESSAGE, userId);
            return;
        }

        // Respond to Unknown message
        SendMessageAdapter.sendTextMessage(context, RESPONSE_UNKNOWN_MESSAGE, userId);

    }

    /**
     * Handle the postbacks received from the user.
     *
     * @param context The context of HelloBot.
     * @param message The postback received from the user.
     */
    public static void onPostbackReceived(BotContext context, Postback message) {
        String userId = message.getSenderId();

        switch (message.getPayload()) {
            // Handle Help postback
            case POSTBACK_PAYLOAD_HELP:
                sendHelpMessage(context, userId);
                break;

            // Handle the get started button
            case POSTBACK_PAYLOAD_GET_STARTED:
                sendHelpMessage(context, userId);
                break;

            // Handle default postback
            default:
                sendTextMessage(context, RESPONSE_UNKNOWN_MESSAGE, userId);
                break;
        }
    }

    /**
     * Check whether the message passed as argument is a greeting.
     *
     * @param text The message to check for greeting.
     * @return True if the message is a greeting, false otherwise.
     */
    private static boolean isGreetingMessage(String text) {
        for (String prefix : RECEIVED_GREETING_MESSAGES) {
            if (startsWithIgnoreCase(text, prefix)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check whether the message contains a like message.
     *
     * @param text The message to check for like message.
     * @return True if the message is a like message, false otherwise.
     */
    private static boolean isLikeMessage(String text) {
        return startsWithIgnoreCase(text, RECEIVED_LIKE_MESSAGE);
    }

    /**
     * Check whether the message contains a help message.
     *
     * @param text The message to check for help message.
     * @return True if the message is a help message, false otherwise.
     */
    private static boolean isHelpMessage(String text) {
        return startsWithIgnoreCase(text, RECEIVED_HELP_MESSAGE);
    }

    /**
     * Check whether the message contains a like button.
     *
     * @param message The message to check for like button.
     * @return True if the message is a like button, false otherwise.
     */
    private static boolean isLikeButtonMessage(ReceivedMessage message) {
        return message.getIncomingMessageType() == ReceivedMessage.IncomingMessageType.ATTACHMENT &&
                message.getStickerId() == Constants.LIKE_STICKER_ID;
    }

    /**
     * Send a message in response to help messages.
     *
     * @param context The context of the bot.
     * @param userId The User ID of the user that sent the message.
     */
    private static void sendHelpMessage(BotContext context, String userId) {
        OutgoingMessage.OutgoingRecipient recipient = new OutgoingMessage.OutgoingRecipient(null, userId);

        Button botForge = new Button(Button.ButtonType.WEB_URL)
                .setTitle(BUTTON_BOTFORGE_TITLE)
                .setUrl(BUTTON_BOTFORGE_URL);

        Button multiBot = new Button(Button.ButtonType.WEB_URL)
                .setTitle(BUTTON_MULTIBOT_TITLE)
                .setUrl(BUTTON_MULTIBOT_URL);

        OutgoingMessage message = new OutgoingMessage.Builder(OutgoingMessage.OutgoingMessageType.TEMPLATE_BUTTON)
                .setText(BUTTON_HELP_TEMPLATE_TITLE)
                .addButton(botForge)
                .addButton(multiBot)
                .build();

        sendMessage(context, message, recipient);
    }

    /**
     * Create a string to respond to greeting messages.
     *
     * @param context The context of the bot.
     * @param userId The User ID of the user that sent the message.
     * @return A string to respond to greeting messages.
     */
    private static String getGreetingMessage(BotContext context, String userId) {
        String userFirstName = UserProfileApiAdapter.getUserProfile(context, userId).getFirstName();
        return String.format(RESPONSE_GREETING_TEMPLATE_MESSAGE, userFirstName);
    }

}
