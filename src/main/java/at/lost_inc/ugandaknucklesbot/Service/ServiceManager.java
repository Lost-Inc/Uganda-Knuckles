package at.lost_inc.ugandaknucklesbot.Service;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Manages instances of service classes.
 * A service class is any class, which provides utility
 * functions. A service may store data in its instance.
 * <p>
 * <p>
 * Services can consist of a simple class,
 * more complicated services, which others may wish
 * to provide their own implementation for, typically
 * have an interface with method definitions, and a class
 * with its default implementation.
 */
@Author("sudo200")
public final class ServiceManager {
    /**
     * Stores service instances by their class.
     */
    private static final HashMap<Class<?>, Object> services = new HashMap<>();

    private ServiceManager() {
    }

    /**
     * Maps a provider for a service to a class.
     *
     * @param service  the service class
     * @param provider the service class instance
     * @throws ClassCastException if the provider is not compatible with the service-class
     */
    public synchronized static <T> void setProvider(@NotNull Class<T> service, @NotNull T provider) throws ClassCastException {
        if (provider.getClass().isInstance(service))
            throw new ClassCastException(provider.getClass().getName() + " is not compatible with " + service.getName() + '!');
        services.put(service, provider);
    }

    /**
     * Gets a service by its class.
     * If there is no service instance for this class,
     * it returns {@link Optional#empty()}.
     *
     * @param service the service class to get
     * @return an {@link Optional}, which may contain an instance of the service
     */
    public static <T> @NotNull Optional<T> provide(Class<T> service) {
        if (!services.containsKey(service))
            return Optional.empty();
        return Optional.ofNullable((T) services.get(service));
    }

    /**
     * Like {@link ServiceManager#provide(Class)}, but directly returns
     * the service instance.
     * Should only be used on services, which are 100% existent!
     *
     * @param service the service class to get
     * @return service instance
     * @throws NoSuchElementException Thrown, when there is no service for this class.
     */
    public static <T> @NotNull T provideUnchecked(Class<T> service) throws NoSuchElementException {
        return provide(service).get();
    }
}
