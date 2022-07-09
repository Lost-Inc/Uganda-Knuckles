package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;

@Command(
        name = "resolve",
        help = "Performs a dns lookup on the given hostname\n\nYes, I know, its very specific!",
        categories = {
                // Main category
                BotCommand.ICategories.SEARCH,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.INTERNET, BotCommand.ICategories.UTIL,
        }
)
public final class ChatCommandResolve extends BotCommand {
    private UtilsChat utilsChat;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "**What hostname should I resolve?!?**");
            return;
        }

        try {
            EmbedBuilder builder = utilsChat.getDefaultEmbed().setTitle(String.format("IPs for \"%s\"", param.args[0]));
            param.message.getJDA().getHttpClient().dns().lookup(param.args[0]).forEach(inetAddress ->
                    builder.addField("", inetAddress.getHostAddress(), false)
            );
            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (UnknownHostException e) {
            utilsChat.sendInfo(param.message.getChannel(), "That host seems to be non existent!\n\n**Maybe your government didn't like him...**");
        }
    }
}
