
## NAMES
Pierce Forte, Nicole Lindbergh, Ben Burnett, Jerry Huang  

## API CHANGES  
- Replaced Opponent API in engine.gameobject.opponent with an abstract Opponent class
    - We made this change to make adding opponent-specific methods in the future more easy and flexible. There are several abstract classes like that in the GameObject inheritance hierarchy which break out specific roles of objects for future use. 
- Game.java
```abstract void initializeBackground(double[] levelInfo);``` is now deprecated
    - We decided to create the pane in the frontend instead of the backend
```abstract void step(double elapsedTime);``` is now deprecated
    - We decided to implement the step methods in separate controller classes
    - This allowed for separate controllers for the two different stages of the game (the build mode and the play mode)
- GameInteraction.java is now deprecated
    - Game interactions are now moved to be inside individual GameObject classes
- StepInterface.java was added
    - This interface ensures that a step function is always callable by the corresponding timeline
- LevelInterface.java was added
    - This interface was created to ensure that specific get methods existed. These methods include things like getBankController that are needed by other parts of the projects


