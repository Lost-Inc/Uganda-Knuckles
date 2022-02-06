package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.UnknownHostException;

public final class ChatCommandResolve extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                BotCommand.ICategories.SEARCH,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.INTERNET, BotCommand.ICategories.UTIL,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "resolve";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Performs a dns lookup on the given hostname\n\nYes, I know, its very specific!";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
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
