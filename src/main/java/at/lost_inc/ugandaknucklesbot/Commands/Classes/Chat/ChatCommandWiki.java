package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.WikipediaSummaryRestAPI;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Command(
        name = "wiki",
        help = "Search Wikipedia\n" +
                "Perfect for proving your friends wrong!",
        categories = {
                // Main category
                BotCommand.ICategories.SEARCH,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.INTERNET
        }
)
public final class ChatCommandWiki extends BotCommand {
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private Gson gson;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {// If the user didn't specify an article
            param.args = new String[1];// we know what to do B)
            param.args[0] = "Getting_lost";
        }

        try {
            final OkHttpClient client = param.message.getJDA().getHttpClient();
            final EmbedBuilder builder = utilsChat.getDefaultEmbed();
            final Request req = new Request.Builder()
                    .url("https://en.wikipedia.org/api/rest_v1/page/summary/" + URLEncoder.encode(String.join(" ", param.args), StandardCharsets.UTF_8.toString()))
                    .header("Api-User-Agent", "gratzerfabian92@gmail.com")
                    .build();
            Response res = client.newCall(req).execute();

            switch (res.code()) {
                case 200:// Page exists
                    WikipediaSummaryRestAPI response = gson.fromJson(res.body().charStream(), WikipediaSummaryRestAPI.class);
                    if (!response.type.equalsIgnoreCase("standard")) {
                        utilsChat.sendInfo(
                                param.message.getChannel(),
                                "**Sry, but this site is a special site, which I cannot show you!**"
                        );
                        return;
                    }

                    builder.setTitle(response.titles.display.replaceAll("<.*?>", ""));
                    builder.setDescription(
                            (
                                    (response.description != null ? "**" + response.description + "**\n\n" : "") +
                                            response.extract
                            ).replaceAll("<.*?>", "")
                    );
                    if (response.thumbnail != null)
                        builder.setImage(response.thumbnail.source);

                    utilsChat.send(param.message.getChannel(), builder.build());
                    break;

                case 404:// Page does not exist
                    utilsChat.sendInfo(
                            param.message.getChannel(),
                            "**Seems like there is no article about \"" + String.join(" ", param.args) + "\" on wikipedia (yet).**"
                    );
                    break;

                default:// Everything else
                    utilsChat.sendInfo(
                            param.message.getChannel(),
                            "**Something went wrong!**\nBlame your intellectual if you have one!"
                    );
                    break;
            }
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Oh no!**\n\nSomething went severely wrong!"
            );
            e.printStackTrace();
        }
    }
}
