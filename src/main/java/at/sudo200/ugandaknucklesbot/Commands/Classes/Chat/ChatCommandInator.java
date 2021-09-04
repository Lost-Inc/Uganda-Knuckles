package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class ChatCommandInator extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String getName() {
        return "inator";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Converts every word into a doofensmirtz quote\n" +
                "*We are not responable for quotes, which aren't grammatically correct* :upside_down:";
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]
                // Main category
                {CommandCategories.FUN,
                // Auxiliary categories
                CommandCategories.CHAT};
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        builder.setTitle("Behold, the " + param.args[0] + "inator!");
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
