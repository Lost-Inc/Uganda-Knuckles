package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Leetify;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChatCommandLeet extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "leet";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Converts text into hacker speech! 1337";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "I n33d 73x7 70 c0nv3r7!");
        }

        utilsChat.sendInfo(param.message.getChannel(), Leetify.leetify(String.join(" ", param.args)));
    }
}
