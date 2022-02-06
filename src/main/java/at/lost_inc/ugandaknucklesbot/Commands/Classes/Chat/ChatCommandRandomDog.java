package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomDogAPIResponse;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public final class ChatCommandRandomDog extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Gson gson = ServiceManager.provideUnchecked(Gson.class);

    @Override
    protected @NotNull String getName() {
        return "dog";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Imports dog pictures from the internet\n" +
                "I know, it ain't cats";
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                BotCommand.ICategories.IMAGE,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.FUN, BotCommand.ICategories.INTERNET
        };
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://random.dog/woof.json").body();
        try {
            RandomDogAPIResponse randomDogAPIResponse = gson.fromJson(response, (Type) RandomDogAPIResponse.class);
            builder.setImage(randomDogAPIResponse.url);
            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Something went severely wrong**\nBlame your neighbor's cat!\n\n" + e
            );
        }
    }
}
