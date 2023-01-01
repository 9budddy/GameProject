package org.budddy.entity;

import org.budddy.main.GamePanel;
import org.budddy.main.KeyHandler;
import org.budddy.main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(18, 36, 12, 10);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();

    }


    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        spriteNum = -1;
        spriteMoving = -1;
        spriteCounter = -1;
    }


    public void getPlayerImage() {

        upl = setup("up-l");
        upr = setup("up-r");
        ups = setup("up-s");
        downl = setup("down-l");
        downr = setup("down-r");
        downs = setup("down-s");
        leftl = setup("left-l");
        leftr = setup("left-r");
        lefts = setup("left-s");
        rightl = setup("right-l");
        rightr = setup("right-r");
        rights = setup("right-r");

    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            }
            else if (keyH.downPressed) {
                direction = "down";
            }
            else if (keyH.leftPressed) {
                direction = "left";
            }
            else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if (spriteNum == -1) {
                spriteNum = (int) Math.floor(Math.random() * 2);
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 0) {
                    spriteNum = 1;
                } else if (spriteNum == 1) {
                    spriteNum = 0;
                }

                spriteCounter = -1;
            }
        } else {
            spriteNum = -1;
            spriteMoving = -1;
            spriteCounter = -1;
        }
    }


    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Key":
                    gp.playSoundEffect(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;

                case "Door":
                    if (hasKey > 0) {
                        gp.playSoundEffect(4);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You unlocked a door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;

                case "Boots":
                    gp.playSoundEffect(3);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up your Helens! Zoom!");
                    break;

                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSoundEffect(2);
                    break;
            }
        }
    }


    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 0) {
                    image = upl;
                } else if (spriteNum == 1) {
                    image = upr;
                }
                if (spriteNum == -1) {
                    image = ups;
                }
                break;

            case "down":
                if (spriteNum == 0) {
                    image = downl;
                } else if (spriteNum == 1) {
                    image = downr;
                } else if (spriteNum == -1) {
                    image = downs;
                }
                break;

            case "left":
                if (spriteNum == 0) {
                    image = leftl;
                } else if (spriteNum == 1) {
                    image = leftr;
                } else if (spriteNum == -1) {
                    image = lefts;
                }
                break;

            case "right":
                if (spriteNum == 0) {
                    image = rightl;
                } else if (spriteNum == 1) {
                    image = rightr;
                } else if (spriteNum == -1) {
                    image = rights;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
