<<<<<<< Updated upstream
import java.util.Timer;
import java.util.TimerTask;

public class Main
{
    public static void main(String[] args)
    {
        Timer chrono = new Timer();        // Fonction Timer pour savoir le temps qu'il reste au joueur
        chrono.schedule(new TimerTask() {

            int time = 90;
            @Override
            public void run() {
                System.out.println("Time : " + time);

                if(time==0){
                    cancel();
                }
                time--;
            }
        }, 1000, 1000);
=======
import com.generic.graphics.*;
import com.generic.graphics.RenderPanel;

public class Main {
    public static void main(String[] args)
    {
        Window w = new Window(800, 600);
        RenderPanel r = new RenderPanel(w);

        Thread thr = new Thread((Runnable)r);

        Sprite spr1 = new Sprite(400, 400);
        spr1.loadImage("src/resources/img1.png");

        r.addToRenderPile(spr1);
        thr.start();
>>>>>>> Stashed changes
    }
}
