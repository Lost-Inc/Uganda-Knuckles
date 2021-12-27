package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ChatCommandClear extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                BotCommand.ICategories.MODERATION,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.UTIL
        };
    }

    @Override
    protected @NotNull String getName() {
        return "clear";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Deletes messages\n\nWhat do you expect?";
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "**How many do ja want to delete?**");
            return;
        }

        if (!Objects.requireNonNull(param.message.getGuild().getMember(param.message.getAuthor())).hasPermission(Permission.MESSAGE_MANAGE)) {
            utilsChat.sendInfo(param.message.getChannel(), "**You are not pog enough to use this!**");
            return;
        }

        if (!Objects.requireNonNull(param.message.getGuild().getMember(param.message.getJDA().getSelfUser())).hasPermission(Permission.MESSAGE_MANAGE)) {
            utilsChat.sendInfo(param.message.getChannel(), "**I don't have permission to be pog!**");
            return;
        }

        final MessageChannel channel = param.message.getChannel();
        final String countStr = param.args[0];
        int count;

        try {
            if (countStr.toLowerCase().startsWith("0x"))
                count = Integer.parseInt(countStr.substring(2), 16);
            else if (countStr.toLowerCase().startsWith("0o"))
                count = Integer.parseInt(countStr.substring(2), 8);
            else if (countStr.toLowerCase().startsWith("0b"))
                count = Integer.parseInt(countStr.substring(2), 2);
            else if (countStr.equalsIgnoreCase("all"))
                count = 99;
            else
                count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            utilsChat.sendInfo(channel, "**\"" + countStr + "\" is not a valid number!**\n\nas far as I know :confused:");
            return;
        }

        try {
            channel.getIterableHistory().takeAsync(count + 1).thenAccept(channel::purgeMessages);
        } catch (IllegalArgumentException e) {
            utilsChat.sendInfo(channel, "**Sry, I cannot erase the world!**\nOnly 99 messages!");
        }
    }
}
