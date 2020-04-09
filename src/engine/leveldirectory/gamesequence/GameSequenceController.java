package engine.leveldirectory.gamesequence;

import engine.general.Game;
import engine.leveldirectory.level.LevelContainer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameSequenceController {
    public static final int FRAME_DURATION = 10;

    private LevelContainer levelContainer;
    private Timeline timeline;

    public GameSequenceController(LevelContainer levelManager, Game info) {
        this.levelContainer = levelManager;
        setupTimeline();


    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
    }

    public void pause() {
        timeline.pause();
    }

    public void play() {
        timeline.play();
    }

    public LevelContainer getLevelContainer() {
        return levelContainer;
    }

    public Timeline getTimeline() {
        return timeline;
    }
}
