package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ChatCommandRandomCat extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Gson gson = new Gson();

    @Override
    protected @NotNull String getName() {
        return "cat";
    }

    @Override
    protected @NotNull String getHelp() {
        return "~~Imports cute little pussies from the internet\n" +
                "we mean cats of course~~ **Currently broken!**";
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]
                // Main category
                {CommandCategories.IMAGE,
                        // Auxiliary categories
                        CommandCategories.CHAT, CommandCategories.FUN, CommandCategories.INTERNET};
    }

    @Override // TODO: FIX!
    protected void execute(@NotNull CommandParameter param) {
        utilsChat.sendInfo(param.message.getChannel(),
                "**This command is currently broken :broken_heart:**\n" +
                        "Like your girlfriend's heart! Go fix it!"
        );
        /*
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        String response = HttpRequest.get("https://aws.random.cat/meow").body();
        try {
            RandomCatAPIResponse randomCatAPIResponse = gson.fromJson(response, (Type) RandomCatAPIResponse.class);
            builder.setImage(randomCatAPIResponse.file);
            utilsChat.send(param.message.getChannel(), builder.build());
        }
        catch (Exception e) {
            utilsChat.sendInfo(
                    param.message.getChannel(),
                    "**Something went severely wrong**\nBlame your neighbor!\n\n" + e
            );
        }*/

    }
}
