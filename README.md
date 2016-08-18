# MultiBot (Powered by BotForge)

MultiBot is a showcase software for the BotForge framework. Click [here](https://github.com/NinetySlide/BotForge) to visit the BotForge repository on GitHub.

MultiBot is a software that impersonate three different Facebook Messenger Bots. Here is a summary of the bots:

* **HelloBot**: A basic Hello World Bot. It is specialized in greetings.

* **EchoBot**: A Bot that sends back everything is sent to it. It also shows some sample templates.

* **AlarmClockBot**: A Bot that works as an alarm clock (*Yet to be developed*).

## Bot Interactions

### HelloBot

* A user can type (case insensitive) “Hello”, “Hi”, “Good morning”, “Good afternoon” or “Good evening”, “Good Night” to the Bot; the Bot will reply with “Hello World! Hello $Username!”.

* A user can type (case insensitive) “I like you”; the Bot will reply with “Thanks, I like you too, but… you can do better ;)”.

* Any other message will be answered by “Sorry, I only know how to say hello :(”.

* A user can press the like button and receive the message “Thanks mate, I like you too! ;)”.

* A user can type (case insensitive) “Help” and receive instructions on how to use the bot.

* The menu item for this bot is “Help”. The Help says: “I am HelloBot, part of the MultiBot family. I only know how to say hello. If you like me, tell me so :D”.

### EchoBot

* A user can send every message, including media attachment messages and the Bot will send them back, preceding them with a typing sender action, a text message that says “Sorry, did you just say…” and following them with a text message that says “by any chance?”.

* Any message that is not supported will be replied with “Sorry, I didn’t understand that kind of message”.

* A user can type (case insensitive) “I like you”; the Bot will reply with “Thanks, I like you too, but… you can do better ;)”.

* A user can press the like button and receive the message “Thanks mate, I like you too! ;)”.

* A user can type (case insensitive) “Help” and receive instructions on how to use the bot.

* After each reply message, the user is presented with some quick replies. In particular there will be the “Yeah! I did!”, “Show me an image”, “Show me a button template” and “Show me a generic template” quick replies.

* If the “Yeah! I did!” quick reply is clicked, the bot says “Great! I knew I heard you say that!”.

* If the “Show me a button template” quick reply is clicked, the Bot sends a button template message with title “Mmm… Ok, if you wish so. But this is just a boring button template. Isn’t it funnier when YOU do all the talking? :D” and buttons “Maybe you’re right” and “Ah! You’re so funny!”. The former is replied with “Thanks… I guess...” and the latter is replied with “Thanks! You’re not bad too :*”.

* If the “Show me a generic template” quick reply is clicked, the Bot sends a generic template message with two bubbles. The first one has “First Bubble” as a title, “This is just a boring bubble. But… isn’t it funnier when YOU do all the talking? :D” as a subtitle, a bubble image from wikipedia, one button “Maybe you’re right” and another button “Ah! You’re so funny!”. The second one has “Second Bubble” as a title, “This is just a boring bubble. But… isn’t it funnier when YOU do all the talking? :D” as a subtitle, a bubble image from wikipedia, one button “Maybe you’re right” and another button “Ah! You’re so funny!”.

* If the “Show me an image” quick reply is clicked, the Bot sends an image message containing a peace symbol taken from wikipedia.

* The menu items for this bot are “Help”, "Sample Buttons", "Sample Bubbles", "Sample Image". The Help says “I am EchoBot, part of the MultiBot family. If you tell me something, I’ll tell you back. Try me! And if you like me, tell me so :D”. The other menu items shows the same contents of the quick replies.

### AlarmClockBot

>***This bot is yet to be developed***

## Authors, Contributions and Contacts

As the licence reads, this is free software released by NinetySlide under the Apache License Version 2.0. The author (Marcello Morena) built this project as a showcase for the BotForge framework, so no further developments are planned and no external contributions are needed. However, you are still welcome to fork and star this project on GitHub. For everything else you can just mail us at opensource[at]ninetyslide[dot]com.