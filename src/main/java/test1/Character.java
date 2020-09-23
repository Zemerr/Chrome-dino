package test1;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Character extends Pane{

    ImageView imageView;
    int count = 4;
    int columns = 1;
    int offsetX = 0;
    int offsetY = 0;
    int width = 88;
    int height = 94;
    int deltajump = 0;
    SpriteAnimation animation;
    private boolean canJump = true;

    public Character(ImageView imageView, int x, int y) {
        this.imageView = imageView;
        this.imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView,Duration.millis(300),count,columns,offsetX,offsetY,width,height);
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(imageView);
        Main.root.getChildren().addAll(this);
    }
    public void moveY(int val) {
        boolean movingDown = val > 0;
        for(int i = 0; i < Math.abs(val); i++) {
            if (this.getTranslateY() < 150 && !movingDown) {
                return;
            }
            if (this.getTranslateY() > 345 && movingDown) {
                if (canJump == false)
                    animation.play();
                canJump = true;
                deltajump = 40;
                return;

            }
            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
        }
    }

    public void jump() {
        if(canJump){
            animation.pause();
            deltajump = -20;
            canJump = false;
        }
    }
}