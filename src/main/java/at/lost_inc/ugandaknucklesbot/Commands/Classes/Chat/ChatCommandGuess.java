package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Games.GameService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Author("sudo200")
@Command(
        name = "guess",
        help = "A command for guessing something.\nUsed with games like hangman",
        categories = {
                BotCommand.ICategories.GAME,
                BotCommand.ICategories.CHAT
        },
        aliases = {
                "g"
        }
)
public final class ChatCommandGuess extends BotCommand {
    private UtilsChat utilsChat;
    private GameService gameService;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        gameService = ServiceManager.provideUnchecked(GameService.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final MessageChannel channel = param.message.getChannel();
        final String gameId = channel.getId() + '+' + guild.getId();

        final Optional<GameService.Game<Character, String>> optionalGame = gameService
                .getById(gameId, Character.class, String.class);

        if (!optionalGame.isPresent()) {
            utilsChat.sendInfo(channel, "There is no game running in this channel, yet");
            return;
        }

        switch (optionalGame.get().getName()) {
            case "hangman": {
                if (param.args.length == 0) {
                    utilsChat.sendInfo(channel, "You forgot your guess!");
                    return;
                }

                if (param.args[0].length() != 1) {
                    utilsChat.sendInfo(channel, "Your guess should only contain a single character!");
                    return;
                }

                final GameService.Game<Character, String> hangman = optionalGame.get();
                final char guess = param.args[0].toUpperCase().charAt(0);

                final GameService.Game.GameState state = hangman.check(guess);

                switch (state) {
                    case OKAY: {
                        utilsChat.sendInfo(channel,
                                "Correct!\n" +
                                        hangman.getMsg().orElse("")
                        );
                    }
                    break;

                    case WRONG: {
                        utilsChat.sendInfo(channel,
                                "You are wrong, kid!\n" +
                                        hangman.getMsg().orElse("")
                        );
                    }
                    break;

                    case WIN: {
                        utilsChat.sendInfo(channel,
                                "GG, you won!\n" +
                                        hangman.getMsg().orElse("")
                        );
                    }
                    break;

                    case GAME_OVER: {
                        utilsChat.sendInfo(channel,
                                "GG kiddo, you lost!\n" +
                                        hangman.getMsg().orElse("")
                        );
                    }
                    break;
                }

                if (state.isOver())
                    gameService.removeById(gameId);
            }
            return;

            default: {
                utilsChat.sendInfo(channel, "Sorry, but this game is not fully supported! yet...");
            }
        }
    }
}
