package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.EvilInsultAPIResponse;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Author("sudo200")
@Command(
        name = "insult",
        help = "Insults the channel with a random, but evil insult\n" +
                "...",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandInsult extends BotCommand {
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
                .url("https://evilinsult.com/generate_insult.php?lang=en&type=json").build();
        try {
            final Response res = client.newCall(req).execute();

            final EvilInsultAPIResponse evilInsultAPIResponse = gson.fromJson(res.body().charStream(), EvilInsultAPIResponse.class);
            builder.setAuthor(evilInsultAPIResponse.createdby);
            builder.setDescription(evilInsultAPIResponse.insult);

            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (IOException e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Error** could not load insult\n\nF\\*\\*\\* you!"
            );
        }
    }
}
