package menu;

import javafx.scene.Scene;

public enum Pages {
    MainMenu("MainMenu"), LevelDirectory("LevelDirectory"),
    LogIn("LogIn"), LocalSignIn("LocalSignIn"), FirstTimeCutScene("FirstTimeCutScene"),
    ChooseLevel("ChooseLevel"), CustomizePlayerMenu("PlayerMenu"), BluePrintStage("BuildStage"),
    PreView("Preview"),PlayLevel("PlayStage"),Replay("Replay"),NextLevelScreen("NextLevel"),
    Debug("Debug");

    private String myType;
    private Scene myScene;

    Pages(String type) {
        this.myType = type;
    }

    public String getMyType() {
        return myType;
    }
}
