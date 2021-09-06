package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatCommandLenny extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String getName() {
        return "lenny";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Prints out lenny\n" +
                "that's all\n" +
                "nothing else";
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]
                // Main category
                {CommandCategories.FUN,
                        // Auxiliary categories
                        CommandCategories.CHAT};
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        EmbedBuilder builder = utilsChat.getDefaultEmbed();
        builder.setTitle("( \u0361° \u035c\u0296 \u0361 °)");

        if (param.args.length == 0 || !utilsChat.isMention(param.args[0]))
            utilsChat.send(param.message.getChannel(), builder.build());
        else {
            User user = utilsChat.getMemberByMention(param.args[0], param.message.getGuild()).getUser();
            if (user.getIdLong() == param.message.getJDA().getSelfUser().getIdLong()) {
                utilsChat.sendInfo(param.message.getChannel(), "Sry, but I cannot talk with myself!");
                return;
            }

            user.openPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.sendMessage(builder.build()))
                    .queue(message -> {
                            }, error ->
                                    utilsChat.sendInfo(
                                            param.message.getChannel(),
                                            "**Could not send private message!**\n" +
                                                    "Is the receiver a bot too?"
                                    )
                    );
        }
    }
}
