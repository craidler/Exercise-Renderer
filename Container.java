package JNR;

import java.awt.event.KeyEvent;

public class Container implements Runnable {
    private Input input = Input.instance;
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private AbstractGame game;
    private boolean running = false;
    private final double UPDATE_CAP = 1.0 / 60.0;
    private int width = 320, height = 240;
    private float scale = 2f;
    private String title = "Engine v1.0";

    public Container(AbstractGame game) {
        this.game = game;
    }

    public void dispose() {

    }

    public void run() {
        running = true;

        boolean render;

        double timeFrame = 0;
        double timeFirst;
        double timeLast = System.nanoTime() / 1000000000.0;
        double timePassed;
        double timeUnprocessed = 0;

        int frames = 0;
        int fps = 0;

        while (running) {
            render = false;

            timeFirst = System.nanoTime() / 1000000000.0;
            timePassed = timeFirst - timeLast;
            timeLast = timeFirst;
            timeFrame += timePassed;
            timeUnprocessed += timePassed;

            while (timeUnprocessed >= UPDATE_CAP) {
                timeUnprocessed -= UPDATE_CAP;
                render = true;

                game.update(this, (float) UPDATE_CAP);
                input.update();

                if (timeFrame >= 1.0) {
                    timeFrame = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (render) {
                renderer.clear();
                game.render(this, renderer);
                renderer.drawText("Engine v1.0 FPS " + fps + " " + input.getMouseX() + "/" + input.getMouseY() + " GIMME AN A: " + input.isKey(KeyEvent.VK_A), 1, 1, 0xff00ff00);
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        dispose();
    }

    public void start() {


        thread = new Thread(this);
        window = new Window(this);
        renderer = new Renderer(this);

        input.initialize(this);
        thread.run();
    }

    public void stop() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Input getInput() {
        return input;
    }

    public float getScale() {
        return scale;
    }

    public String getTitle() {
        return title;
    }

    public Window getWindow() {
        return window;
    }
}
