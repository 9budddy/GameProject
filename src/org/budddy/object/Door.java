package org.budddy.object;

import org.budddy.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Door extends SuperObject{

    GamePanel gp;

    public Door(GamePanel gp) {

        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
