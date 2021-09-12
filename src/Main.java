import gameplay.GameController;

public class Main {
    public static void init()
    {

    }
    public static void main(String[] args)
    {
        GameController gc = GameController.createGameController();
        gc.start();
    }
}
