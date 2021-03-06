package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomCatAPIResponse;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "cat",
        help = "Imports cute little pussies from the internet\n" +
                "we mean cats of course",
        categories = {
                // Main category
                BotCommand.ICategories.IMAGE,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.FUN, BotCommand.ICategories.INTERNET
        }
)
public final class ChatCommandRandomCat extends BotCommand {
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private Gson gson;

    @Override
    public void execute(@NotNull CommandParameter param) {
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        final OkHttpClient client = param.message.getJDA().getHttpClient();
        final Request req = new Request.Builder()
                .url("https://aws.random.cat/meow")
                .build();

        try {
            final Response res = client.newCall(req).execute();
            final RandomCatAPIResponse randomCatAPIResponse = gson.fromJson(res.body().charStream(), RandomCatAPIResponse.class);
            builder.setImage(randomCatAPIResponse.file);
            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Failed to get pussies**"
            );
        }

    }
}
