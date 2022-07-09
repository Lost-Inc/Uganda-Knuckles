package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.RandomFoodAPIResponse;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "food",
        help = "Serves random pictures of various foods\n" +
                "Yes, you can call it \"foodporn\" if you want",
        categories = {
                // Main category
                BotCommand.ICategories.IMAGE,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.FUN, BotCommand.ICategories.INTERNET
        }
)
public final class ChatCommandFood extends BotCommand {
    private UtilsChat utilsChat;
    private Gson gson;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        gson = ServiceManager.provideUnchecked(Gson.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        final OkHttpClient client = param.message.getJDA().getHttpClient();
        final Request req = new Request.Builder()
                .url("https://foodish-api.herokuapp.com/api")
                .build();

        try {
            final Response res = client.newCall(req).execute();
            final RandomFoodAPIResponse randomFoodAPIResponse = gson.fromJson(res.body().charStream(), RandomFoodAPIResponse.class);
            builder.setImage(randomFoodAPIResponse.image);
            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Failed to retrieve food!**"
            );
        }
    }
}
