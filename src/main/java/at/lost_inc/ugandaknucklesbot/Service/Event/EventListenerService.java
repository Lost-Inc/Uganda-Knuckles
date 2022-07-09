package at.lost_inc.ugandaknucklesbot.Service.Event;

import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface EventListenerService extends Consumer<EventListener>, Supplier<EventListener[]> {
    default void registerListener(EventListener @NotNull ... listeners) {
        for (EventListener listener : listeners)
            accept(listener);
    }
}
