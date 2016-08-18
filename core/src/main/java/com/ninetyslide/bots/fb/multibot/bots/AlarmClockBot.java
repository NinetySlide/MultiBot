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

import com.ninetyslide.libs.botforge.adapter.SendMessageAdapter;
import com.ninetyslide.libs.botforge.core.BotContext;
import com.ninetyslide.libs.botforge.core.message.incoming.Postback;
import com.ninetyslide.libs.botforge.core.message.incoming.ReceivedMessage;

/**
 * Class implementing an AlarmClockBot.
 *
 * @author Marcello Morena
 */
public class AlarmClockBot {

    /**
     * Handle the messages received from the user.
     *
     * @param context The context of AlarmClockBot.
     * @param message The message received from the user.
     */
    public static void onMessageReceived(BotContext context, ReceivedMessage message) {
        // TODO: Add implementation!
        SendMessageAdapter.sendTextMessage(
                context,
                "I'm not working yet, please wait for me to be developed.",
                message.getSenderId()
        );
    }

    /**
     * Handle the postbacks received from the user.
     *
     * @param context The context of AlarmClockBot.
     * @param message The postback received from the user.
     */
    public static void onPostbackReceived(BotContext context, Postback message) {
        // TODO: Add implementation!
        SendMessageAdapter.sendTextMessage(
                context,
                "I'm not working yet, please wait for me to be developed.",
                message.getSenderId()
        );
    }

}
