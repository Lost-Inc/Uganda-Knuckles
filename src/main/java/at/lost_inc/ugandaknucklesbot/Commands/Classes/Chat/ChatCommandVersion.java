package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TeamMember;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


@Command(
        name = "version",
        help = "Prints out info about the bot, and credits it's devs",
        categories = {
                BotCommand.ICategories.MISC,
                BotCommand.ICategories.CHAT
        },
        aliases = {
                "v"
        }
)
public final class ChatCommandVersion extends BotCommand {
    private static final String botVersion =
            ChatCommandVersion.class.getPackage().getImplementationVersion() == null ?
                    "Development Version" :
                    "v" + ChatCommandVersion.class.getPackage().getImplementationVersion();
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final Message msg =  utilsChat.sendInfo(param.message.getChannel(), "Getting info...");
        final ApplicationInfo info = msg.getJDA().retrieveApplicationInfo().complete();

        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setDescription(String.format(
                "**%s** %s\n" +
                        "\n" +
                        "**Developer(s):**\n" +
                        "%s\n" +
                        "and contributors",
                info.getJDA().getSelfUser().getName(),
                botVersion,
                info.getTeam() != null ?
                        String.join(
                                "\n",
                                info.getTeam().getMembers().stream()
                                        .map(TeamMember::getUser).map(User::getName)
                                        .map(s -> "+ " + s).toArray(String[]::new)
                        ) :
                        info.getOwner().getName()
        ));
        builder.setThumbnail(info.getJDA().getSelfUser().getAvatarUrl());
        builder.setAuthor(
                info.getTeam() != null ?
                        Objects.requireNonNull(info.getTeam().getOwner()).getUser().getName() :
                        info.getOwner().getName(), null,
                info.getTeam() != null ?
                        info.getTeam().getIconUrl() :
                        info.getOwner().getAvatarUrl()
        );

        msg.editMessage(builder.build()).queue();
    }
}
