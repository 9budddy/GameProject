package org.budddy.object;

import org.budddy.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends SuperObject{

    GamePanel gp;

    public Key(GamePanel gp) {

        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
