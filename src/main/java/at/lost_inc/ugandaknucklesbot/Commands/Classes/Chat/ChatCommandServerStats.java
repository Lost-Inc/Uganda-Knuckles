package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class ChatCommandServerStats extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                ICategories.UTIL,
                ICategories.CHAT,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "serverstats";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Boring statistic about the server";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final List<Member> members = guild.getMembers();
        final int all = members.size();
        final int boosters = guild.getBoosters().size();
        final int bots = getBots(members).size();


        utilsChat.send(
                param.message.getChannel(),
                utilsChat.getDefaultEmbed()
                        .setTitle(String.format("Statistics for \"%s\"", guild.getName()))
                        .setThumbnail(guild.getIconUrl())
                        .addField("Percentages", String.format(
                                "%d members, maximum of %d members, filled to %.4f%%\n" +
                                        "%d members are bots, so %.4f%% are bots\n" +
                                        "%d members are boosters, so %.3f%% boost the server\n",
                                all, guild.getMaxMembers(), ((float) all / (float) guild.getMaxMembers()) * 100.0,
                                bots, ((float) bots / (float) all) * 100.0,
                                boosters, ((float) boosters / (float) all) * 100.0
                        ), false)
                        .addField("Other stuff", String.format(
                                "This server is %d years and %d months old\n" +
                                        "It has %d boosts, which brings it to level %d",
                                OffsetDateTime.now().getYear() - guild.getTimeCreated().getYear(),
                                OffsetDateTime.now().getMonthValue() - guild.getTimeCreated().getMonthValue(),
                                guild.getBoostCount(), guild.getBoostTier().getKey()
                        ), false).build()
                );
    }


    private @NotNull List<Member> getBots(@NotNull List<Member> members) {
        return members.stream().filter(m -> m.getUser().isBot()).collect(Collectors.toList());
    }
}
