package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChatCommandPoop extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @Nullable [] getAliases() {
        return null;

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
    protected @NotNull String getName() {
        return "poop";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Poops (whoops)";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {

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
