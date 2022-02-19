package at.lost_inc.ugandaknucklesbot.Util;

import java.util.TimerTask;

@Author("sudo200")
public final class TimerTaskRunnable extends TimerTask {
    private final Runnable runnable;

    public TimerTaskRunnable(Runnable function) {
        runnable = function;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
