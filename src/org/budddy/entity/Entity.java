package org.budddy.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage upl, upr, ups, downl, downr, downs, leftl, leftr, lefts, rightl, rightr, rights;
    public String direction;

    public int spriteNum;
    public int spriteMoving;
    public int spriteCounter;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
