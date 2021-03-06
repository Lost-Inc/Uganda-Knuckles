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
        name = "coinflip",
        help = "Flips a coin\n\nWhat did you expect? Printing money?",
        categories = {
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.IMAGE, BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandCoinflip extends BotCommand {
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private Random rand;

    @Override
    public void execute(@NotNull CommandParameter param) {
        final boolean heads = rand.nextBoolean();
        final EmbedBuilder builder = utilsChat.getDefaultEmbed();

        builder.setTitle(heads ? "Heads" : "Tails");
        builder.setImage(
                heads ?
                        "https://cdn.discordapp.com/attachments/975045626549108786/975051628715601990/Coin_heads.png" :
                        "https://cdn.discordapp.com/attachments/975045626549108786/975051628954652732/Coin_tails.png"
        );

        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
