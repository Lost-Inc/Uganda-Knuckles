package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import de.bill.owo.OwOfy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;


@Command(
        name = "owo",
        help = "Converts text into OwO!",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT,
        }
)
public final class ChatCommandOwO extends BotCommand {
    private final Random random = ServiceManager.provideUnchecked(Random.class);
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "I nyeed text to convewt! (つ✧ω✧)つ");
            return;
        }

        utilsChat.sendInfo(param.message.getChannel(), OwOfy.owofy(String.join(" ", param.args)));
    }
}
