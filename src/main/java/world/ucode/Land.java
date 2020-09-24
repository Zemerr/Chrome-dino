package world.ucode;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Land extends Pane {
    ImageView block;
    Image pic;
    int w;
    int h;
    static int y = 410;


    public Land(int x) {
        setrandom();
        block = new ImageView(pic);
        setTranslateX(x);
        setTranslateY(y);
        //block.setImage(pics);
        //block.setFitWidth(w);
        //block.setFitHeight(h);

        getChildren().add(block);
        Main.root.getChildren().add(this);
        Main.lands.add(this);
    }




    void moveLeft() {
        setTranslateX(getTranslateX() - Main.speed);
    }
    void setrandom() {
        double foo =  Math.random()*100;
        if (foo > 97) {
            pic = Res.getland(0);
        }
        else if (foo < 4)
            pic = Res.getland(2);
        else {
            pic = Res.getland(1);
        }
    }
}
