package at.sudo200.ugandaknucklesbot.Commands.Core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** abstract class which gets inherited
 *  by every command class
 * @author sudo200
 *
 */
public abstract class BotCommand {
    /** Returns aliases for the command.
     *  When no aliases are defined, null is returned instead
     *  @author sudo200
     *  @return Array of alias strings or null
     */
    protected abstract String @Nullable [] getAliases();

    /** Returns category strings
     *  of the categories the command belongs to
     *  @author sudo200
     *  @see CommandCategories
     *  @return Array of category strings
     */
    protected abstract String @NotNull [] getCategories();

    /**
     *  @author sudo200
     *  @return Command name
     */
    protected abstract @NotNull String getName();

    /**
     *  @author sudo200
     *  @return Help page describing the command
     */
    protected abstract @NotNull String getHelp();

    /** Method, which contains the logic for this command
     *  @author sudo200
     *  @param param Object containing the command args and the message object
     *  @see CommandParameter
     */
    protected abstract void execute(@NotNull CommandParameter param);
}
