package org.budddy.main;

import org.budddy.object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public int thisMessage = 0;
    public int lastMessage = 0;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double drawCount;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Key key = new Key();
        keyImage = key.image;
        drawCount = 60;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
        thisMessage++;
    }

    public void getFPS() {
            drawCount = gp.drawCount;
    }

    public void draw(Graphics2D g2) {

        if (gameFinished) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);


            text = "Your time was: " + dFormat.format(playTime) + "seconds!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize *4);
            g2.drawString(text, x, y);


            g2.setFont(arial_80B);
            g2.setColor(Color.RED);

            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize *2);
            g2.drawString(text, x, y);

            gp.gameThread = null;


        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);



            g2.drawString("FPS: " + (drawCount), gp.tileSize*11, 65);


            playTime += (double)1/60;

            // MESSAGE
            if (messageOn) {
                if (thisMessage != lastMessage) {
                    messageCounter = 0;
                }
                lastMessage = thisMessage;

                g2.setFont(g2.getFont().deriveFont(30F));
                g2.setColor(Color.blue);
                g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
