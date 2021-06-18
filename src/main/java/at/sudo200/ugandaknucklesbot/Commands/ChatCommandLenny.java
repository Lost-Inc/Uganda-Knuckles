package at.sudo200.ugandaknucklesbot.Commands;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class ChatCommandLenny extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String setName() {
        return "lenny";
    }

    @Override
    protected void execute(CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setTitle("( \u0361° \u035c\u0296 \u0361 °)");

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
