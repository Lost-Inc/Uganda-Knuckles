package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.reddit.Subreddit;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


@Command(
        name = "reddit",
        help = "Gets the topmost post from a subreddit",
        categories = {
                BotCommand.ICategories.INTERNET,
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandReddit extends BotCommand {
    private UtilsChat utilsChat;
    private Gson gson;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        gson = ServiceManager.provideUnchecked(Gson.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {// If the user didn't specify a subreddit
            param.args = new String[1];// we know what to do B)
            param.args[0] = "r/whoosh";
        }

        if (!param.args[0].startsWith("r/"))
            param.args[0] = "r/" + param.args[0];

        final OkHttpClient client = param.message.getJDA().getHttpClient();
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        final Request req = new Request.Builder()
                .url("https://www.reddit.com/" + String.join(" ", param.args) + ".json")
                .addHeader("UserAgent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.3538.77 Safari/537.36")
                .build();

        Response res;
        try {
            res = client.newCall(req).execute();
        } catch (IOException e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "Couldn't get info, maybe they don't like us?"
            );
            return;
        }

        Subreddit.PostData postData = null;

        try {
            postData = gson.fromJson(res.body().charStream(), Subreddit.class).data.children[0].data;
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
