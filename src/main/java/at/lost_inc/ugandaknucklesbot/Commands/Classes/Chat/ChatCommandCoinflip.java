package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;


import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


@Command(
        name = "coinflip",
        help = "Flips a coin\n\nWhat did you expect? Printing money?",
        categories = {
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.IMAGE, BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandCoinflip extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Random rand = ServiceManager.provideUnchecked(Random.class);

    @Override
    public void execute(@NotNull CommandParameter param) {
        final boolean heads = rand.nextBoolean();
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();

        builder.setTitle(heads ? "Heads" : "Tails");

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
