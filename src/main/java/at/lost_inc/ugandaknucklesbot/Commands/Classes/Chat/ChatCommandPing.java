package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Command(
        name = "ping",
        help = "Pings the bot.\n(in a technical sense, not like discord ping :no_mouth:)",
        categories = {
                // Main category
                BotCommand.ICategories.MISC,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.UTIL
        }
)
public final class ChatCommandPing extends BotCommand {
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private Random random;

    @Override
    public void execute(@NotNull CommandParameter param) {
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
