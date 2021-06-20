package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ChatCommandUrban extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String getName() {
        return "urban";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Search on Urban Dictionary\n" +
                "*But should you?*";
    }

    @Override
    protected void execute(CommandParameter param) {
        JSONObject object, definition;
        JSONParser parser = new JSONParser();
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://api.urbandictionary.com/v0/define", true, "term", String.join(" ", param.args)).body();
        try {
            object = (JSONObject) parser.parse(response);
            definition = (JSONObject)((JSONArray) object.get("list")).get(0);
        }
        catch (Exception e) {
            System.err.println("Ouch:");
            e.printStackTrace();
            utilsChat.sendInfo(param.message.getChannel(), "**Something went severely wrong**\nBlame your neighbor!");
            return;
        }

        builder.setTitle((String) definition.get("word"), (String) definition.get("permalink"));
        builder.setDescription((String) definition.get("definition"));
        builder.addField("Example", (String) definition.get("example"), false);
        builder.addField("Author", (String) definition.get("author"), false);
        builder.addField(":thumbsup:", ((Long) definition.get("thumbs_up")).toString(), true);
        builder.addField(":thumbsdown:", ((Long) definition.get("thumbs_down")).toString(), true);

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
