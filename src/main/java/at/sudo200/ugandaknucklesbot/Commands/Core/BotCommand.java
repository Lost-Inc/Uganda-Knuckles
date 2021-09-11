package at.sudo200.ugandaknucklesbot.Commands.Core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * abstract class which gets inherited
 * by every command class
 *
 * @author sudo200
 */
public abstract class BotCommand {
    /**
     * Returns aliases for the command.
     * When no aliases are defined, null is returned instead
     *
     * @return Array of alias strings or null
     * @author sudo200
     */
    protected abstract String @Nullable [] getAliases();

    /**
     * Returns category strings
     * of the categories the command belongs to
     *
     * @return Array of category strings
     * @author sudo200
     * @see CommandCategories
     */
    protected abstract String @NotNull [] getCategories();

    /**
     * @return Command name
     * @author sudo200
     */
    protected abstract @NotNull String getName();

    /**
     * @return Help page describing the command
     * @author sudo200
     */
    protected abstract @NotNull String getHelp();

    /**
     * Method, which contains the logic for this command
     *
     * @param param Object containing the command args and the message object
     * @author sudo200
     * @see CommandParameter
     */
    protected abstract void execute(@NotNull CommandParameter param);
}
