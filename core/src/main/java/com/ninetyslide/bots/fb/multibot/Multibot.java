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

package com.ninetyslide.bots.fb.multibot;

import com.ninetyslide.bots.fb.multibot.bots.AlarmClockBot;
import com.ninetyslide.bots.fb.multibot.bots.EchoBot;
import com.ninetyslide.bots.fb.multibot.bots.HelloBot;
import com.ninetyslide.bots.fb.multibot.common.Constants;
import com.ninetyslide.libs.botforge.FbBot;
import com.ninetyslide.libs.botforge.core.BotContext;
import com.ninetyslide.libs.botforge.core.message.incoming.Postback;
import com.ninetyslide.libs.botforge.core.message.incoming.ReceivedMessage;

import java.util.List;

/**
 * Main class for the MultiBot project.
 */
public class Multibot extends FbBot {

    @Override
    protected BotContext onContextLoad(String pageId, String webhookUrl) {
        // Bot Contexts are loaded in the botInit() method, so just return null here
        return null;
    }

    @Override
    protected List<BotContext> botInit() {
        // Return the list of Bot Contexts
        return Constants.BOT_CONTEXTS;
    }

    @Override
    protected void onMessageReceived(BotContext context, ReceivedMessage message) {

        switch (context.getWebhookUrl()) {
            case Constants.HELLOBOT_WEBHOOK_URL:
                HelloBot.onMessageReceived(context, message);
                break;
            case Constants.ECHOBOT_WEBHOOK_URL:
                EchoBot.onMessageReceived(context, message);
                break;
            case Constants.ALARMCLOCKBOT_WEBHOOK_URL:
                AlarmClockBot.onMessageReceived(context, message);
        }

    }

    @Override
    protected void onPostbackReceived(BotContext context, Postback message) {

        switch (context.getWebhookUrl()) {
            case Constants.HELLOBOT_WEBHOOK_URL:
                HelloBot.onPostbackReceived(context, message);
                break;
            case Constants.ECHOBOT_WEBHOOK_URL:
                EchoBot.onPostbackReceived(context, message);
                break;
            case Constants.ALARMCLOCKBOT_WEBHOOK_URL:
                AlarmClockBot.onPostbackReceived(context, message);
        }

    }
}
