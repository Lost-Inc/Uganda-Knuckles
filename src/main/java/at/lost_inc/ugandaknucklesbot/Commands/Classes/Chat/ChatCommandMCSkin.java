package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;


@Command(
        name = "mc-skin",
        help = "Grabs the skin of a Minecraft player\n" +
                "by name or UUID\n" +
                "**Important:** Nonexistent players will appear to have the Steve skin",
        categories = {
                // Main category
                BotCommand.ICategories.MISC,
                // Auxiliary categories
                BotCommand.ICategories.CHAT, BotCommand.ICategories.INTERNET, BotCommand.ICategories.IMAGE
        },
        aliases = {
                "mcs"
        }
)
public final class ChatCommandMCSkin extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Random random = ServiceManager.provideUnchecked(Random.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    (random.nextInt(500) == 1) ? "Pwease teww me the pwayew nyame ow uuid U\u03C9U" : "Please tell me the player name or uuid!"
            );
            return;
        }
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setTitle("Download", "https://minotar.net/download/" + param.args[0]);
        builder.setImage("https://minotar.net/armor/body/" + param.args[0] + "/512.png");
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
