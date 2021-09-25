package at.lost_inc.ugandaknucklesbot.Util;

import java.util.TimerTask;

public class TimerTaskRunnable extends TimerTask {
    private final Runnable runnable;

    public TimerTaskRunnable(Runnable function) {
        runnable = function;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
