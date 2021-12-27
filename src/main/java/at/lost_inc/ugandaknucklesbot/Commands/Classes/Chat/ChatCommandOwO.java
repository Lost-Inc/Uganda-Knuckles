package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import de.bill.owo.OwOfy;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class ChatCommandOwO extends BotCommand {
    private final Random random = ServiceManager.provideUnchecked(Random.class);

    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "owo";
    }

    @Override
    protected @NotNull String getHelp() {
        return random.nextBoolean() ? "Converts text into OwO!" : "Convewts text into OwO! (^.^)";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "I nyeed text to convewt! (つ✧ω✧)つ");
            return;
        }

        utilsChat.sendInfo(param.message.getChannel(), OwOfy.owofy(String.join(" ", param.args)));
    }
}
