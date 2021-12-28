package at.lost_inc.ugandaknucklesbot.Service;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class ServiceManager {
    private static final HashMap<Class<?>, Object> services = new HashMap<>();

    private ServiceManager() {
    }

    public synchronized static <T> void setProvider(Class<T> service, @NotNull T provider) {
        services.put(service, provider);
    }

    public static <T> Optional<T> provide(Class<T> service) {
        if (!services.containsKey(service))
            return Optional.empty();
        return Optional.ofNullable((T) services.get(service));
    }

    public static <T> @NotNull T provideUnchecked(Class<T> service) throws NoSuchElementException {
        return provide(service).get();
    }
}
