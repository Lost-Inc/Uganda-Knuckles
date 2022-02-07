package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.UrbanDictionaryAPIResponse;
import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;


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
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Gson gson = ServiceManager.provideUnchecked(Gson.class);

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Mate, please, what do you want me to search?");
            return;
        }

        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://api.urbandictionary.com/v0/define", true, "term", String.join(" ", param.args)).body();
        try {
            UrbanDictionaryAPIResponse urbanDictionaryAPIResponse = gson.fromJson(response, (Type) UrbanDictionaryAPIResponse.class);

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
                    "**Something went severely wrong**\nBlame your neighbor!\n\n" + e
            );
        }


    }
}
