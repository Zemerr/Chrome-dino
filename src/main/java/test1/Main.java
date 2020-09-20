package test1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.InputStream;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;


import java.util.Random;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;


import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main extends Application {

    static Pane root = new Pane();
    static int speed = 7;


    ImageView imageViewpaler = new ImageView(Res.imagerex);
    Character player = new Character(imageViewpaler, 40, 350);
    public static ArrayDeque<Obsstacles> cactuses = new ArrayDeque<>();
    static ArrayList<Land> lands = new ArrayList<>();
    Random rand = new Random();
    private int lastblockx = 0;



    private Parent createContent() {
        root.setPrefSize(1200, 500);

        root.getChildren().addAll(player);
        root.setStyle("-fx-background-color: #F7F7F7");

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
              update();
            }
        };
        timer.start();
        land();
        obsticles();
        player.toFront();
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {

        Scene game = new Scene(createContent());
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image image = new Image(iconStream);


        stage.getIcons().add(image);
        stage.setTitle("dino");
        stage.setScene(game);
        player.animation.play();
        stage.show();
    }

    private void obsticles() {
        int X = 0;
        double type = Math.random();

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


    private void update() {
        for (Obsstacles s : cactuses) {
            s.moveLeft();
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
    }


    public static void main(String[] args) {
        launch(args);
    }
}


