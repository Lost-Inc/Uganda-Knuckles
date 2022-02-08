package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;


@Command(
        name = "poop",
        help = "Poops (whoops)",
        categories = {
                // Main category
                BotCommand.ICategories.FUN,
                // Auxiliary categories
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandPoop extends BotCommand {
    private UtilsChat utilsChat;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {

        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setTitle(":poop:");

        if (param.args.length == 0 || !utilsChat.isMention(param.args[0]))
            utilsChat.send(param.message.getChannel(), builder.build());
        else {
            User user = utilsChat.getMemberByMention(param.args[0], param.message.getGuild()).getUser();
            if (user.getIdLong() == param.message.getJDA().getSelfUser().getIdLong()) {
                utilsChat.sendInfo(param.message.getChannel(), "Sry, but I cannot poop myself!");
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
