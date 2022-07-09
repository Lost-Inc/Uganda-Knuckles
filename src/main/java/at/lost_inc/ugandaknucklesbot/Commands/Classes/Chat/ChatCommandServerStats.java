package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Command(
        name = "serverstats",
        help = "Boring statistic about the server",
        categories = {
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.CHAT,
        }
)
public final class ChatCommandServerStats extends BotCommand {
    private UtilsChat utilsChat;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final Message message = utilsChat.sendInfo(param.message.getChannel(), "Getting info...");

        final Guild guild = param.message.getGuild();
        final List<Member> members = guild.getMembers();
        final Optional<Message> newestMsg = getLastMessage(guild.getChannels(), param.message.getIdLong());
        final int all = members.size();
        final int boosters = guild.getBoosters().size();
        final int bots = getBots(members).size();

        message.editMessage(
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
                                        "It has %d boosts, which brings it to level %d\n" +
                                        (newestMsg.isPresent() ? String.format(
                                                "The last message was created on\n**%s**",
                                                newestMsg.get().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                                        ) : ""),
                                OffsetDateTime.now().getYear() - guild.getTimeCreated().getYear(),
                                OffsetDateTime.now().getMonthValue() - guild.getTimeCreated().getMonthValue(),
                                guild.getBoostCount(), guild.getBoostTier().getKey()
                        ), false).build()
        ).complete();
    }


    private @NotNull List<Member> getBots(@NotNull List<Member> members) {
        return members.stream().filter(m -> m.getUser().isBot()).collect(Collectors.toList());
    }

    private @NotNull Optional<Message> getLastMessage(@NotNull List<GuildChannel> channels, long markerMsg) {
        Message newestMsg = null;
        for (GuildChannel channel : channels) {
            if (!ChannelType.TEXT.equals(channel.getType()))
                continue;

            final TextChannel textChannel = channel.getGuild().getTextChannelById(channel.getIdLong());
            if (textChannel == null)
                continue;
            final List<Message> msgs = textChannel.getHistoryBefore(markerMsg, 1).complete().getRetrievedHistory();
            if (msgs.size() == 0)
                continue;
            if (newestMsg == null || msgs.get(0).getTimeCreated().isAfter(newestMsg.getTimeCreated()))
                newestMsg = msgs.get(0);
        }

        return Optional.ofNullable(newestMsg);
    }
}
