package org.budddy.main;

import org.budddy.entity.Player;
import org.budddy.object.SuperObject;
import org.budddy.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenRow = 12;
    public final int maxScreenCol = 16;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels


    // WORLD SETTINGS
    public int maxWorldRow = 50;
    public int maxWorldCol = 50;

    // FPS
    int FPS = 60;
    public int drawCount = 0;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); // game panel can be focused to receive input
    }

    public void setupGame() {
        aSetter.setObject();

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    // Sleep Loop
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null) { // as long as exists, repeat process that is written
//
////---------------------------------------------------------------------------------------
//            // 1 UPDATE: update information such as character positions
//            update();
//
//            // 2 DRAW: draw the screen with the updated information
//            repaint();
////---------------------------------------------------------------------------------------
//
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long)remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    // Delta/Accumulator Loop
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                ui.getFPS();
                drawCount = 0;
                timer = 0;
            }

        }


    }


    public void update() {

        player.update();

    }


    public void paintComponent(Graphics g) { // graphics is pencil

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }


        SuperObject[] tempObj = new SuperObject[obj.length];
        int tempIndex = 0;

//---------------------------------------------------
        // Draw as if Layers
        tileM.draw(g2, 1);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                switch (obj[i].name) {
                    case "Boots", "Key" -> {
                        obj[i].draw(g2, this);
                    }
                    default -> {
                        tempObj[tempIndex] = obj[i];
                        tempIndex++;
                    }
                }
            }
        }

        player.draw(g2);

        tileM.draw(g2, 2);

        for (int i = 0; i < tempObj.length; i++) {
            if (tempObj[i] != null) {
                tempObj[i].draw(g2, this);
            }
        }

        ui.draw(g2);
//---------------------------------------------------

        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }


        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    }
}
