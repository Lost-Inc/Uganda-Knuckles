package at.lost_inc.ugandaknucklesbot.Commands.API;

import at.lost_inc.ugandaknucklesbot.Util.Author;

import java.lang.annotation.*;

/**
 * Specifies properties of a command class.
 * <br>
 * <b>Every command class must be annotated with this!</b>
 */
@Author("sudo200")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {
    /**
     * @return The name of the command
     */
    String name();

    /**
     * @return The help for the command
     */
    String help();

    /**
     * @return An array of category strings from {@link BotCommand.ICategories}
     */
    String[] categories();

    /**
     * Can be omitted, if no aliases are desired.
     *
     * @return An array of aliases for the command;
     */
    String[] aliases() default {};
}
