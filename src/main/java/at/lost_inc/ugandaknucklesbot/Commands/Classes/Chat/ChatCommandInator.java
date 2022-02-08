package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;


@Command(
        name = "inator",
        help = "Converts every word into a doofensmirtz quote\n" +
                "*We are not responable for quotes, which aren't grammatically correct* :upside_down:",
        categories = {
                // Main category
                BotCommand.ICategories.FUN,
                // Auxiliary categories
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandInator extends BotCommand {
    private UtilsChat utilsChat;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();

        builder.setTitle(String.format("Behold, the %sinator!", String.join(" ", param.args)));
        utilsChat.send(param.message.getChannel(), builder.build());
    }
}
