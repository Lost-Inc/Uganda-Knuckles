package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.reddit.Subreddit;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChatCommandReddit extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Gson gson = ServiceManager.provideUnchecked(Gson.class);

    /**
     * Returns aliases for the command.
     * When no aliases are defined, null is returned instead
     *
     * @return Array of alias strings or null
     * @author sudo200
     */
    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    /**
     * Returns category strings
     * of the categories the command belongs to
     *
     * @return Array of category strings
     * @author sudo200
     * @see ICategories
     */
    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                ICategories.INTERNET
        };
    }

    /**
     * @return Command name
     * @author sudo200
     */
    @Override
    protected @NotNull String getName() {
        return "reddit";
    }

    /**
     * @return Help page describing the command
     * @author sudo200
     */
    @Override
    protected @NotNull String getHelp() {
        return "Gets the topmost post from a subreddit";
    }

    /**
     * Method, which contains the logic for this command
     *
     * @param param Object containing the command args and the message object
     * @author sudo200
     * @see CommandParameter
     */
    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {// If the user didn't specify a subreddit
            param.args = new String[1];// we know what to do B)
            param.args[0] = "r/whoosh";
        }

        if (!param.args[0].startsWith("r/"))
            param.args[0] = "r/" + param.args[0];

        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        final String jsonString = HttpRequest.get("https://www.reddit.com/" + String.join(" ", param.args) + ".json")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.3538.77 Safari/537.36").body();

        Subreddit.PostData postData;

        try {
            postData = gson.fromJson(jsonString, Subreddit.class).data.children[0].data;
        } catch (RuntimeException e) {
            utilsChat.sendInfo(param.message.getChannel(), "It seem, as if there is no subreddit called \"" + String.join(" ", param.args) + "\"");
            return;
        }

        builder.setTitle(postData.title);
        builder.setDescription(postData.selftext.length() < 2048 ? postData.selftext : postData.selftext.substring(0, 2045) + "...");
        builder.setAuthor(postData.author, "https://reddit.com/u/" + postData.author);
        builder.setImage(postData.url);

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
