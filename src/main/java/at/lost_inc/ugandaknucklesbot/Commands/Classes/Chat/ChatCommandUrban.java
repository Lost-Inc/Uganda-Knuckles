package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.UrbanDictionaryAPIResponse;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Command(
        name = "urban",
        help = "Search on Urban Dictionary\n" +
                "*But should you?*",
        categories = {
                // Main category
                BotCommand.ICategories.SEARCH,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.INTERNET
        }
)
public final class ChatCommandUrban extends BotCommand {
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private Gson gson;

    private static @NotNull String encode(@NotNull String param) {
        try {
            return URLEncoder.encode(param, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "What should I search?");
            return;
        }

        final OkHttpClient client = param.message.getJDA().getHttpClient();
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        final Request req = new Request.Builder()
                .url("https://api.urbandictionary.com/v0/define?term=" + encode(String.join(" ", param.args)))
                .build();

        try {
            final Response res = client.newCall(req).execute();
            final UrbanDictionaryAPIResponse urbanDictionaryAPIResponse = gson.fromJson(res.body().charStream(), UrbanDictionaryAPIResponse.class);

            if (urbanDictionaryAPIResponse.list.length != 0) {
                UrbanDictionaryAPIResponse.DefinitionObject definition = urbanDictionaryAPIResponse.list[0];
                builder.setTitle(definition.word, definition.permalink);
                builder.setDescription(definition.definition);
                builder.addField("Example", definition.example, false);
                builder.addField("Author", definition.author, false);
                builder.addField(":thumbsup:", definition.thumbs_up.toString(), true);
                builder.addField(":thumbsdown:", definition.thumbs_down.toString(), true);
            } else {
                builder.setTitle("Oh no!");
                builder.setDescription("Seems like there is no definition for this!");
                builder.setImage("https://media1.tenor.com/images/22e32eae11bf34c250c716dc88c4ca6a/tenor.gif");
            }

            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Something went severely wrong**\n||Like my life...||"
            );
        }


    }
}
