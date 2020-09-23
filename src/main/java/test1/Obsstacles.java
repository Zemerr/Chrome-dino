package test1;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;



public class Obsstacles extends Pane {


    ImageView block;
    Image pic;
    int w;
    int h;
    int y = 350;



    public Obsstacles(int x) {
        setrandom();
        block = new ImageView(pic);
        setTranslateX(x);
        setTranslateY(y);
        block.setFitWidth(w);
        block.setFitHeight(h);
        

        getChildren().add(block);
        Main.root.getChildren().add(this);
        Main.cactuses.addLast(this);
    }




    void moveLeft() {
        setTranslateX(getTranslateX() - Main.speed);
    }
    void setrandom() {
        double foo =  Math.random()*100;
        if (foo > 20) {
            pic = Res.getImages(0);
            w = 40;
            h = 90;

        }
        else {
            pic = Res.getImages(1);
            w = 100;
            h = 60;
            y = y + 30;
        }
    }
};
