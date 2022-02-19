package at.lost_inc.ugandaknucklesbot.Service.Event;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

@Author("sudo200")
public interface EventBusService {
    /**
     * Registers a new event emitter
     * @param clazz
     * @param eventEmitter
     * @throws AlreadySetException
     */
    <T> void register(@NotNull Class<T> clazz, @NotNull EventEmitter<T> eventEmitter) throws AlreadySetException;

    @NotNull <T> Optional<EventEmitter<T>> remove(@NotNull Class<T> clazz);

    @NotNull <T> Optional<EventEmitter<T>> provide(@NotNull Class<T> clazz);

    @NotNull <T> EventEmitter<T> provideUnchecked(@NotNull Class<T> clazz) throws NoSuchElementException;


    class EventEmitter<T> {
        private final Map<String, List<Consumer<T>>> events = new HashMap<>();

        public void emit(@NotNull String name, T type) {
            for(Consumer<T> element : listeners(name))
                element.accept(type);
        }

        public void on(@NotNull String name, @NotNull Consumer<T> function) {
            if(!events.containsKey(name)) {
                final List<Consumer<T>> list = new ArrayList<>();
                list.add(function);
                events.put(name, list);
                return;
            }

            events.get(name).add(function);
        }

        public int listenerCount(@NotNull String name) {
            return listeners(name).size();
        }

        public @NotNull List<Consumer<T>> listeners(@NotNull String name) {
            return events.get(name);
        }

        @Contract(value = " -> new", pure = true)
        public @NotNull List<String> eventNames() {
            return new ArrayList<>(events.keySet());
        }

        public void removeAllListeners(@NotNull String name) {
            events.remove(name);
        }

        public boolean removeListener(@NotNull String name, @NotNull Consumer<T> function) {
            return events.get(name).remove(function);
        }
    }

    class AlreadySetException extends RuntimeException {
        public AlreadySetException(String name) { super(name); }
        public AlreadySetException(Throwable throwable) { super(throwable); }
        public AlreadySetException(String name, Throwable throwable) { super(name, throwable); }
    }
}
