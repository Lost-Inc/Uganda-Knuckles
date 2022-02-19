package at.lost_inc.ugandaknucklesbot.Service.Event;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Author("sudo200")
public interface EventListenerService extends Consumer<EventListener>, Supplier<EventListener[]> {
    default void registerListener(EventListener @NotNull ... listeners) {
        for (EventListener listener : listeners)
            accept(listener);
    }
}
