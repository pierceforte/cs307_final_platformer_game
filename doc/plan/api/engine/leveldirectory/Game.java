

public abstract class Game implements SceneChanger {

    private Group root;
    private Timeline myAnimation;
    private Text display;

    private Runnable nextScene;
    private Scene myScene;
    private Stage myStage;


    public Game(int[] levelInfo, Stage stage);

    /**
     * initializes the level and objects needed for the game
     *
     * @param levelInfo: specifies the brick layout
     */
    abstract void initializeBackground(double[] levelInfo);

    /**
     * starts the animation
     */
    abstract protected void run();

    /**
     * step function that is called for the animation of the game. We tried to keep this method as concise as possible
     * so that it would be easy to debug and add new features
     */
    abstract void step(double elapsedTime);

    /**
     * handles user input
     *
     * @param k: user input
     */
    private void userInput(KeyCode k);

    /**
     * implements get method outlined in the engine.leveldirectory.gamesequence.SceneChanger interface
     */
    @Override
    public Scene getScene() {
        return myScene;
    }

    /**
     * implements the get method outlined in the engine.leveldirectory.gamesequence.SceneChanger interface
     */
    @Override
    public Runnable getNextScene() {
        return nextScene;
    }

    /**
     * implements setter outlined in the engine.leveldirectory.gamesequence.SceneChanger interface
     *
     * @param s: scene currently stored within the class
     */
    @Override
    public void setScene(Scene s) {
        myScene = s;
    }

    /**
     * implements setter outlined in the engine.leveldirectory.gamesequence.SceneChanger interface
     *
     * @param s: scene currently stored within the class
     */
    @Override
    public void setNextScene(Runnable s) {
        nextScene = s;
    }
}