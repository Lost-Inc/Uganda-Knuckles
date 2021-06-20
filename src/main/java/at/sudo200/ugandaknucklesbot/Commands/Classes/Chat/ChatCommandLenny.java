package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class ChatCommandLenny extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String getName() {
        return "lenny";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Prints out lenny\n" +
                "that's all\n" +
                "nothing else";
    }

    @Override
    protected void execute(CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setTitle("( \u0361° \u035c\u0296 \u0361 °)");

        if(param.args.length == 0 || !utilsChat.isMention(param.args[0]))
            utilsChat.send(param.message.getChannel(), builder.build());
        else {
            utilsChat.getMemberByMention(param.args[0], param.message.getGuild())
                .getUser().openPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.sendMessage(builder.build()))
                    .queue();
        }
    }
}
