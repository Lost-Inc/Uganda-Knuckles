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

public class ChatCommandRandomCat extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String getName() {
        return "cat";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Imports cute little pussies from the internet\n" +
                "we mean cats of course";
    }

    @Override
    protected void execute(CommandParameter param) {
        JSONObject object;
        String url;
        JSONParser parser = new JSONParser();
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://aws.random.cat/meow").body();
        try {
            object = (JSONObject) parser.parse(response);
            url = (String) object.get("file");
        }
        catch (Exception e) {
            System.err.println("Ouch:");
            e.printStackTrace();
            utilsChat.sendInfo(param.message.getChannel(), "**Something went severely wrong**\nBlame your neighbor!");
            return;
        }
        builder.setImage(url);
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
