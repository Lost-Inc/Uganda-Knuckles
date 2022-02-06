package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class ChatCommandPing extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Random random = ServiceManager.provideUnchecked(Random.class);

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                BotCommand.ICategories.MISC,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.UTIL
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
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();
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

        builder.setDescription(random.nextInt(10) == 0 ? special : normal);
        builder.addField("Bot <-> Discord", param.message.getJDA().getGatewayPing() + " ms", true);

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
