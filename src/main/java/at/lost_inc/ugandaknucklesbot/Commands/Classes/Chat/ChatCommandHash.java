package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;


import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.Hash;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@Author("sudo200")
@Command(
        name = "hash",
        help = "Creates a hash from a string using the given algorithm,\n" +
                "for your hashing needs!\n" +
                "Currently supported algorithms:\n" +
                "MD5, SHA-1, SHA-256\n\n" +
                "**Usage:**`hash <algorithm> <string_to_hash>`",
        categories = {
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandHash extends BotCommand {
    private UtilsChat utilsChat;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final MessageChannel channel = param.message.getChannel();
        if(param.args.length == 0) {
            utilsChat.sendInfo(
                    channel,
                    "You wanna hash to void?\n" +
                    "Sure, if you tell me which algorithm to use..."
            );
            return;
        }

        if(param.args.length == 1 && (
                param.args[0].equalsIgnoreCase("MD5") ||
                        param.args[0].equalsIgnoreCase("SHA-1") ||
                        param.args[0].equalsIgnoreCase("SHA-256")
        )) {
            utilsChat.sendInfo(
                    channel,
                    "You really thought, i would hash to void?\n" +
                            "Naaa!"
            );
            return;
        }

        if(param.args.length == 1 && !(
                param.args[0].equalsIgnoreCase("MD5") ||
                        param.args[0].equalsIgnoreCase("SHA-1") ||
                        param.args[0].equalsIgnoreCase("SHA-256")
        )) {
            utilsChat.sendInfo(
                    channel,
                    "Give me an algorithm, and I will hash!"
            );
            return;
        }

        Function<String, String> hashFunction = null;

        switch (param.args[0]) {
            case "MD5": {
                hashFunction = Hash::getMD5Hash;
            }
            break;

            case "SHA-1": {
                hashFunction = Hash::getSHA1Hash;
            }
            break;

            case "SHA-256": {
                hashFunction = Hash::getSHA256Hash;
            }
            break;
        }

        param.args[0] = "";
        final String hash = hashFunction.apply(
                String.join(" ", param.args)
        );

        utilsChat.sendInfo(
                channel,
                "Your Hash:\n\n`" + hash + "`"
        );
    }
}
