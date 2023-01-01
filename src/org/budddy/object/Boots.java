package org.budddy.object;

import org.budddy.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Boots extends SuperObject {

    GamePanel gp;

    public Boots(GamePanel gp) {

        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
