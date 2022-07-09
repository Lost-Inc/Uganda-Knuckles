package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomDogAPIResponse;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

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
    private UtilsChat utilsChat;
    private Gson gson;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        gson = ServiceManager.provideUnchecked(Gson.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final OkHttpClient client = param.message.getJDA().getHttpClient();
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        final Request req = new Request.Builder()
                .url("https://random.dog/woof.json")
                .build();

        try {
            final Response res = client.newCall(req).execute();
            final RandomDogAPIResponse randomDogAPIResponse = gson.fromJson(res.body().charStream(), RandomDogAPIResponse.class);
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
