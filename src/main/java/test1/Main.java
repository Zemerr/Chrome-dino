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

public class Main extends Application {

    static Pane root = new Pane();
    static int speed = 9;


    ImageView imageViewpaler = new ImageView(Res.imagerex);
    Character player = new Character(imageViewpaler, 40, 350);
    public static ArrayDeque<Obsstacles> cactuses = new ArrayDeque<>();
    static ArrayList<Land> lands = new ArrayList<>();
    Random rand = new Random();
    private int lastblockx = 0;
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();



    private Parent createContent() {
        root.setPrefSize(1200, 500);

        //root.getChildren().addAll(player);
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

        game.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            System.out.println(keyCode);
            System.out.println("lol2");
            keys.put(event.getCode(), true);
            if (event.equals(KeyCode.Q)) {
                System.out.println("lolsita");

            }});
        game.setOnKeyReleased(event -> {
            System.out.println("lol1");
            keys.put(event.getCode(), false);});
        System.out.println("start");
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
            if (player.getBoundsInParent().intersects(s.getBoundsInParent())) {
                player.animation.stop();
                speed = 0;
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
        if(isPressed(KeyCode.UP)){
            System.out.println("lol");
            player.jump();
        }
        if(player.deltajump < 40){
            player.deltajump = player.deltajump+1;
        }
        player.moveY(player.deltajump);
    }


    private Boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }



    public static void main(String[] args) {
        launch(args);
    }
}


