package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Author({"sudo200", "Lauze1"})
@Command(
        name = "dice",
        help = "magic dice",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandDice extends BotCommand {
    private static final String[] numbers = new String[]{
            " 0000\n" +
                    "00  00\n" +
                    "00  00\n" +
                    "00  00\n" +
                    " 0000",

            "1111\n" +
                    "  11\n" +
                    "  11\n" +
                    "  11\n" +
                    "111111",

            " 2222\n" +
                    "22  22\n" +
                    "   22\n" +
                    "  22\n" +
                    "222222\n",

            " 3333\n" +
                    "33  33\n" +
                    "   333\n" +
                    "33  33\n" +
                    " 3333",

            "44  44\n" +
                    "44  44\n" +
                    "444444\n" +
                    "    44\n" +
                    "    44",

            "555555\n" +
                    "55\n" +
                    "55555\n" +
                    "    55\n" +
                    "55555",

            " 6666\n" +
                    "66\n" +
                    "66666\n" +
                    "66  66\n" +
                    " 6666\n",

            "777777\n" +
                    "   77\n" +
                    "  77\n" +
                    " 77\n" +
                    "77\n",

            " 8888\n" +
                    "88  88\n" +
                    " 8888\n" +
                    "88  88\n" +
                    " 8888",

            " 9999\n" +
                    "99  99\n" +
                    " 99999\n" +
                    "    99\n" +
                    " 9999"
    };
    private UtilsChat utilsChat;
    private Function<Long, Long> random;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        random = ThreadLocalRandom.current()::nextLong;
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "```c\n" + numbers[(int) (random.apply(6L) + 1)] + "\n```"); //Integer.toString( random.nextInt(5+1))
            return;
        }

        final String countStr = param.args[0];
        long dice = Long.parseUnsignedLong(countStr);

        try {
            if (countStr.toLowerCase().startsWith("0x"))
                dice = Long.parseUnsignedLong(countStr.substring(2), 16);
            else if (countStr.toLowerCase().startsWith("0o"))
                dice = Long.parseUnsignedLong(countStr.substring(2), 8);
            else if (countStr.toLowerCase().startsWith("0b"))
                dice = Long.parseUnsignedLong(countStr.substring(2), 2);
        } catch (NumberFormatException e) {
            utilsChat.sendInfo(param.message.getChannel(), "**\"" + countStr + "\" is not a valid number!**\n\nas far as I know :confused:");
            return;
        }

        try {
            long randomnumber = random.apply(dice);
            StringBuilder asciinumber = new StringBuilder("```c\n");
            for (char c : Long.toString(randomnumber).toCharArray()) {
                asciinumber.append(numbers[Integer.parseInt(String.valueOf(c))]);
                asciinumber.append("\n\n");
            }
            asciinumber.append("\n```");

            utilsChat.sendInfo(param.message.getChannel(), asciinumber.toString());
        } catch (NumberFormatException e) {
            utilsChat.sendInfo(param.message.getChannel(), param.args[0] + " is not a number!");
        } catch (IllegalArgumentException e) {
            utilsChat.sendInfo(param.message.getChannel(), "Number has to be positive!\n\nLike your attitude");
        }


    }
}
