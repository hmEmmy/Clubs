package me.emmy.clubs.util;

import lombok.experimental.UtilityClass;
import me.emmy.clubs.Clubs;
import org.bukkit.scheduler.BukkitRunnable;

@UtilityClass
public class TaskUtil {
    /**
     * Run a task asynchronously
     *
     * @param runnable The task to run
     */
    public void runTaskAsync(Runnable runnable) {
        Clubs.getInstance().getServer().getScheduler().runTaskAsynchronously(Clubs.getInstance(), runnable);
    }

    /**
     * Run a task later
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     */
    public void runTaskLater(Runnable runnable, long delay) {
        Clubs.getInstance().getServer().getScheduler().runTaskLater(Clubs.getInstance(), runnable, delay);
    }

    /**
     * Run a task timer with a delay and timer in ticks asynchronously
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param timer    The timer in ticks
     */
    public void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(Clubs.getInstance(), delay, timer);
    }

    /**
     * Run a task timer with a delay and timer in ticks
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param timer    The timer in ticks
     */
    public void runTaskTimer(Runnable runnable, long delay, long timer) {
        Clubs.getInstance().getServer().getScheduler().runTaskTimer(Clubs.getInstance(), runnable, delay, timer);
    }

    /**
     * Run a task
     *
     * @param runnable The task to run
     */
    public void runTask(Runnable runnable) {
        Clubs.getInstance().getServer().getScheduler().runTask(Clubs.getInstance(), runnable);
    }
}