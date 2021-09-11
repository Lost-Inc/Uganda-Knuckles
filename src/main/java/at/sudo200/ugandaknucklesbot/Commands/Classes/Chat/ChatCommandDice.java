package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ChatCommandDice extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Random random = new Random();
    private final String[] numbers = new String[] {
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

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                CommandCategories.UTIL,
                CommandCategories.CHAT
        };
    }

    @Override
    protected @NotNull String getName() {
        return "dice";
    }

    @Override
    protected @NotNull String getHelp() {
        return "magic dice";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0 ) {
            utilsChat.sendInfo(param.message.getChannel(), "```c\n" + numbers[random.nextInt(6)+1] + "\n```"); //Integer.toString( random.nextInt(5+1))
            return;
        }
        try{
            int dice = Integer.parseInt(param.args[0]);
            int randomnumber = random.nextInt(dice);
            StringBuilder asciinumber = new StringBuilder("```c\n");
            for (char c: Integer.toString(randomnumber).toCharArray()) {
                asciinumber.append(numbers[Integer.parseInt(String.valueOf(c))]);
                asciinumber.append("\n\n");
            }
            asciinumber.append("\n```");

            utilsChat.sendInfo(param.message.getChannel(), asciinumber.toString());
        }
        catch (NumberFormatException e){
            utilsChat.sendInfo(param.message.getChannel(),param.args[0] + " is not a number!");
        }
        catch (IllegalArgumentException e){
            utilsChat.sendInfo(param.message.getChannel(), "Number has to be positive!\n\nLike your attitude");
        }




    }
}
