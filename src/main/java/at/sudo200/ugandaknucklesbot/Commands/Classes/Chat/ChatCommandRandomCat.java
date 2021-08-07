package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomCatAPIResponse;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ChatCommandRandomCat extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Gson gson = new Gson();

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
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://aws.random.cat/meow").body();
        try {
            RandomCatAPIResponse randomCatAPIResponse = gson.fromJson(response, (Type) RandomCatAPIResponse.class);
            builder.setImage(randomCatAPIResponse.file);
            utilsChat.send(param.message.getChannel(), builder.build());
        }
        catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Something went severely wrong**\nBlame your neighbor!\n\n" + e
            );
        }

    }
}
