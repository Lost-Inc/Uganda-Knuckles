package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Command(
        name = "kick",
        help = "Yes, it kicks a member of your choice\n\n(not in the balls)",
        categories = {
                BotCommand.ICategories.MODERATION,
                BotCommand.ICategories.CHAT, BotCommand.ICategories.UTIL
        }
)
public final class ChatCommandKick extends BotCommand {
    @Inject
    private UtilsChat utilsChat;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0 || !utilsChat.isMention(param.args[0])) {
            utilsChat.sendInfo(param.message.getChannel(), "You have to say who you want to kick.");
            return;
        }

        if (!Objects.requireNonNull(param.message.getGuild().getMember(param.message.getAuthor())).hasPermission(Permission.KICK_MEMBERS) && !param.message.getAuthor().getId().equals("624243342896660513")) {
            utilsChat.sendInfo(param.message.getChannel(), "**You are not pog enough to use this!**");
            return;
        }

        if (!Objects.requireNonNull(param.message.getGuild().getMember(param.message.getJDA().getSelfUser())).hasPermission(Permission.KICK_MEMBERS)) {
            utilsChat.sendInfo(param.message.getChannel(), "**I don't have permission to be pog!**");
            return;
        }

        try {
            AuditableRestAction<Void> kick = utilsChat.getMemberByMention(param.args[0], param.message.getGuild()).kick();
            if (param.args.length > 1) {
                param.args[0] = "";
                kick = kick.reason(String.join(" ", param.args));
            }
            kick.queue();
        } catch (HierarchyException e) {
            EmbedBuilder builder = utilsChat.getDefaultEmbed();
            builder.setDescription("**That dude is more pog than me, sry** ");
            builder.setImage("https://i1.sndcdn.com/avatars-ypCd5dE5YbGkyF0p-Y59d9w-t500x500.jpg");
            utilsChat.send(param.message.getChannel(), builder.build());
        }


    }
}
