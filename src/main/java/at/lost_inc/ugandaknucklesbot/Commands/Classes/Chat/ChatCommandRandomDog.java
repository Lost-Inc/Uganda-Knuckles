package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomDogAPIResponse;
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
        name = "dog",
        help = "Imports dog pictures from the internet\n" +
                "I know, it ain't cats",
        categories = {
                // Main category
                BotCommand.ICategories.IMAGE,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.FUN, BotCommand.ICategories.INTERNET
        }
)
public final class ChatCommandRandomDog extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Gson gson = ServiceManager.provideUnchecked(Gson.class);

    @Override
    public void execute(@NotNull CommandParameter param) {
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
