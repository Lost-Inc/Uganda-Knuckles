package at.lost_inc.ugandaknucklesbot.Service.Event;

import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SimpleEventListenerService implements EventListenerService {
    private final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void accept(EventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public void registerListener(EventListener @NotNull ... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    @Override
    public EventListener[] get() {
        return listeners.toArray(new EventListener[0]);
    }
}
