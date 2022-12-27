package org.budddy.entity;

import org.budddy.main.GamePanel;
import org.budddy.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
        random = -1;
        tempRandom = -1;

    }

    public void getPlayerImage() {

        try {

            upl = ImageIO.read(getClass().getResourceAsStream("/player/up-l.png"));
            upr = ImageIO.read(getClass().getResourceAsStream("/player/up-r.png"));
            ups = ImageIO.read(getClass().getResourceAsStream("/player/up-s.png"));
            downl = ImageIO.read(getClass().getResourceAsStream("/player/down-l.png"));
            downr = ImageIO.read(getClass().getResourceAsStream("/player/down-r.png"));
            downs = ImageIO.read(getClass().getResourceAsStream("/player/down-s.png"));
            leftl = ImageIO.read(getClass().getResourceAsStream("/player/left-l.png"));
            leftr = ImageIO.read(getClass().getResourceAsStream("/player/left-r.png"));
            lefts = ImageIO.read(getClass().getResourceAsStream("/player/left-s.png"));
            rightl = ImageIO.read(getClass().getResourceAsStream("/player/right-l.png"));
            rightr = ImageIO.read(getClass().getResourceAsStream("/player/right-r.png"));
            rights = ImageIO.read(getClass().getResourceAsStream("/player/right-s.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
                y -= speed;

            }
            else if (keyH.downPressed) {
                direction = "down";
                y += speed;

            }
            else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;

            }
            else if (keyH.rightPressed) {
                direction = "right";
                x += speed;

            }
            if (spriteCounter == -1 && tempRandom == -1) {
                random = 1;
                tempRandom = 1;

            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (random == 0) {
                    random = 1;
                } else if (random == 1) {
                    random = -1;
                } else if (random == -1) {
                    random = 0;
                }

                spriteCounter = -1;
            }

        } else {
            random = -1;
            tempRandom = -1;
            spriteCounter = -1;
        }
    }
    public void draw(Graphics2D g2) {

//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);


        BufferedImage image = null;



        switch (direction) {
            case "up":
                if (random == 0) {
                    image = upl;
                } else if (random == 1) {
                    image = upr;
                }
                if (random == -1) {
                    image = ups;
                }
                break;

            case "down":
                if (random == 0) {
                    image = downl;
                } else if (random == 1) {
                    image = downr;
                } else if (random == -1) {
                    image = downs;
                }
                break;

            case "left":
                if (random == 0) {
                    image = leftl;
                } else if (random == 1) {
                    image = leftr;
                } else if (random == -1) {
                    image = lefts;
                }
                break;


            case "right":
                if (random == 0) {
                    image = rightl;
                } else if (random == 1) {
                    image = rightr;
                } else if (random == -1) {
                    image = rights;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
