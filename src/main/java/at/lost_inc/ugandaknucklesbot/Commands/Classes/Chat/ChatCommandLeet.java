package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Util.Leetify;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "leet",
        help = "Converts text into hacker speech! 1337",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT,
        }
)
public final class ChatCommandLeet extends BotCommand {
    @Inject
    private UtilsChat utilsChat;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "I n33d 73x7 70 c0nv3r7!");
        }

        utilsChat.sendInfo(param.message.getChannel(), Leetify.leetify(String.join(" ", param.args)));
    }
}
