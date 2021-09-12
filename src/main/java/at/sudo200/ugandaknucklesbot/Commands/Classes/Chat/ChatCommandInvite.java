package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatCommandInvite extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    private final String invite = "https://discord.com/api/oauth2/authorize?client_id=720214661047386176&permissions=8&scope=bot%20applications.commands";

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                CommandCategories.MISC,
                CommandCategories.CHAT, CommandCategories.UTIL
        };
    }

    @Override
    protected @NotNull String getName() {
        return "invite";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Gives you the bot invite, so you can add me to your very own discord server!\n\nWhy though?";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        utilsChat.sendInfo(
                param.message.getChannel(),
                String.format(
                        "You wanna add me to your own discord server?\n" +
                        "[Click here!](%s)",
                        invite
                )
        );
    }
}
