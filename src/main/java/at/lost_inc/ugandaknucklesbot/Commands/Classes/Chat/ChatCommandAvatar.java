package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.API.Inject;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "avatar",
        help = "Gets the profile picture of a server member by mentioning him",
        categories = {
                // Main category
                BotCommand.ICategories.UTIL,
                // Auxiliary categories
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandAvatar extends BotCommand {
    @Inject
    private UtilsChat utilsChat;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0 || !utilsChat.isMention(param.args[0])) {
            utilsChat.sendInfo(param.message.getChannel(), "**Mention the one, who's avatar shall be retrieved**");
            return;
        }

        Member member = utilsChat.getMemberByMention(param.args[0], param.message.getGuild());
        if (member == null) {
            utilsChat.sendInfo(param.message.getChannel(), "**Could not get User for some reason**\nTry make him send a message or join a voice channel");
            return;
        }

        User user = member.getUser();
        String url = user.getAvatarUrl();
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        if (url == null)
            builder.setDescription("**This user seems to not have a profile picture!**");
        else
            builder.setImage(url);

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
