package at.sudo200.ugandaknucklesbot.Commands;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;

public class ChatCommandInator extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String setName() {
        return "inator";
    }

    @Override
    protected void execute(CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        builder.setTitle("Behold, the " + param.args[0] + "inator!");
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
