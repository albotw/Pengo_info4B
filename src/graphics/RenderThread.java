package graphics;

import gameplay.GameController;

import java.awt.*;

import static config.CONFIG.*;

public class RenderThread extends Thread {

    private boolean running;

    private FPSCounter fps;
    private Window w;
    private SpriteManager sm;

    //private GameOverlay go;

    private boolean client;

    public RenderThread() {
        this.w = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.sm = SpriteManager.createSpriteManager(GameController.instance.getMap());
        fps = new FPSCounter();

        //go = new GameOverlay();
        w.setLayout(new BorderLayout());
        w.add(sm, BorderLayout.CENTER);
        //w.add(go, BorderLayout.NORTH);

        running = true;
        w.revalidate();
    }

    public void setClient(boolean val) {
        this.client = val;
    }

    public void run() {
        System.out.println("--- RenderThread started ---");
        fps.start();
        while (running) {
            fps.frame();
            w.setTitle(WINDOW_TITLE + " | FPS: " + fps.get());
            w.repaint();
            //go.repaint();

            if (!client) {
                //go.updateLocal();
            }
            try {
                sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("--- Arrêt RenderThread ---");
        sm.cleanSprites();
        w.setVisible(false);
        w.dispose();

        fps = null;
        w = null;
        //go = null;
    }

    public void stopRendering() {
        running = false;
        fps.stop();
    }

    public Window getWindow() {
        return this.w;
    }

    /*
    public GameOverlay getGameOverlay() {
        return this.go;
    }*/
}
