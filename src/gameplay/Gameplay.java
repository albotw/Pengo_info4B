package gameplay;

public interface Gameplay {
    public GameController controller = GameController.createGameController();

    default void init()
    {
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    public void initDiamondBlocks();
    public void checkDiamondBlocks();
    public void initPlayers();
    public void initIA();

    public void respawnAnimal(MapObjectController owner);
    public void respawnPenguin(MapObjectController owner);

    public void onAnimalKilled(MapObjectController owner, MapObjectController killer);
    public void onPenguinKilled(MapObjectController owner, MapObjectController killer);
    public void onStunTriggered(char dirMur);

    public void stop();
    public void clean();
}
