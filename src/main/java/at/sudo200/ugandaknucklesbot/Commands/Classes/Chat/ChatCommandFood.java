package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ChatCommandFood extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String getName() {
        return "food";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Serves random pictures of various foods\n" +
                "Yes, you can call it \"foodporn\" if you want";
    }

    @Override
    protected void execute(CommandParameter param) {
        JSONObject object;
        String url;
        JSONParser parser = new JSONParser();
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://foodish-api.herokuapp.com/api").body();
        try {
            object = (JSONObject) parser.parse(response);
            url = (String) object.get("image");
        }
        catch (Exception e) {
            System.err.println("Ouch:");
            e.printStackTrace();
            utilsChat.sendInfo(param.message.getChannel(), "**Something went severely wrong**\nBlame your cook!\nIf you have one");
            return;
        }
        builder.setImage(url);
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
