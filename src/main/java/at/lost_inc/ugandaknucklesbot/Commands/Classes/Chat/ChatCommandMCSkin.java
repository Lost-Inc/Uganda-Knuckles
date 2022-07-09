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
    private UtilsChat utilsChat;
    private Random random;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        random = ServiceManager.provideUnchecked(Random.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
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
