# soundcloud_trending
use case of rxjava 2 with MVP design pattern, a crawler to retrieve target user's 20 favorite followers's favorite song

Project:
SoundCloud "Whats hot" android project

Third party libraries:
Okhttp, Rxjava2, ButterKnife, Gson, Fresco

Architecture and overall design: 

The project is MVP based. A core presenter(HotSongPresent) connection networking logic and sorting to UI. By separating view from core model, the project is easy to maintain and develop more feature as the different parts of function are separated and sealed. Here are some detail information： 

1. Asynchronous: Networking calls, Parsing Json, Sorting Logic, and other potential long running operation need to work on separate thread, especially, query target user’s 20 recent followers, and get their favorite songs needs a lot of worker threads to operate to speed performance. Using Rxjava2 is a good fit, cuz with only lambda and its operator, it can easily wrapper different work to different thread and interchange. The logic is a lot of reusable, adding more logic is easy as just to wrap code into single Observable, and use operator to do the work

2. Writing Abstract Logic Class By writing logic into POJO, it is easy for testing code logic in unit testing. Following software design pattern, make the code reusable by build on top of interface. And if the code logic change in the future, just subclass it….
3. Designing MVP Code separation for view and model, make the code more maintainable.

4. Unit Testing Unit testing small pieces of code unit. Automation testing is great for project. In my projects, the unit testing was implementing not very good. For example, the JSON parsings and get target user’s 20 follower’ favorite songs depend too much on networking statues. Should have separated logic from networking dependent result. But didn't have time to write more small pieces of unit testing code.
