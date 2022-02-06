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
