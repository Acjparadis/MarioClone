import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Level {
    Player player;
    Vector<Enemy>enemies = new Vector<Enemy>(0);
    Vector<DestructibleBlock> destructBlocks = new Vector<DestructibleBlock>(0);
    Vector<Drop> drops = new Vector<Drop>(0);
    DropBlock[] dropBlocks = new DropBlock[]{};
    Ground[] ground = new Ground[]{};
    JPanel canvas = new JPanel(){
        public synchronized void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics graphics = g;
            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            long startTime = System.nanoTime()/1000000;
            if (player != null){
                Draw(graphics, null);
            }
            System.out.println("Draw Time: " + (System.nanoTime()/1000000 - startTime));
        }
    };
    BufferStrategy buffer;
    Image background;
    final int scale;
    JScrollPane scrollPane = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    int rightScroll = 1000;

    public Level(JFrame frame) {
        scale = Math.min(frame.getHeight()/172, frame.getWidth()/307);
        scrollPane.setCursor(null);
        canvas.setCursor(null);
        canvas.setSize(0, frame.getHeight());
        scrollPane.setWheelScrollingEnabled(false);
        frame.add(scrollPane);
        scrollPane.setSize(frame.getWidth(), frame.getHeight());
        try {
            File file = new File("Mario.png");
            BufferedImage inputImage = ImageIO.read(file);
            BufferedImage image = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            BufferedImage usedImage = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            player = new Player(image, new int[]{image.getWidth()*2, canvas.getHeight()-image.getHeight()*5}, image, scale);

            file = new File("Ground.png");
            inputImage = ImageIO.read(file);
            image = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            g2d = image.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            int numGround = 50;
            ground = new Ground[numGround];
            for (int i = 0; i < numGround; i++) {
                ground[i] = new Ground(image, image.getWidth()*i, canvas.getHeight()-image.getHeight());
            }
            file = new File("Block.png");
            inputImage = ImageIO.read(file);
            image = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            g2d = image.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            destructBlocks.add(new DestructibleBlock(image, image.getWidth(), canvas.getHeight()-image.getHeight()*2, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth(), canvas.getHeight()-image.getHeight()*5, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*3, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*4, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*5, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*6, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*7, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*8, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*10, canvas.getHeight()-image.getHeight()*9, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*20, canvas.getHeight()-image.getHeight()*3, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*30, canvas.getHeight()-image.getHeight()*3, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*40, canvas.getHeight()-image.getHeight()*3, image));
            destructBlocks.add(new DestructibleBlock(image, image.getWidth()*49, canvas.getHeight()-image.getHeight()*2, image));

            file = new File("Goomba.png");
            inputImage = ImageIO.read(file);
            image = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            g2d = image.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            enemies.add(new Goomba(image, image.getWidth()*8, canvas.getHeight()-image.getWidth()*2, image, scale));
            enemies.add(new Goomba(image, image.getWidth()*10, canvas.getHeight()-image.getWidth()*10, image, scale));
            
            file = new File("DropBlock.png");
            inputImage = ImageIO.read(file);
            image = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            g2d = image.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            file = new File("DropBlockUsed.png");
            inputImage = ImageIO.read(file);
            usedImage = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            g2d = usedImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            file = new File("Mushroom.png");
            inputImage = ImageIO.read(file);
            BufferedImage dropImage = new BufferedImage(inputImage.getWidth()*scale, inputImage.getHeight()*scale, inputImage.getType());
            g2d = dropImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, inputImage.getWidth()*scale, inputImage.getHeight()*scale, null);
            int numDropBlock = 1;
            dropBlocks = new DropBlock[numDropBlock];
            dropBlocks[0] = new DropBlock(image, image.getWidth()*4, canvas.getHeight()-image.getHeight()*5, usedImage, new Mushroom(dropImage, image.getWidth()*4, canvas.getHeight()-image.getWidth()*6, scale));

            int canvasWidth = 0;
            {
                int x = player.getPosition()[0]+player.getImage().getWidth(null);
                canvasWidth = x > canvasWidth ? x : canvasWidth;
                // System.out.println("Canvas size:\nCurrent: " + canvasWidth + "\nNew: " + x);
            }
            for (Ground ground : ground) {
                int x = ground.getPosition()[0]+ground.getImage().getWidth(null);
                canvasWidth = x > canvasWidth ? x : canvasWidth;
                // System.out.println("Canvas size:\nCurrent: " + canvasWidth + "\nNew: " + x);
            }
            for (Enemy enemy : enemies) {
                int x = enemy.getPosition()[0]+enemy.getImage().getWidth(null);
                canvasWidth = x > canvasWidth ? x : canvasWidth;
                // System.out.println("Canvas size:\nCurrent: " + canvasWidth + "\nNew: " + x);
            }
            for (DestructibleBlock block : destructBlocks) {
                int x = block.getPosition()[0]+block.getImage().getWidth(null);
                canvasWidth = x > canvasWidth ? x : canvasWidth;
                // System.out.println("\nCanvas size:\nCurrent: " + canvasWidth + "\nNew: " + x);
            }
            canvas.setSize(canvasWidth, frame.getHeight());
            canvas.setPreferredSize(new Dimension(canvasWidth, frame.getHeight()));
            scrollPane.setPreferredSize(new Dimension(canvasWidth, frame.getHeight()));
            canvas.repaint();
            player.StartLevelInput();
        } catch (IOException e) {
            System.out.println("Image could not be found");
        }
        rightScroll = scrollPane.getWidth()/2;
        System.out.println("canvas width: " + canvas.getWidth());
    }

    public void gameloop(){
        //remove enemies and blocks that have been dead long enough
        enemies.removeIf(i -> i.shouldRemove());
        destructBlocks.removeIf(i -> i.shouldRemove());
        drops.removeIf(i -> i.shouldRemove());
        //check for collisions
        int[] position = player.getPosition();
        for (int i = 0; i < enemies.size(); i++) {
            boolean[] enemyImpacting = enemies.elementAt(i).isIntersecting(position[0], position[1]);
            if (enemyImpacting[1] && player.isAlive() && enemies.elementAt(i).isAlive()){
                enemies.elementAt(i).Die();
                player.Bounce();
            }
            else if (enemyImpacting[0] && enemies.elementAt(i).isAlive()){
                player.Die();
            }
            else {
                int[] enemyPosition = enemies.elementAt(i).getPosition();
                for (int x = 0; x < destructBlocks.size(); x++) {
                    enemies.elementAt(i).impact(destructBlocks.elementAt(x), enemyPosition);
                }
                for (Ground ground : ground) {
                    enemies.elementAt(i).impact(ground, enemyPosition);
                }
                for (DropBlock dropBlock : dropBlocks){
                    enemies.elementAt(i).impact(dropBlock, enemyPosition);
                }
                enemies.elementAt(i).gameloop(position);
            }
        }
        for (int i = 0; i < drops.size(); i++) {
            boolean[] dropImpacting = drops.elementAt(i).isIntersecting(position[0], position[1]);
            if (/* (dropImpacting[0] || dropImpacting[1]) */false && player.isAlive() && drops.elementAt(i).isAlive()){
                drops.elementAt(i).Die();
            }
            else {
                int[] dropPosition = drops.elementAt(i).getPosition();
                for (int x = 0; x < destructBlocks.size(); x++) {
                    drops.elementAt(i).impact(destructBlocks.elementAt(x), dropPosition);
                }
                for (Ground ground : ground) {
                    drops.elementAt(i).impact(ground, dropPosition);
                }
                for (DropBlock dropBlock : dropBlocks){
                    drops.elementAt(i).impact(dropBlock, dropPosition);
                }
                drops.elementAt(i).gameloop();
            }
        }
        for (int i = 0; i < destructBlocks.size(); i++) {
            if (destructBlocks.elementAt(i).isAlive()){
                player.impact(destructBlocks.elementAt(i).isIntersecting(position[0], position[1], true), destructBlocks.elementAt(i).getPosition());
            }
        }
        for (Ground ground : ground) {
            player.impact(ground.isIntersecting(position[0], position[1]), ground.getPosition());
        }
        for (DropBlock dropBlock : dropBlocks) {
            player.impact(dropBlock.isIntersecting(position[0], position[1], true), dropBlock.getPosition());
            if (dropBlock.isUsed() && dropBlock.getDrop() != null){
                drops.add(dropBlock.getDrop());
            }
        }
        //scroll
        int scrolled = (int)scrollPane.getHorizontalScrollBar().getValue();
        player.gameloop(scrolled);
        if (player.getPosition()[0] - scrolled > rightScroll && !(scrolled >= scrollPane.getHorizontalScrollBar().getModel().getMaximum())){
            scrollPane.getHorizontalScrollBar().setValue(player.getPosition()[0] - rightScroll);
        }
        canvas.repaint();
    }

    private void Draw(Graphics g, ImageObserver observer) {
        // if (background == null){
        //     background = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        //     Graphics imageGraphics = background.getGraphics();
        //     for (Ground ground : ground) {
        //         ground.Draw(imageGraphics, observer);
        //     }
        // }
        // else if (groundDrawTime <= 0) {
        //     g.drawImage(background, 0, 0, observer);
        // }
        // else {
        //     groundDrawTime--;
        // }
        for (Ground ground : ground) {
            ground.Draw(g, observer);
        }
        for (DestructibleBlock block : destructBlocks) {
            block.Draw(g, observer);
        }
        for (Enemy enemy : enemies) {
            enemy.Draw(g, observer);
        }
        for (DropBlock dropBlock : dropBlocks){
            dropBlock.Draw(g, observer);
        }
        player.Draw(g, observer);
    }
}