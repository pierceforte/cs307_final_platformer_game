final
====

This project implements a platformer with a detailed game story and design. Highlights include a level-designing GUI, custom-made game art, and thorough input/output.

Names: Pierce Forte, Nicole Lindbergh, Benjamin Burnett, Jerry Huang


### Timeline

Start Date: March 29

Finish Date: April 25

Hours Spent: 100+

### Primary Roles
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

### Resources Used
- Many resources were used. Notably, we used StackOverflow to identify and solve bugs and Piazza to ask for help.

### Running the Program

Main class: 
- The main class can be found in src/Main.java
    - Note that upon running the application, one should opt to create a New Game and then create an account.
    - They will then be entered into a builder stage, where they can purchase and place items to help them beat the level.
    - They can then choose to play the level.

Data files needed: 
- All of the files in the resources directory are necessary for the game to run properly. These files include the following:
    - The data directory includes files that contain the information to build each level. There is also an error log in this directory.
    - The images folder includes all of the art needed for the game, including art used for background and for game elements.
    - The text directory includes all properties files, which are used to store all user-read text such that it can be modified easily.

Features implemented:
- Example games
    - Given the detail with which we delved into our game, our TA agreed that we could focus on this single game as opposed to multiple games.
    - Notably, however, the builder stage is *very* extensible and can be used to design a level for any tile-based game.
- Dark Mode
    - A button with an image icon is available throughout the game, allowing users to switch between light and dark modes.
- Load Games
    - Users can either choose to continue from where they left off (with their score maintained) or start over as a new player.
- Preferences
    - Users can choose which team they are on (Snakes vs. Snails) and which character within these teams they would like to be.
- Artificial Players
    - A basic implementation of artificial players can be seen in the game's enemies, who are given different AIs that allow them to attack the player.
- Game Area Editor
    - The builder stage serves as a game area editor where players can add to existing levels or create their own.
- Player Profiles
    - Players have the ability to create an account or sign in. These accounts include a player name, password, birthday, and avatar.
    
### Notes/Assumptions

Assumptions or Simplifications: 
    - By going into great detail for our choice of game, we made the assumption that it would be okay focus more on our game as opposed to creating additional different games.

Interesting data files: 
    - Our JSON files were rather challenging to design, but because we used reflection to support them, they are very extensible and flexible. Each is written in JSON and contains a set of information for each level, which includes constructors and variable information.

Known Bugs: 
    - There are a number of bugs that exist primarily within the game play.
        - After completing the first level, the game crashes.
        - Game object interactions are not very clean and can be unexpected at times.
        - After a player loses all of its lives, the game ends, as opposed to presenting a menu.

Extra credit:
    - While we did not add a significant number of additional features, for the features we did add, we went into *great* depth. Our game features a lot of incredible, custom-made art, a very flexible level-designer, and thorough input/output procedures using reflection.


### Impressions  
From the beginning, it was clear that this game would be an incredible challenge. It took us days (if not over a week) to finally wrap our heads around the game's requirements and design, but it was certainly worth all of these efforts. We each gained experience in a number of areas, and we gained a greater sense of what it is like to work on a full, detailed project from start to finish. And while some team members were taking the course as S/U (which made the requirements a little unclear at times), everyone contributed their fair share and helped to design a working game.
