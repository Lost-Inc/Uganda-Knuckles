package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.UnknownHostException;

public class ChatCommandDNSLookup extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                // Main category
                CommandCategories.SEARCH,
                // Auxiliary categories
                CommandCategories.CHAT, CommandCategories.INTERNET, CommandCategories.UTIL,
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
        if(param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "**What hostname should I resolve?!?**");
            return;
        }

        try {
            EmbedBuilder builder = utilsChat.getDefaultEmbed().setTitle(String.format("IPs for \"%s\"", param.args[0]));
            param.message.getJDA().getHttpClient().dns().lookup(param.args[0]).forEach(inetAddress ->
                    builder.addField("", inetAddress.getHostAddress(), false)
            );
            utilsChat.send(param.message.getChannel(), builder.build());
        }
        catch (UnknownHostException e) {
            utilsChat.sendInfo(param.message.getChannel(), "That host seems to be non existent!\n\n**Maybe your government didn't like him...**");
        }
    }
}
