package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.MonospaceConverter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "monospace",
        help = "Converts text, nicknames, and even channel names to monospace",
        categories = {BotCommand.ICategories.UTIL, BotCommand.ICategories.CHAT}
)
public class ChatCommandMonospace extends BotCommand {
    @Inject
    UtilsChat utilsChat;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "**Give me some Text, or mention a user or channel**");
            return;
        }

        if (utilsChat.isUserMention(param.args[0])) {
            final Member member = utilsChat.getMemberByMention(param.args[0], param.message.getGuild());
            if (!utilsChat.sameUser(param.message.getAuthor(), member.getUser()) && !member.hasPermission(Permission.NICKNAME_MANAGE)) {
                userNotPermitted(param.message.getChannel());
                return;
            }

            if (!param.message.getGuild().getMember(param.message.getJDA().getSelfUser()).hasPermission(Permission.NICKNAME_MANAGE)) {
                selfNotPermitted(param.message.getChannel());
                return;
            }

            try {
                member.modifyNickname(MonospaceConverter.monospaceify(member.getEffectiveName())).queue();
                return;
            } catch (HierarchyException e) {
                hierarchyExceptionHandler(param.message.getChannel());
                return;
            }
        }

        if (utilsChat.isChannelMention(param.args[0])) {
            final MessageChannel channel = utilsChat.getChannelByMention(param.args[0], param.message.getGuild());
            if (!param.message.getGuild().getMember(param.message.getAuthor()).hasPermission(Permission.MANAGE_CHANNEL)) {
                userNotPermitted(param.message.getChannel());
                return;
            }

            if (!param.message.getGuild().getMember(param.message.getJDA().getSelfUser()).hasPermission(Permission.MANAGE_CHANNEL)) {
                selfNotPermitted(param.message.getChannel());
                return;
            }

            try {
                param.message.getGuild().getGuildChannelById(channel.getIdLong()).getManager().setName(
                        MonospaceConverter.monospaceify(channel.getName())
                ).queue();
                return;
            } catch (HierarchyException e) {
                hierarchyExceptionHandler(param.message.getChannel());
                return;
            }
        }

        utilsChat.sendInfo(param.message.getChannel(), MonospaceConverter.monospaceify(String.join(" ", param.args)));
    }

    private void userNotPermitted(@NotNull MessageChannel channel) {
        utilsChat.sendInfo(channel, "**You are not pog enough to use this!**");
    }

    private void selfNotPermitted(@NotNull MessageChannel channel) {
        utilsChat.sendInfo(channel, "**I don't have permission to be pog!**");
    }

    private void hierarchyExceptionHandler(@NotNull MessageChannel channel) {
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setDescription("**That dude is more pog than me, sry** ");
        builder.setImage("https://i1.sndcdn.com/avatars-ypCd5dE5YbGkyF0p-Y59d9w-t500x500.jpg");
        utilsChat.send(channel, builder.build());
    }
}
