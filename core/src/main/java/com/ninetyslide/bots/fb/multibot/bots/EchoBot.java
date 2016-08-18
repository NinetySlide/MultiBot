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
import com.ninetyslide.libs.botforge.FbBot;
import com.ninetyslide.libs.botforge.core.BotContext;
import com.ninetyslide.libs.botforge.core.message.incoming.IncomingTextMessage;
import com.ninetyslide.libs.botforge.core.message.incoming.Postback;
import com.ninetyslide.libs.botforge.core.message.incoming.ReceivedMessage;
import com.ninetyslide.libs.botforge.core.message.outgoing.OutgoingMessage;
import com.ninetyslide.libs.botforge.core.message.outgoing.response.SendMessageError;
import com.ninetyslide.libs.botforge.core.message.outgoing.response.SendMessageResponse;
import com.ninetyslide.libs.botforge.core.message.outgoing.response.SendMessageSuccess;
import com.ninetyslide.libs.botforge.core.message.outgoing.widget.Bubble;
import com.ninetyslide.libs.botforge.core.message.outgoing.widget.Button;
import com.ninetyslide.libs.botforge.core.message.outgoing.widget.QuickReply;
import com.ninetyslide.libs.botforge.util.MessageConverter;

import java.util.logging.Logger;

import static com.ninetyslide.bots.fb.multibot.utils.StringUtils.startsWithIgnoreCase;
import static com.ninetyslide.libs.botforge.adapter.SendMessageAdapter.sendAction;
import static com.ninetyslide.libs.botforge.adapter.SendMessageAdapter.sendImageMessage;
import static com.ninetyslide.libs.botforge.adapter.SendMessageAdapter.sendMessage;
import static com.ninetyslide.libs.botforge.adapter.SendMessageAdapter.sendTextMessage;

/**
 * Class implementing an EchoBot.
 *
 * @author Marcello Morena
 */
public class EchoBot {

    /*
    Use this command to set the Greeting Text

    curl -X POST -H "Content-Type: application/json" -d '{
      "setting_type":"greeting",
      "greeting":{
        "text":"I am EchoBot, part of the MultiBot family. If you tell me something, I\'ll tell you back. Try me! And if you like me, tell me so :D"
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
        },
        {
          "type":"postback",
          "title":"Sample Buttons",
          "payload":"button_template"
        },
        {
          "type":"postback",
          "title":"Sample Bubbles",
          "payload":"generic_template"
        },
        {
          "type":"postback",
          "title":"Sample Image",
          "payload":"image"
        }
      ]
    }' "https://graph.facebook.com/v2.6/me/thread_settings?access_token=PAGE_ACCESS_TOKEN"
     */

    private static final Logger log = Logger.getLogger(FbBot.class.getName());

    // Received messages related constants
    private final static String RECEIVED_LIKE_MESSAGE = "i like you";
    private final static String RECEIVED_HELP_MESSAGE = "help";

    // Quick replies related constants
    private final static String QUICK_REPLY_YEAH_TITLE = "Yeah! I did!";
    private final static String QUICK_REPLY_YEAH_PAYLOAD = "yeah";
    private final static String QUICK_REPLY_NOPE_TITLE = "Nope!";
    private final static String QUICK_REPLY_NOPE_PAYLOAD = "nope";
    private final static String QUICK_REPLY_BUTTON_TITLE = "Show me buttons";
    private final static String QUICK_REPLY_BUTTON_PAYLOAD = "button";
    private final static String QUICK_REPLY_GENERIC_TITLE = "Show me bubbles";
    private final static String QUICK_REPLY_GENERIC_PAYLOAD = "generic";
    private final static String QUICK_REPLY_IMAGE_TITLE = "Show me an image";
    private final static String QUICK_REPLY_IMAGE_PAYLOAD = "image";

    // Bubbles related constants
    private final static String BUBBLE_1_TITLE = "First Bubble";
    private final static String BUBBLE_2_TITLE = "Second Bubble";
    private final static String BUBBLE_SUBTITLE = "Just a boring bubble. But... isn't it funnier when YOU do all the talking? :D";
    private final static String BUBBLE_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bubble_Ring_in_Sunlight.JPG/250px-Bubble_Ring_in_Sunlight.JPG";

    // Buttons related constants
    private final static String BUTTON_TEMPLATE_TITLE = "Mmm... Ok, if you wish so. But this is just a boring button template. Isn't it funnier when YOU do all the talking? :D";
    private final static String BUTTON_SOFUNNY_TITLE = "Ah! So funny!";
    private final static String BUTTON_SOFUNNY_TEXT_REPLY = "Thanks! You're not bad too :*";
    private final static String BUTTON_YOURERIGHT_TITLE = "Maybe you're right";
    private final static String BUTTON_YOURERIGHT_TEXT_REPLY = "Thanks... I guess...";
    private final static String BUTTON_HELP_TEMPLATE_TITLE = "I am EchoBot, part of the MultiBot family, a showcase implementation made using the BotForge framework. If you tell me something, I'll tell you back. Try me! And if you like me, tell me so :D";
    private final static String BUTTON_BOTFORGE_TITLE = "BotForge on GitHub";
    private final static String BUTTON_BOTFORGE_URL = "https://github.com/NinetySlide/BotForge";
    private final static String BUTTON_MULTIBOT_TITLE = "MultiBot on GitHub";
    private final static String BUTTON_MULTIBOT_URL = "https://github.com/NinetySlide/MultiBot";

    // Image related constants
    private final static String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Peace_sign.svg/220px-Peace_sign.svg.png";

    // Postbacks related constants
    private final static String POSTBACK_PAYLOAD_HELP = "help";
    private final static String POSTBACK_PAYLOAD_BUTTON_SOFUNNY = "sofunny";
    private final static String POSTBACK_PAYLOAD_BUTTON_YOURERIGHT = "youreright";
    private final static String POSTBACK_PAYLOAD_BUTTON_TEMPLATE = "button_template";
    private final static String POSTBACK_PAYLOAD_GENERIC_TEMPLATE = "generic_template";
    private final static String POSTBACK_PAYLOAD_IMAGE = "image";
    private final static String POSTBACK_PAYLOAD_GET_STARTED = "get_started";

    // Responses related constants
    private final static String RESPONSE_LIKE_MESSAGE = "Thanks, I like you too, but... you can do better ;)";
    private final static String RESPONSE_LIKE_BUTTON_MESSAGE = "Thanks mate, I like you too! ;)";
    private final static String RESPONSE_QUICK_REPLY_YEAH = "Great! I knew I heard you say that!";
    private final static String RESPONSE_QUICK_REPLY_NOPE = "Oh! Sorry... my bad...";
    private final static String RESPONSE_ALL_PREFIX = "Sorry, did you just say...";
    private final static String RESPONSE_ALL_SUFFIX = "by any chance?";
    private final static String RESPONSE_UNSUPPORTED_MESSAGE = "Sorry, that message is not supported... :(";

    /**
     * Handle the messages received from the user.
     *
     * @param context The context of EchoBot.
     * @param message The message received from the user.
     */
    public static void onMessageReceived(BotContext context, ReceivedMessage message) {

        String userId = message.getSenderId();

        if (message.getIncomingMessageType() == ReceivedMessage.IncomingMessageType.TEXT) {

            IncomingTextMessage incomingText = (IncomingTextMessage) message;
            String messageText = incomingText.getText();

            // Handle quick replies
            if (incomingText.hasQuickReply()) {
                String quickReplyPayload = incomingText.getQuickReply();

                switch (quickReplyPayload) {
                    // Handle "yeah" quick reply
                    case QUICK_REPLY_YEAH_PAYLOAD:
                        sendTextMessage(context, RESPONSE_QUICK_REPLY_YEAH, userId);
                        return;

                    // Handle "nope" quick reply
                    case QUICK_REPLY_NOPE_PAYLOAD:
                        sendTextMessage(context, RESPONSE_QUICK_REPLY_NOPE, userId);
                        return;

                    // Handle "button template" quick reply
                    case QUICK_REPLY_BUTTON_PAYLOAD:
                        sendSampleButtonTemplate(context, userId);
                        return;

                    // Handle "generic template" quick reply
                    case QUICK_REPLY_GENERIC_PAYLOAD:
                        sendSampleGenericTemplate(context, userId);
                        return;

                    // Handle "image" quick reply
                    case QUICK_REPLY_IMAGE_PAYLOAD:
                        sendSampleImage(context, userId);
                        return;

                    // Handle unsupported quick reply
                    default:
                        sendTextMessage(context, RESPONSE_UNSUPPORTED_MESSAGE, userId);
                        return;
                }

            }

            // Respond to I like you message
            if (isLikeMessage(messageText)) {
                sendTextMessage(context, RESPONSE_LIKE_MESSAGE, userId);
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
            sendTextMessage(context, RESPONSE_LIKE_BUTTON_MESSAGE, userId);
            return;
        }

        // Forward received messages
        forwardMessage(context, message, userId);

    }

    /**
     * Handle the postbacks received from the user.
     *
     * @param context The context of EchoBot.
     * @param message The postback received from the user.
     */
    public static void onPostbackReceived(BotContext context, Postback message) {

        String userId = message.getSenderId();

        switch (message.getPayload()) {
            // Handle Help postback
            case POSTBACK_PAYLOAD_HELP:
                sendHelpMessage(context, userId);
                break;

            // Handle "so funny" postback button
            case POSTBACK_PAYLOAD_BUTTON_SOFUNNY:
                sendTextMessage(context, BUTTON_SOFUNNY_TEXT_REPLY, userId);
                break;

            // Handle "you're right" postback button
            case POSTBACK_PAYLOAD_BUTTON_YOURERIGHT:
                sendTextMessage(context, BUTTON_YOURERIGHT_TEXT_REPLY, userId);
                break;

            // Handle button template postback
            case POSTBACK_PAYLOAD_BUTTON_TEMPLATE:
                sendSampleButtonTemplate(context, userId);
                break;

            // Handle generic template postback
            case POSTBACK_PAYLOAD_GENERIC_TEMPLATE:
                sendSampleGenericTemplate(context, userId);
                break;

            // Handle image postback
            case POSTBACK_PAYLOAD_IMAGE:
                sendSampleImage(context, userId);
                break;

            // Handle the get started button
            case POSTBACK_PAYLOAD_GET_STARTED:
                sendHelpMessage(context, userId);
                break;

            // Handle default postback
            default:
                sendTextMessage(context, RESPONSE_UNSUPPORTED_MESSAGE, userId);
                break;
        }

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
     * Create and send a new sample button template message.
     *
     * @param context The context of the bot.
     * @param userId The User ID of the user that sent the message.
     */
    private static void sendSampleButtonTemplate(BotContext context, String userId) {
        OutgoingMessage.OutgoingRecipient recipient = new OutgoingMessage.OutgoingRecipient(null, userId);

        Button sofunny = new Button(Button.ButtonType.POSTBACK)
                .setTitle(BUTTON_SOFUNNY_TITLE)
                .setPayload(POSTBACK_PAYLOAD_BUTTON_SOFUNNY);

        Button youreright = new Button(Button.ButtonType.POSTBACK)
                .setTitle(BUTTON_YOURERIGHT_TITLE)
                .setPayload(POSTBACK_PAYLOAD_BUTTON_YOURERIGHT);

        OutgoingMessage.Builder builder = new OutgoingMessage.Builder(OutgoingMessage.OutgoingMessageType.TEMPLATE_BUTTON)
                .setText(BUTTON_TEMPLATE_TITLE)
                .addButton(sofunny)
                .addButton(youreright);

        SendMessageResponse response = sendMessage(context, builder.build(), recipient);

        if (response.hasErrors()) {
            SendMessageError error = (SendMessageError) response;
            log.info("Error Code: " + String.valueOf(error.getCode()));
            log.info("Error Type: " + error.getType());
            log.info("Error Message: " + error.getMessage());
        } else {
            SendMessageSuccess success = (SendMessageSuccess) response;
            log.info("Message ID: " + success.getMessageId());
            log.info("Recipient ID: " + success.getRecipientId());
        }

    }

    /**
     * Create and send a new sample generic template message.
     *
     * @param context The context of the bot.
     * @param userId The User ID of the user that sent the message.
     */
    private static void sendSampleGenericTemplate(BotContext context, String userId) {
        OutgoingMessage.OutgoingRecipient recipient = new OutgoingMessage.OutgoingRecipient(null, userId);

        Button sofunny = new Button(Button.ButtonType.POSTBACK)
                .setTitle(BUTTON_SOFUNNY_TITLE)
                .setPayload(POSTBACK_PAYLOAD_BUTTON_SOFUNNY);

        Button youreright = new Button(Button.ButtonType.POSTBACK)
                .setTitle(BUTTON_YOURERIGHT_TITLE)
                .setPayload(POSTBACK_PAYLOAD_BUTTON_YOURERIGHT);

        Bubble bubbleOne = new Bubble()
                .setTitle(BUBBLE_1_TITLE)
                .setSubtitle(BUBBLE_SUBTITLE)
                .setImageUrl(BUBBLE_IMAGE_URL)
                .addButton(sofunny)
                .addButton(youreright);

        Bubble bubbleTwo = new Bubble()
                .setTitle(BUBBLE_2_TITLE)
                .setSubtitle(BUBBLE_SUBTITLE)
                .setImageUrl(BUBBLE_IMAGE_URL)
                .addButton(sofunny)
                .addButton(youreright);

        OutgoingMessage.Builder builder = new OutgoingMessage.Builder(OutgoingMessage.OutgoingMessageType.TEMPLATE_GENERIC)
                .addBubble(bubbleOne)
                .addBubble(bubbleTwo);

        SendMessageResponse response = sendMessage(context, builder.build(), recipient);

        if (response.hasErrors()) {
            SendMessageError error = (SendMessageError) response;
            log.info("Error Code: " + String.valueOf(error.getCode()));
            log.info("Error Type: " + error.getType());
            log.info("Error Message: " + error.getMessage());
        } else {
            SendMessageSuccess success = (SendMessageSuccess) response;
            log.info("Message ID: " + success.getMessageId());
            log.info("Recipient ID: " + success.getRecipientId());
        }

    }

    /**
     * Create and send a new sample generic template message.
     *
     * @param context The context of the bot.
     * @param userId The User ID of the user that sent the message.
     */
    private static void sendSampleImage(BotContext context, String userId) {
        sendImageMessage(context, IMAGE_URL, userId);
    }

    /**
     * Forward received messages with sender action and quick replies
     *
     * @param context The context of the bot.
     * @param userId The User ID of the user that sent the message.
     */
    private static void forwardMessage(BotContext context, ReceivedMessage originalMessage, String userId) {
        OutgoingMessage.OutgoingRecipient recipient = new OutgoingMessage.OutgoingRecipient(null, userId);

        // Send the typing action
        sendAction(context, OutgoingMessage.SenderAction.TYPING_ON, userId);

        // Send the prefix of the forwarded message
        sendTextMessage(context, RESPONSE_ALL_PREFIX, userId);

        // Forward the message
        try {
            OutgoingMessage forwardedMessage = MessageConverter.getOutgoingMessage(originalMessage);
            sendMessage(context, forwardedMessage, recipient);
        } catch (IllegalArgumentException e) {
            sendTextMessage(context, RESPONSE_UNSUPPORTED_MESSAGE, userId);
        }

        // Send the suffix of the forwarded message together with the quick replies
        OutgoingMessage.Builder builder = new OutgoingMessage.Builder(OutgoingMessage.OutgoingMessageType.TEXT)
                .setText(RESPONSE_ALL_SUFFIX)
                .addQuickReply(new QuickReply().setTitle(QUICK_REPLY_YEAH_TITLE).setPayload(QUICK_REPLY_YEAH_PAYLOAD))
                .addQuickReply(new QuickReply().setTitle(QUICK_REPLY_NOPE_TITLE).setPayload(QUICK_REPLY_NOPE_PAYLOAD))
                .addQuickReply(new QuickReply().setTitle(QUICK_REPLY_IMAGE_TITLE).setPayload(QUICK_REPLY_IMAGE_PAYLOAD))
                .addQuickReply(new QuickReply().setTitle(QUICK_REPLY_BUTTON_TITLE).setPayload(QUICK_REPLY_BUTTON_PAYLOAD))
                .addQuickReply(new QuickReply().setTitle(QUICK_REPLY_GENERIC_TITLE).setPayload(QUICK_REPLY_GENERIC_PAYLOAD));

        SendMessageResponse response = sendMessage(context, builder.build(), recipient);

        if (response.hasErrors()) {
            SendMessageError error = (SendMessageError) response;
            log.info("Error Code: " + String.valueOf(error.getCode()));
            log.info("Error Type: " + error.getType());
            log.info("Error Message: " + error.getMessage());
        } else {
            SendMessageSuccess success = (SendMessageSuccess) response;
            log.info("Message ID: " + success.getMessageId());
            log.info("Recipient ID: " + success.getRecipientId());
        }

    }

}
