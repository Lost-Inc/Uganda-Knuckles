package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ChatCommandBan extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                CommandCategories.MODERATION,
                CommandCategories.CHAT, CommandCategories.UTIL
        };
    }

    @Override
    protected @NotNull String getName() {
        return "ban";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Yes, it bans a member of your choice\n\n(forever, that's a long time)";
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0 || !utilsChat.isMention(param.args[0])) {
            utilsChat.sendInfo(param.message.getChannel(), "You have to say who you want to ban.");
            return;
        }

        if (!Objects.requireNonNull(param.message.getGuild().getMember(param.message.getAuthor())).hasPermission(Permission.BAN_MEMBERS)) {
            utilsChat.sendInfo(param.message.getChannel(), "**You are not pog enough to use this!**");
            return;
        }

        if(!Objects.requireNonNull(param.message.getGuild().getMember(param.message.getJDA().getSelfUser())).hasPermission(Permission.BAN_MEMBERS)) {
            utilsChat.sendInfo(param.message.getChannel(), "**I don't have permission to be pog!**");
            return;
        }

        try {
            AuditableRestAction<Void> ban = utilsChat.getMemberByMention(param.args[0], param.message.getGuild()).ban(0);
            if (param.args.length > 1) {
                param.args[0] = "";
                ban = ban.reason(String.join(" ", param.args));
            }
            ban.queue();
        } catch (HierarchyException e) {
            EmbedBuilder builder = utilsChat.getDefaultEmbed();
            builder.setDescription("**That dude is more pog than me, sry** ");
            builder.setImage("https://i1.sndcdn.com/avatars-ypCd5dE5YbGkyF0p-Y59d9w-t500x500.jpg");
            utilsChat.send(param.message.getChannel(), builder.build());
        }
    }
}
