package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.EvilInsultAPIResponse;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

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
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Gson gson = ServiceManager.provideUnchecked(Gson.class);

    @Override
    public void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://evilinsult.com/generate_insult.php?lang=en&type=json").body();
        try {
            EvilInsultAPIResponse evilInsultAPIResponse = gson.fromJson(response, EvilInsultAPIResponse.class);
            builder.setAuthor(evilInsultAPIResponse.createdby);
            builder.setDescription(evilInsultAPIResponse.insult);

            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Error** could not load insult\n\nF\\*\\*\\* you!"
            );
        }
    }
}
