##Use Cases

USE_CASES.md

Write at least 5 use cases per person on your team that describe specific features you expect to complete. Focus on those features you plan to complete during the first two sprints (i.e., the next two weeks — the project's highest priority features). Note, they can be similar to those given in the assignment specification, but should include more details based on your project's goals.

http://agilemodeling.com/artifacts/systemUseCase.htm

##Pierce (Backend):
1. Signing in
    -  Description
        -  A player wants to sign into their profile
    - Preconditions
        - The player already has an account
    - Postconditions
        - The player will be sign into their account, allowing them to access their money, their avatars, their highscores, their progress, etc.
    - Basic course of action
        -   The player clicks the sign in icon
        -   The player enters their username and password
        -   The player is prompted to try again if their credentials are invalid, else they are signed in and can begin playing the game
2. engine.gameobject.player.Player death
    - Description
        - A player encounters a hazard and dies
    - Preconditions
        - Before the hazard, the player has not touched an object that can kill it.
    - Postconditions
        - The player will make a choice about how to continue.
    - Basic course of action
        - A sound and animation are played to indicate the player’s death
        - A pagination pops up presenting the user options for how to continue
        - If they have enough money, they can continue
        - Else they have the option to restart
        - If they reached a checkpoint, they can restart from there
        - If the user chooses to quit, they will have options regarding saving their score
3. Beginning a level
    - Description
        - A player begins a level
    - Preconditions
        - The player has access to this level after having cleared previous levels or using money to advance to this level
    - Postconditions
        - The player can play the level
    - Basic course of action    
        - The player selects the level to play
        - The player chooses whether to restart or continue the level (if they have previously saved it)
        - The player can also choose to “race” the previous high score “ghost”
        - The player begins the level
4. Blueprint stage
    - Description
        - A player begins a level, can now view its blueprint, and adds objects to create a path for defeating the level
    - Preconditions
        - The player has chosen to start the level from the beginning.
    - Postconditions
        - The player can play the level and utilize the objects that have been placed.
    - Basic course of action
        - The player is presented with the blueprint stage.
        - The player can scroll through and view the level, and they can scroll through a list of placeable objects.
        - The player can select an object and place it anywhere on the level (these objects may “snap” to a grid)
        - Each time a player selects an object, they have to “pay” for it in some sense such that there is a limit to the number of objects used
        - Once the user is happy with their object placement, they can start playing the level
5. Basic enemy movement
    - Description
        - This enemy has a simple thought process: move toward the player and attempt to do damage
    - Preconditions
        - The enemy is in sight of the player
    - Postconditions
        - The player loses a life
    - Basic course of action
        - The enemy, when on the screen at the same time as the player, turns toward the player
        - On each step, the enemy moves toward the player
        - If the player does not move/defeat the enemy, the enemy touches the player and deals a certain amount of damage
        - The player is now invincible for a few seconds (if not dead), and the enemy disappears or does not move

#Jerry (Controller):
1. Initializes the sequence of levels
    - Description: initializes the necessary objects needed to create a level by instantiating the appropriate classes from the backend
    - Preconditions: has access to the necessary classes in the backend to create the objects
    - Postconditions: instantiated levels and transitions scenes
    - Basic course of action
        - Gets the necessary level data from the I/O
        -Instantiates the objects based on the input data
        -Creates the levels and links them in a sequence (i.e. create a sequence of levels that flow from one to the next)
2. User input that affects the flow of the game
   - Description: buttons such as play/pause, reset, quit. The controller gets input from the frontend and classes the necessary methods from the backend
   - Preconditions: the controller has access to the user input
   - Postconditions: effectively processes the user input
   - Basic course of action
      - The frontend will pass in the user’s input as a parameter
      - The controller will either directly modify the game’s flow based on the user input or will call the appropriate method from the backend

3. Facilitates the different stages of the game (build phase, play phase, etc.)
    - Description: runs the different stages of the games
    - Preconditions: initialized levels and transition scenes,
    - Postconditions: transitions to the next level
    - Basic course of action
        - Checks if the current stage of the game is complete
        - Runs the next runnable

4. Handles interactions between objects
    - Description: if two objects collide with one another, the controller will lookup the appropriate interaction between the two objects and return the effects
    - Preconditions: states of the two objects
    - Postconditions: appropriate new states of the two colliding objects
    - Basic course of action
        - Lookup the interaction between the two objects
        - Update the states of the two objects
        
5. Create the parent class for the game
    - Description: creates the game class which runs the game
    - Preconditions: access to the objects in the backend
    - Postconditions: returns the appropriate GUIs to the frontend to be displayed
    - Basic course of action
        - Instantiates the level
        - Stores necessary information in private fields
        - Returns the scene for the frontend to display
##Ben (Data - Configuration)
1. User tries to log in to system
    - Call made to *****google accounts****** (how to get this to work/is this the best way to password protect stuff) to provide security and simultaneous call made to locally stored data (how to password protect locally)
    - Preconditions: access to internet
    - Postconditions: returns to the appropriate GUI’s to select the next player actions
    - Basic course of action
        - Instantiates a new player object with the data loaded from the saved profile
        - Passes the player back to the GUI for next steps

2. User selects a level and makes a call to the data to get the initial configuration
    - Basic course of action
    - Configuration makes a new level object which acts as a container until to pass the data back to the front end where it can be displayed
    - User saves an edited layout that made it easy for them to beat the level
    - Basic course of action
        - Configuration receives the level object which contains all of the components and saves the file as local json
3. User beats a level and wants to save their layout
    - Description: user has beaten a level and hits the save button so that they can keep the way that they solved it for future reference or future sharing
    - Preconditions: level has been beaten
    - Postconditions: return to the select your level screen
    - Basic course of action: 
        - Passes a level object back to the save level class to save the information as a json file in the data
        - Moves on to the next level
4. User saves their information and exits the game
    - Description: needs to save information when the user leaves so that on the next reboot the user can load where they left off
    - Preconditions: user is done playing the game - last checkpoint reached is final stage
    - Postconditions: User logs off and the application closes
    - Basic course of Action

##Nicole
1. User clicks on a Level to play it
    - Description:
        - User has decided to play a level. They need to go to the BLUEPRINT stage, where they can see the full layout of the map and a BANK of GameObjects they can add to the level map to reach their objective(s).
    - Preconditions:
        - The user has access to internet.
    - Postconditions:
        - The user has access to internet to save their progress after defeating/failing to defeat the level
    - Basic course of action
        - The Scenechanger must inform the Game to switch scenes to the BLUEPRINT stage.
        - The Game must call the Data package to read the level layout .json file associated with the level chosen by the user.
        - The Engine must build the basic level layout based on this information and display it for the user. 
        - The Game must call the Data package to read the .json file associated with the Bank of this level. It must pass this information to the Engine to make a BANK of objects based on the objects written in the .json file.
        - The Engine must display these GameObjects in the display BANK so the use can interact with them.
2. User interacts with an Object in the Bank
    - Description:
        - User can click and drag an object in the BANK in the BLUEPRINT stage to add a GameObject to their level.
    - Preconditions:
        - User has selected a level to play
        - Game has already loaded a map and bank for user to interact with.
    - Postconditions:
        - Game has informed Data package of the changes made to the map so Data can rewrite the .json file associated with the saved level configurations to a map.
        - BANK has one less object in it.
    - Basic course of action
        - User clicks an object. User must spend coins to buy the object; the coins are removed from the engine.gameobject.player.Player’s inventory (represented in engine.gameobject.player.Player API). If the user does not have the coins, nothing happens.
        - If they do, this object is no longer represented in the BANK, and a sprite of the object will follow the cursor until the user has placed it.
        - The user deposits the object in a tile space. The Engine will read the location of the object (represented in a Javafx affine) and tell the Data package to update the .json file associated with the saved level configurations of the map for the player to play later.
        - When the user presses play level, they will see this object represented in the level.

3. User opens the customizable pagination.
    - Description
        - The user wants to see their lifetime game stats (victories; level progress; high scores; coin count) and maybe change their character.
    - Preconditions
        - User has made a global account
    - Postconditions
        - The engine.gameobject.player.Player’s information will update accordingly
    - Basic course of action
        - User presses an icon associated with the engine.gameobject.player.Player’s description.
        - User is taken to a player info pagination
        - Menu displays number of levels beaten with their high scores and time completed stats. They can also see how many coins they have.
        - In the bottom of the screen they can see other snizards they can unlock. If they have the coins, they can purchase a new snizard to play. If they have already unlocked a snizard and paid for it, they can switch characters anytime. Information on the abilities of their snizards is on the bottom of the screen.
        - There is also a trophy room of other snizards describing their abilities.The player CANNOT unlock rival snizards no matter how many coins they get.

4. User clicks on a snizard sprite in the Customizable Menu.
    - Description
        - User has clicked on a snizard sprite in the Customizable Menu. They want to switch playable characters and have chosen this particular snizard.
    - Preconditions
        - The user already has a snizard they are playing as; this is not the same snizard.
    - Postconditions
        - Either they can unlock this snizard by paying the coins or they cannot. If they can, they will now play as this snizard avatar, with all abilities associated with this snizard available to them.
        - If they cannot, a dialogue box will open explaining that they cannot.
    - Basic course of action
        - User clicks on a sprite. Engine tells engine.gameobject.player.Player that this sprite will cost X amount of coins.
        - engine.gameobject.player.Player tells Engine how many coins the User has. Engine checks whether this is sufficient; if so, this amount is removed from the engine.gameobject.player.Player and the sprite is unlocked. The Engine tells the engine.gameobject.player.Player that the sprite is unlocked, and this unlocked character is given to Data to remember that this character is unlocked. 
        - Automatically, in this scenario, the engine.gameobject.player.Player’s sprite switches to this snizard. engine.gameobject.player.Player now remembers this sprite; when called by Engine to produce the sprite associated with the engine.gameobject.player.Player in BLUEPRINT and GAMEPLAY, it will refer to the .json image associated with the sprite in Data.

5. User clicks on a vending machine.
    - Description
        - In the Playable Level, the user clicks on a Vending Machine, which is a GameObject the engine.gameobject.player.Player can see. 
    - Preconditions
        - User has already assembled their level in the BLUEPRINT stage and is playing it. 
        - User has placed a Vending Machine GameObject in their map, meaning it must have already spawned in the BANK and the user has paid for it with coins.
        - Playable character is within the Vending Machine’s set radius to interact with the object.
        - engine.gameobject.player.Player has enough coins to pay for a Potion from the Vending Machine
    - Postconditions
        - User spends one coin to interact with the Vending Machine; that coin is removed from engine.gameobject.player.Player. 
        - User now has a Potion in their inventory.
    - Basic course of action
        - User has enough coins to pay the Vending Machine. The Engine tells the engine.gameobject.player.Player to remove a coin from their coin count.
        - Engine animates the Vending Machine on screen and deposits a Potion in the engine.gameobject.player.Player’s inventory. engine.gameobject.player.Player can now press P to throw the Potion. 
        - Engine tells Data that the engine.gameobject.player.Player has a Potion in their inventory. Data updates the .json file associated with the engine.gameobject.player.Player’s inventory accordingly.
