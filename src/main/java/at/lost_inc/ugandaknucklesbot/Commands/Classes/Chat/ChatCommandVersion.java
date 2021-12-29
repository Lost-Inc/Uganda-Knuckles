package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.TeamMember;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ChatCommandVersion extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    private static final String botVersion =
            ChatCommandVersion.class.getPackage().getImplementationVersion() == null ?
                    "Development Version" :
                    "v" + ChatCommandVersion.class.getPackage().getImplementationVersion();
    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                BotCommand.ICategories.MISC,
                BotCommand.ICategories.CHAT
        };
    }

    @Override
    protected @NotNull String getName() {
        return "version";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Prints out information about the currently running backend, for those who are interested\n\nprobably nobody";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        utilsChat.sendInfo(param.message.getChannel(), "Getting info...", msg ->
                msg.getJDA().retrieveApplicationInfo().queue(info -> {
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
                }));
    }
}
