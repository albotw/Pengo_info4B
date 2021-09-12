package events;

public enum ThreadID {
    Render,
    Game,
    Controller,
    Launcher,
    Timer,

    BOT_1,
    BOT_2,
    BOT_3,
    BOT_4,
    BOT_5,
    BOT_6,

    LocalPlayer,

    NetworkPlayer_1,
    NetworkPlayer_2,
    NetworkPlayer_3,
    NetworkPlayer_4,
    NetworkPlayer_5,
    NetworkPlayer_6,
    NetworkPlayer_7,
    NetworkPlayer_8;

    private boolean used;

    public boolean isUsed()
    {
        return this.used;
    }

    public void setUsed(boolean value)
    {
        this.used = value;
    }

    public static ThreadID getAvailableBotID()
    {
        for (int i = 1; i < 7; i++)
        {
            ThreadID test = ThreadID.valueOf("BOT_" + i);
            if (!test.isUsed())
                return test;
        }

        return null;
    }
}
