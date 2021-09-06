package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatCommandClear extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                CommandCategories.UTIL,
                // Auxiliary categories
                CommandCategories.CHAT,
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
            utilsChat.sendInfo(channel, "**Sry, I cannot erase the world!**\nOnly 100 messages!");
        }
    }
}
