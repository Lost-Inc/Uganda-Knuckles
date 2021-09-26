package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand.ICategories;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return new String[]{
                // Main category
                BotCommand.ICategories.FUN,
                // Auxiliary categories
                BotCommand.ICategories.CHAT
        };
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        builder.setTitle(String.format("Behold, the %sinator!", String.join(" ", param.args)));
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
