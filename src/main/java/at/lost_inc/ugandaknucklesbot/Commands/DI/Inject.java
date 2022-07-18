package at.lost_inc.ugandaknucklesbot.Commands.DI;

import java.lang.annotation.*;

/**
 * Automatically fills fields inside command classes
 * with their value.
 * <p>
 * Useful for getting core services easily.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
}
