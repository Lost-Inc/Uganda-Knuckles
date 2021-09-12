package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomFoodAPIResponse;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class ChatCommandFood extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Gson gson = new Gson();

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
    protected String @NotNull [] getCategories() {
        return new String[] {
                // Main category
                CommandCategories.IMAGE,
                // Auxiliary categories
                CommandCategories.CHAT, CommandCategories.FUN, CommandCategories.INTERNET
        };
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        String response = HttpRequest.get("https://foodish-api.herokuapp.com/api").body();
        try {
            RandomFoodAPIResponse randomFoodAPIResponse = gson.fromJson(response, (Type) RandomFoodAPIResponse.class);
            builder.setImage(randomFoodAPIResponse.image);
            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Something went severely wrong**\nBlame your cook!\nIf you have one\n\n" + e
            );
        }
    }
}
