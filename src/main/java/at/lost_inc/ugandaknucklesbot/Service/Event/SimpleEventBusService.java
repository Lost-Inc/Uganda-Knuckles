package at.lost_inc.ugandaknucklesbot.Service.Event;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Author("sudo200")
public final class SimpleEventBusService implements EventBusService {
    private final Map<Integer, EventBusService.EventEmitter<?>> events = new HashMap<>();

    @Override
    public <T> void register(@NotNull Class<T> clazz, @NotNull EventBusService.EventEmitter<T> eventEmitter) throws EventBusService.AlreadySetException {
        if(events.containsKey(clazz.hashCode()))
            throw new EventBusService.AlreadySetException("EventEmitter for " + clazz + " already exists!");


        events.put(clazz.hashCode(), eventEmitter);
    }

    @Override
    public @NotNull <T> Optional<EventBusService.EventEmitter<T>> remove(@NotNull Class<T> clazz) {
        return Optional.ofNullable((EventBusService.EventEmitter<T>) events.remove(clazz.hashCode()));
    }

    @Override
    public <T> @NotNull Optional<EventBusService.EventEmitter<T>> provide(@NotNull Class<T> clazz) {
        return Optional.ofNullable((EventBusService.EventEmitter<T>) events.get(clazz.hashCode()));
    }

    @Override
    public <T> EventBusService.@NotNull EventEmitter<T> provideUnchecked(@NotNull Class<T> clazz) throws NoSuchElementException {
        return provide(clazz).get();
    }
}
