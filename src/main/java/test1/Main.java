package test1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.InputStream;

import javafx.scene.layout.Pane;


import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.text.*;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;




import java.sql.SQLException;
import javafx.scene.paint.Color;




public class Main extends Application {

    static Pane root = new Pane();
    static int speed = 8;
    static boolean gameover = false;
    ImageView imageViewpaler = new ImageView(Res.imagerex);
    Character player = new Character(imageViewpaler, 40, 345);
    public static ArrayDeque<Obsstacles> cactuses = new ArrayDeque<>();
    static ArrayList<Land> lands = new ArrayList<>();
    Random rand = new Random();
    private int lastblockx = 0;
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    Label score = new Label();
    static int scorenum = 0;
    static int old_score = 0;
    Label hiscore = new Label();
    AnimationTimer timer;
    private boolean sv = true;
    Label startlab = new Label();

    boolean menu = true;





    final Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis( 150 ),
                    event -> {
                        score.setText(Long.toString(scorenum));
                        scorenum++;
                        if (scorenum%25==0)
                            speed +=1;
                    }
            )
    );








    private Parent createContent() throws ClassNotFoundException, SQLException {
        root.setPrefSize(1200, 500);
        root.setStyle("-fx-background-color: #F7F7F7");

        timer = new AnimationTimer()  {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        };

        land();
        obsticles();
        score.setTranslateX(1000);
        score.setTranslateY(100);

        player.toFront();
        score.setText("0");
        score.setFont(Font.loadFont( getClass().getResource("/Dinofont.ttf").toExternalForm(), 40));
        score.setTextFill(Color.web("#535353"));
        hiscore.setFont(Font.loadFont( getClass().getResource("/Dinofont.ttf").toExternalForm(), 40));
        hiscore.setTranslateX(600);
        hiscore.setTranslateY(100);
        hiscore.setTextFill(Color.web("#535353"));
        hiscore.setText("HI  " + old_score);
        hiscore.setWrapText(true);

        timeline.setCycleCount( Timeline.INDEFINITE );

        startlab.setFont(Font.loadFont( getClass().getResource("/Dinofont.ttf").toExternalForm(), 40));
        startlab.setTranslateX(300);
        startlab.setTranslateY(100);
        startlab.setTextFill(Color.web("#535353"));
        startlab.setText("START");

        root.getChildren().add(hiscore);
        root.getChildren().add(score);
        root.getChildren().add(startlab);



        timeline.play();
        timer.start();
        player.animation.play();



        return root;
    }

    @Override
    public void start(Stage stage) throws Exception, ClassNotFoundException, SQLException {

        Scene game = new Scene(createContent());
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image image = new Image(iconStream);

        game.setOnKeyPressed(event -> {
            keys.put(event.getCode(), true); });
        game.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);});

        stage.getIcons().add(image);
        stage.setTitle("dino");

        stage.setScene(game);

        stage.show();
    }

    private void obsticles() {
        int X = 0;

        for (int i = 0; i < 5; i++) {
            int nextX = rand.nextInt(400) + 400;
            if (i == 0)
                X = 2000;
            else
                X = (int)cactuses.getLast().getTranslateX() + nextX;
            Obsstacles cactus = new Obsstacles(X);

        }
    }

    private void land() {
        for (int i = 0; i < 100; i++) {
            Land blocks = new Land(lastblockx);
            lastblockx += 71;
        }
    }


    private void update() throws ClassNotFoundException, SQLException {
        if (gameover == false) {
            for (Obsstacles s : cactuses) {
                s.moveLeft();
                if (player.getTranslateX() >= s.getTranslateX() && player.getTranslateX() <= s.getTranslateX() + s.getWidth() - (s.getWidth())/2
                        || player.getTranslateX() + player.getWidth() - 25 >= s.getTranslateX() && player.getTranslateX() + player.getWidth() - 25 <= s.getTranslateX() + s.getWidth())
                {
                    if (player.getTranslateY() + player.getHeight() >= s.getTranslateY()
                            && player.getTranslateY() + player.getHeight() <= s.getTranslateY() + s.getHeight())
                    {
                        player.animation.stop();
                        timeline.stop();
                        gameover = true;
                        if (sv && db.saving)
                            if (scorenum - 1 > old_score)
                                db.WriteDB(scorenum - 1);

                        timer.stop();

                    }
                }

                if (s.getTranslateX() < -100) {
                    root.getChildren().remove(s);

                    cactuses.pollFirst();
                    int nextX = rand.nextInt(700) + 400;
                    Obsstacles n = new Obsstacles((int)cactuses.getLast().getTranslateX() + nextX);
                }
            }
            for (int i = 0; i < lands.size(); i++) {
                Land s = lands.get(i);
                s.moveLeft();
                if (s.getTranslateX() < -50) {
                    s.setTranslateX(lastblockx - 71);
                }
            }
            if(isPressed(KeyCode.UP)) {
                player.jump();
            }
            if(player.deltajump < 40){
                player.deltajump = player.deltajump+1;
            }
            player.moveY(player.deltajump);

        }

    }


    private Boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }



    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        db.CreateDB();
        launch(args);
    }
}


