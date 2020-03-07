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
    }
}
