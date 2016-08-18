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

package com.ninetyslide.bots.fb.multibot.common;

import com.ninetyslide.libs.botforge.core.BotContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing constants that are used across the whole project.
 *
 * @author Marcello Morena
 */
public final class Constants {

    public final static long LIKE_STICKER_ID = 369239263222822L;

    // EchoBot related constants
    public final static String ECHOBOT_PAGE_ID = "CHANGE_ME_ECHOBOT"; // TODO: Change EchoBot values (CHANGE_ME_ECHOBOT) to real values
    public final static String ECHOBOT_PAGE_ACCESS_TOKEN = "CHANGE_ME_ECHOBOT";
    public final static String ECHOBOT_APP_SECRET_KEY = "CHANGE_ME_ECHOBOT";
    public final static String ECHOBOT_VERIFY_TOKEN = "CHANGE_ME_ECHOBOT";
    public final static String ECHOBOT_WEBHOOK_URL = "CHANGE_ME_ECHOBOT";

    public final static BotContext echoBotContext = new BotContext(
            ECHOBOT_PAGE_ID,
            ECHOBOT_PAGE_ACCESS_TOKEN,
            ECHOBOT_APP_SECRET_KEY,
            ECHOBOT_VERIFY_TOKEN,
            ECHOBOT_WEBHOOK_URL
    );

    // HelloBot related constants
    public final static String HELLOBOT_PAGE_ID = "CHANGE_ME_HELLOBOT"; // TODO: Change HelloBot values (CHANGE_ME_HELLOBOT) to real values
    public final static String HELLOBOT_PAGE_ACCESS_TOKEN = "CHANGE_ME_HELLOBOT";
    public final static String HELLOBOT_APP_SECRET_KEY = "CHANGE_ME_HELLOBOT";
    public final static String HELLOBOT_VERIFY_TOKEN = "CHANGE_ME_HELLOBOT";
    public final static String HELLOBOT_WEBHOOK_URL = "CHANGE_ME_HELLOBOT";

    public final static BotContext helloBotContext = new BotContext(
            HELLOBOT_PAGE_ID,
            HELLOBOT_PAGE_ACCESS_TOKEN,
            HELLOBOT_APP_SECRET_KEY,
            HELLOBOT_VERIFY_TOKEN,
            HELLOBOT_WEBHOOK_URL
    );

    // AlarmClockBot related constants
    public final static String ALARMCLOCKBOT_PAGE_ID = "CHANGE_ME_ALARMCLOCKBOT"; // TODO: Change AlarmClockBot values (CHANGE_ME_ALARMCLOCKBOT) to real values
    public final static String ALARMCLOCKBOT_PAGE_ACCESS_TOKEN = "CHANGE_ME_ALARMCLOCKBOT";
    public final static String ALARMCLOCKBOT_APP_SECRET_KEY = "CHANGE_ME_ALARMCLOCKBOT";
    public final static String ALARMCLOCKBOT_VERIFY_TOKEN = "CHANGE_ME_ALARMCLOCKBOT";
    public final static String ALARMCLOCKBOT_WEBHOOK_URL = "CHANGE_ME_ALARMCLOCKBOT";

    public final static BotContext alarmClockBotContext = new BotContext(
            ALARMCLOCKBOT_PAGE_ID,
            ALARMCLOCKBOT_PAGE_ACCESS_TOKEN,
            ALARMCLOCKBOT_APP_SECRET_KEY,
            ALARMCLOCKBOT_VERIFY_TOKEN,
            ALARMCLOCKBOT_WEBHOOK_URL
    );

    // BotContext related constants
    public final static List<BotContext> BOT_CONTEXTS;

    static {
        List<BotContext> tmpList = new ArrayList<>();
        tmpList.add(echoBotContext);
        tmpList.add(helloBotContext);
        tmpList.add(alarmClockBotContext);
        BOT_CONTEXTS = tmpList;
    }

    private Constants() {
    }

}
