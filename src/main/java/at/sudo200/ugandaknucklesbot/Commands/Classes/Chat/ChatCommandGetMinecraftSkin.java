package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ChatCommandGetMinecraftSkin extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Random random = new Random();

    @Override
    protected @NotNull String getName() {
        return "mc-skin";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Grabs the skin of a Minecraft player\n" +
                "by name or UUID\n" +
                "**Important:** Nonexistent players will appear to have the Steve skin";
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]
                // Main category
                {CommandCategories.MISC,
                        // Auxiliary categories
                        CommandCategories.CHAT, CommandCategories.INTERNET, CommandCategories.IMAGE};
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
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
