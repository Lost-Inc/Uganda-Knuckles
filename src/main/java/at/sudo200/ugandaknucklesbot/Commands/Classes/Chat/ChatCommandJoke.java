package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ChatCommandJoke extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final String baseUrl = "https://v2.jokeapi.dev/joke/";

    @Override
    protected @NotNull String getName() {
        return "joke";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Get a joke from a category\n" +
                "Available Categories: \n" +
                "**Any**, **Programming**, **Misc**, **Dark**, **Pun**, **Spooky**, **Christmas**";
    }

    @Override
    protected void execute(CommandParameter param) {
        String option = "";
        if(param.args.length == 0)
            option = Category.CATEGORIES[Category.ANY];
        else {
            boolean ok = false;
            for (String category:Category.CATEGORIES) {
                if(category.equalsIgnoreCase(param.args[0])) {
                    ok = true;
                    option = category;
                    break;
                }
            }
            if(!ok) {
                    utilsChat.sendInfo(param.message.getChannel(), param.args[0].equalsIgnoreCase("nikolas") ? "Yes, that's correct" : "That's not a valid category!\n" +
                            "A valid category is one of the following: **Any**, **Programming**, **Misc**, **Dark**, **Pun**, **Spooky**, **Christmas**");
                    return;
            }
        }
        JSONObject object, flags;
        JSONParser parser = new JSONParser();
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get(baseUrl + option).body();
        try {
            object = (JSONObject) parser.parse(response);
            //flags = (JSONObject) object.get("flags");
        }
        catch (Exception e) {
            System.err.println("Ouch:");
            e.printStackTrace();
            utilsChat.sendInfo(param.message.getChannel(), "**Something went severely wrong**\nBlame your ...\nI don't know");
            return;
        }
        if((boolean) object.get("error")) {
            utilsChat.sendInfo(param.message.getChannel(), "**Something went wrong**");
            System.err.println("Joke-API-Error:" + object.toJSONString());
            return;
        }
        builder.setFooter("id: " + object.get("id"));
        if(((String) object.get("type")).equalsIgnoreCase(Type.TYPES[Type.TWOPART]))
            builder.setDescription(
                    object.get("setup") + "\n" +
                            "||" + object.get("delivery") + "||"
            );
        else
            builder.setDescription((String) object.get("joke"));

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}

interface Category {
    String[] CATEGORIES = {"Any", "Programming", "Misc", "Dark", "Pun", "Spooky", "Christmas"};
    byte ANY = 0;
    byte PROGRAMMING = 1;
    byte MISC = 2;
    byte DARK = 3;
    byte PUN = 4;
    byte SPOOKY = 5;
    byte CHRISTMAS = 6;
}

interface Type {
    String[] TYPES = {"single", "twopart"};
    byte SINGLE = 0;
    byte TWOPART = 1;
}
