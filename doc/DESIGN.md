## Names:  
Pierce Forte, Ben Burnett, Nicole Lindbergh, Jerry Huang

## Roles:
- Pierce
    - Pierce's primary role was, initially, to work on the APIs and implementations for backend game elements. 
    - After completing this work, he switched his focus to the builder stage, which he tried to make as extensible as possible by allowing users to not only use it for this game but to also design a tile-based level for *any* game.
- Nicole
    - Nicole's primary role was to create the menus/handle the flow among different stages of the application.
    - She also created detailed and impressive game art like backgrounds and sprites, all in addition to her creation of the game's story.
- Ben
    - Ben's primary role was to handle the input/output of our program, providing each of us with what we needed to eliminate all hard coded values and read in everything from JSON files.
    - Ben also worked a log-in system for our game that can keep track of a user's progress, and later on in the project, he made some efforts to improve the backend game objects.
- Jerry
    - Jerry's primary role was to build the engine behind the game. He wrote the step functions, defined how enemies interact, and designed the level system.
    - To support his efforts, he also designed some backend game objects.


## Design Goals  
what are the project's design goals, specifically what kinds of new features did you want to make easy to add
Our goal was to make our game as flexible as possible, even with the basic functionality of the game. 
 - We wanted to make it very easy to implement new backend game objects. Accordingly, once these objects’ code is written, one could very easily just specify the objects in the associated JSON file for a level, and our code could use existing frameworks to read in the new objects with reflection. Likewise, given the use of inheritance for these objects, they would be required to have certain attributes (like image, position, and size), meaning they could be converted into their associated frontend views without the need for any new code.
 - We wanted to make it very easy to add new menus to the game. With the Page class, 
 - We wanted to make it easy for users to modify/create levels. Because they can place objects themselves in the builder stage and do so with *any* object that has *any* functionality, the game design process is significantly easier and requires no hardcoding or if/else conditions about how to handle the users’ choice of objects.
 - The project has a robust and structured code hierarchy for pages. At a basic level, the abstract Page class accepts the primaryStage and manages the scene displayed in it; when a new javafx scene needs to be created, it passes this stage to a new Page. All pages extend this abstract Page class.
 - When the user starts the game, they are taken to a MainMenu class that allows them to either log in or make a new user; in either case, after the user has done so, a PageController class is constructed to manage the User class, a class from the Data package that handles front-end to json file interactions and allows the front end to have access to saved data, and to manage the Screen Height and Width, so all scenes are properly sized. The PageController is also passed to the Engine class to provide access to the User. 
 - At any time, pages can change between dark and light mode by switching CSS sheets from light.css to dark.css by pressing a button. 



## High Level Design  
The overall design follows the player-engine-data model. The data handles the reading in of information from data files, the player (our pagination package) supports the menus and path for the game, and the engine supports the backend game elements and stepping logic.
- Data
    - The data's design is rather simple: it has a number of methods available, each of which can be used to provide other parts of the application with read-in data. There are few dependencies/assumptions here about which data files to use, for other parts of the application can simply request certain data and *then* the data will be read in. The only assumptions are based on how the JSON data files are formatted.
- Player
    - The player is designed to support many different pages, each of which can be styled as desired and direct the user to different parts of a game. It relies on its corresponding CSS files, and it relies on certain game characteristics (like winning or losing) to present its menus.
- Engine
    - The engine is designed to support any number of backend game objects (like characters or enemies or platforms) that can update in accordance with a stepping function and handle interactions among each other. This engine relies on the data that is read-in to populate its levels and the ability to move through different pages from the player.


## Assumptions
- By going into great detail for our choice of game, we made the assumption that it would be okay to focus more on our game as opposed to creating additional different games.
- We assumed that it would be okay to provide minimal password protection for users. While we initially made strong efforts to use Google’s Firebase platform to handle this password security, we ran into numerous issues with Maven libraries. As a result, we decided to store user information locally.

## How to Add New Features:
- Game Selector
    -The primary new feature that needs to be added is a menu to select which game to play. In our original design (and thus visible in early commits), we had a LevelDirectory Page (a Page class that was extended) to allow Users to click which level they wanted to play.  Because we wanted to stick with our design goal of avoiding hard-coded limitations, we decided to remove a the level directory functionality as we had it because we were using a Level Directory system that had a hard-coded number of levels available to play. If one wanted to add a new level, they would have to hard code the addition of the level into the level directory in order to navigate to it. Because this was incongruent to our design goals, we sacrificed the Level Directory in favor of two new buttons: the Story mode, and the Resume Saved Game feature. In the story mode, the game goes through all levels sequentially, building every level available in the saved json file until it runs out. This enables us to avoid a hard-coded set of levels with a cap, and simply play level after level until the json file runs out. The Resume Saved Game feature is for when a user returns to the Main Menu and wishes to pick up where they left off. It only works according to the PageController class, meaning once the user exits the game completely, they cannot resume their saved progress. 
    - To add a new level currently, all one needs to do is simply write a new level in the json file and it will continue, which is a good design feature we already have. To add the feature of a more robust game selector feature, which we do not have, we would need to add a class in the data package that reads the total number of levels available in the levels.json file and pass that along to the current LevelDirectory class for it to construct an N number of level buttons to take the user to each level.
- High scores
    - This feature would allow users to keep track of their best runs as part of their profile. To add this feature, we would have to pass around the user object, and then once a run on a level is over, make a call to the user class’s “updateLevelScore()” method with the parameters of the new score and the level.  This method keeps the user’s highest score so if the new score is below the high score, it will not be saved. To load the user’s high score for a level, again we would need to pass around the instance of the user class and then make a call to the getter method “getLevel()” with the parameter of the level the user is currently running on.  To display the level high score, we could just add it to the HUD which updates automatically for the level. 
- Multiple Games at Once
    - This feature would be implemented quite similarly to the Game Selector mentioned above. Upon selecting a game to play, the application could simply launch a new window with that game, allowing users to play multiple games in different windows, all at the same time. 
    - At a lower-level, this feature would be introduced where the application creates a Game object after the user exits the main menu. Instead of passing this object the same Pane used for the menus, it would pass it a new Pane, which would be part of a Scene set in a different Stage, allowing for the Game to be launched in its own window.
- Dynamic Game Rules
    - This feature could be added by using variables and/or reflection. Users could select certain options (like how long the game should last or whether or not players could see each other), and they could select specific objects/entities to add/change (these changes would be implemented through reflection: an instance of the object that represents their choice would be created).
- Save Game Data in the Web
    - Our goal was to have a fully functioning online database through firebase by the end of the project. I think we have a pretty good start to that and we were able to read and write to the online database from one computer. However, when we added multiple computers and multiple restarts of the java code, firebase tried to create the database all over again each time, which is obviously a problem. I don’t think this problem is too difficult to fix but we were more focused on fixing the basic functionality of the game and didn’t have time to dig through the firebase documentation. Another thing that would facilitate using firebase, is that our files were in JSON, which firebase mimics with its databases so it would’ve been much easier to make that work. 
- Social Center
    - The configuration part of the social center would be easy to add after implementing the firebase database. We would just have to pull all of the data from firebase, much like reading a file from memory. The view piece would be harder as that would’ve mandated a completely new visual section.
