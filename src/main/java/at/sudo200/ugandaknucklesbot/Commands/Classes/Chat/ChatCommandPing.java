package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ChatCommandPing extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Random random = new Random();

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                CommandCategories.MISC,
                // Auxiliary categories
                CommandCategories.CHAT, CommandCategories.UTIL
        };
    }

    @Override
    protected @NotNull String getName() {
        return "ping";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Pings the bot.\n(in a technical sense, not like discord ping :no_mouth:)";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final String normal = "**`Pong!`**";
        final String special =
                "```\n" +
                        " ____                   _ \n" +
                        "|  _ \\ ___  _ __   __ _| |\n" +
                        "| |_) / _ \\| '_ \\ / _` | |\n" +
                        "|  __/ (_) | | | | (_| |_|\n" +
                        "|_|   \\___/|_| |_|\\__, (_)\n" +
                        "                  |___/   " +
                        "```";

        utilsChat.sendInfo(param.message.getChannel(), random.nextInt(500) == 0 ? special : normal);
    }
}
