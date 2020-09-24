package world.ucode;

import javafx.scene.image.Image;

import java.io.InputStream;

public class Res {
    static InputStream iconStream = Res.class.getResourceAsStream("/cactus1.png");
    static public Image image1 = new Image(iconStream);
    static InputStream iconStream2 = Res.class.getResourceAsStream("/cactus2.png");
    static public Image image2 = new Image(iconStream2);
    static InputStream iconStream3 = Res.class.getResourceAsStream("/land1.png");
    static public Image image3 = new Image(iconStream3);
    static InputStream iconStream4 = Res.class.getResourceAsStream("/land2.png");
    static public Image image4 = new Image(iconStream4);
    static InputStream iconStream5 = Res.class.getResourceAsStream("/land3.png");
    static public Image image5 = new Image(iconStream5);
    static public Image imagerex = new Image(Res.class.getResourceAsStream("/trex.png"));


    static public Image getImages(int chance) {
        if (chance == 0)
            return image1;
        else
            return image2;
    }

    static public Image getland(int chance) {
        if (chance == 0)
            return image3;
        else if (chance == 1)
            return image4;
        else
            return image5;
    }
}
