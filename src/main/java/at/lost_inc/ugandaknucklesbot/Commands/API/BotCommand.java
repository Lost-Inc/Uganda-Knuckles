package at.lost_inc.ugandaknucklesbot.Commands.API;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import org.jetbrains.annotations.NotNull;

/**
 * abstract class which gets inherited
 * by every command class
 *
 * @author sudo200
 */
@Author("sudo200")
public abstract class BotCommand {
    /**
     * Called, when bot is in INITIALIZATION phase.
     * <br>
     * You should register services here.
     */
    public void onInitialization() {
    }

    /**
     * Called, when bot is in POST_INITIALIZATION phase.
     * <br>
     * You should get services your command needs here.
     * <br>
     * After this phase, your command should be fully operational.
     */
    public void onPostInitialization() {
    }

    /**
     * Method, which contains the logic for this command
     *
     * @param param Object containing the command args and the message object
     * @author sudo200
     * @see CommandParameter
     */
    public abstract void execute(@NotNull CommandParameter param);

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
