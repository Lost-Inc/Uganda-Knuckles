package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.WikipediaSummaryRestAPI;
import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;


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
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Gson gson = ServiceManager.provideUnchecked(Gson.class);

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {// If the user didn't specify an article
            param.args = new String[1];// we know what to do B)
            param.args[0] = "Getting_lost";
        }

        try {
            EmbedBuilder builder = utilsChat.getDefaultEmbed();
            HttpRequest request = HttpRequest.get("https://en.wikipedia.org/api/rest_v1/page/summary/" + URLEncoder.encode(String.join(" ", param.args), "ISO-8859-1"));
            request.header("Api-User-Agent", "gratzerfabian92@gmail.com");
            String jsonString = request.body();

            switch (request.code()) {
                case 200:// Page exists
                    WikipediaSummaryRestAPI response = gson.fromJson(jsonString, (Type) WikipediaSummaryRestAPI.class);
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
