package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "lenny",
        help = "Prints out lenny\n" +
                "that's all\n" +
                "nothing else",
        categories = {
                // Main category
                BotCommand.ICategories.FUN,
                // Auxiliary categories
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandLenny extends BotCommand {
    @Inject
    private UtilsChat utilsChat;

    @Override
    public void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setTitle("( \u0361° \u035c\u0296 \u0361 °)");

        if (param.args.length == 0 || !utilsChat.isUserMention(param.args[0]))
            utilsChat.send(param.message.getChannel(), builder.build());
        else {
            User user = utilsChat.getMemberByMention(param.args[0], param.message.getGuild()).getUser();
            if (user.getIdLong() == param.message.getJDA().getSelfUser().getIdLong()) {
                utilsChat.sendInfo(param.message.getChannel(), "Sry, but I cannot talk with myself!");
                return;
            }

            user.openPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.sendMessage(builder.build()))
                    .queue(message -> utilsChat.sendInfo(
                                    param.message.getChannel(),
                                    "**Payload sent!**"
                            ), error ->
                                    utilsChat.sendInfo(
                                            param.message.getChannel(),
                                            "**Could not send private message!**\n" +
                                                    "Is the receiver a bot too?"
                                    )
                    );
        }
    }
}
