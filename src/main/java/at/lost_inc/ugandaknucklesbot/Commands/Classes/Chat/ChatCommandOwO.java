package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import de.bill.owo.OwOfy;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "owo",
        help = "Converts text into OwO!",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT,
        }
)
public final class ChatCommandOwO extends BotCommand {
    @Inject
    private UtilsChat utilsChat;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "I nyeed text to convewt! (つ✧ω✧)つ");
            return;
        }

        utilsChat.sendInfo(param.message.getChannel(), OwOfy.owofy(String.join(" ", param.args)));
    }
}
