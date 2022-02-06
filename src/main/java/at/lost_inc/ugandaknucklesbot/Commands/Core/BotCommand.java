package at.lost_inc.ugandaknucklesbot.Commands.Core;

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
    protected String @Nullable [] getAliases() {
        return null;
    }

    /**
     * Returns category strings
     * of the categories the command belongs to
     *
     * @return Array of category strings
     * @author sudo200
     * @see BotCommand.ICategories
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

    /**
     * Interface containing categories for bot commands
     *
     * @author sudo200
     */
    public interface ICategories {
        String UTIL = ":tools: Utility";
        String CHAT = ":speech_balloon: Chat";
        String GAME = ":game_die: Games";
        String IMAGE = ":frame_photo: Image";
        String FUN = ":rofl: Fun";
        String INTERNET = ":globe_with_meridians: Internet";
        String MISC = ":sparkles: Misc";
        String MODERATION = ":cop: Moderation";
        String SEARCH = ":mag: Search";
        String VOICE = ":microphone2: Voice";
    }
}
