package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChatCommandAvatar extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected @NotNull String getName() {
        return "avatar";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Gets the profile picture of a server member by mentioning him";
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                BotCommand.ICategories.UTIL,
                // Auxiliary categories
                BotCommand.ICategories.CHAT
        };
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0 || !utilsChat.isMention(param.args[0])) {
            utilsChat.sendInfo(param.message.getChannel(), "**How am I supposed to know the dude, if you don't even mention him?**");
            return;
        }

        Member member = utilsChat.getMemberByMention(param.args[0], param.message.getGuild());
        if (member == null) {
            utilsChat.sendInfo(param.message.getChannel(), "**Could not get User for some reason**\nTry to make him send a message or join a voice channel");
            return;
        }

        User user = member.getUser();
        String url = user.getAvatarUrl();
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        if (url == null)
            builder.setDescription("**That guy seems to not have a profile picture!**");
        else
            builder.setImage(url);

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
