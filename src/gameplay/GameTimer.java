package gameplay;

/**
 * TODO: Permettre l'utilisation du timer comme chrono ou compteur. ? A voir
 */
public class GameTimer extends Thread {
    private boolean stop;
    private int time;

    public GameTimer() {
        time = 0;
    }

    public void run() {
        while (stop == false) {
            time++;
            try {
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(" --- timer stopped ---");
    }

    public void stopTimer() {
        stop = true;
    }

    public int getTime() {
        return this.time;
    }
}
