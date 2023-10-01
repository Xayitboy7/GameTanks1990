package com.thebytguru.game;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.thebytguru.IO.Input;
import com.thebytguru.display.Display;
import com.thebytguru.graphics.TextureAtlas;
import com.thebytguru.utils.Time;

public class Game implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks 727";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_BATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_BATE;
    public static final long IDLE_TIME = 1;

    public static final String ATLAS_FILE_NAME = "texture_atlas.png";

    private boolean running;
    private Thread gamThread;
    private Graphics2D graphics;
    private Input input;
    private TextureAtlas atlas;
    private SpriteSheet sheet;
    private Sprite sprite;

    float x = 350;
    float y = 250;
    float delta = 0;
    float radius = 50;
    float speed = 3;

    public Game() {
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        sheet = new SpriteSheet(atlas.cut(8 * 16, 5 * 16, 2 * 16, 16), 2, 16);
        sprite = new Sprite(sheet, 1);
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        gamThread = new Thread(this);
        gamThread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            gamThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cleanUp();
    }

    private void update() {
        //delta += 0.02f;
        if(input.getKey(KeyEvent.VK_W)){
            y -= speed;
        }
        if(input.getKey(KeyEvent.VK_DOWN)){
            y += speed;
        }
        if(input.getKey(KeyEvent.VK_LEFT)){
            x -= speed;
        }
        if(input.getKey(KeyEvent.VK_RIGHT)){
            x += speed;
        }
    }

    private void render() {
        Display.clear();
        // graphics.drawImage(atlas.cut(0, 0, 32, 32), 300, 300, null);
        // graphics.setColor(Color.WHITE);
        //graphics.fillOval((int) (x + (Math.sin(delta) * 200)), (int) (y), (int) (radius * 2), (int) (radius * 2));
        Display.swapBuffers();
        sprite.render(graphics, x, y);
    }

    private void cleanUp() {
        Display.destroy();
    }

    @Override
    public void run() {

        int fps = 0;
        int upd = 0;
        int updl = 0;

        int count = 0;

        float delta = 0;

        long lastTime = Time.get();
        while (running) {
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }
            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (count >= Time.SECOND) {
                Display.SetTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                upd = 0;
                fps = 0;
                upd = 0;
                count = 0;
            }
        }
    }

}
