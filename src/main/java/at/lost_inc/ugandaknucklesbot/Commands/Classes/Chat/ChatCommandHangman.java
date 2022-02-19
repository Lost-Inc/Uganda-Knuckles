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

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Author("sudo200")
@Command(
        name = "hangman",
        help = "Play a game of hangman with your friends (who needs paper?)\n" +
                "To end a game preemptively, append `end` to the command",
        categories = {
                BotCommand.ICategories.GAME,
                BotCommand.ICategories.CHAT
        },
        aliases = {
                "hm"
        }
)
public final class ChatCommandHangman extends BotCommand {
    private static final String[] guessWords = new String[]{
            "abruptly",
            "absurd",
            "abyss",
            "affix",
            "askew",
            "avenue",
            "awkward",
            "axiom",
            "azure",
            "bagpipes",
            "bandwagon",
            "banjo",
            "bayou",
            "beekeeper",
            "bikini",
            "blitz",
            "blizzard",
            "boggle",
            "bookworm",
            "boxcar",
            "boxful",
            "buckaroo",
            "buff",
            "buffalo",
            "buffoon",
            "buxom",
            "buzzard",
            "buzzed",
            "buzzing",
            "buzzwords",
            "caliph",
            "cobweb",
            "cockiness",
            "cozy",
            "croquet",
            "crypt",
            "curacao",
            "cycle",
            "daiquiri",
            "dirndl",
            "disavow",
            "dizzy",
            "dizzying",
            "duplex",
            "dwarves",
            "embezzle",
            "equip",
            "espionage",
            "euouae",
            "exodus",
            "faking",
            "fishhook",
            "fixable",
            "fizzy",
            "fjord",
            "flapjack",
            "flopping",
            "fluffiness",
            "fluffing",
            "flyby",
            "foxglove",
            "frazzled",
            "frizzled",
            "fuchsia",
            "funny",
            "fuzz",
            "fuzzy",
            "gabby",
            "galaxy",
            "galvanize",
            "gazebo",
            "giaour",
            "gizmo",
            "glowworm",
            "glyph",
            "gnarly",
            "gnostic",
            "gossip",
            "grogginess",
            "haiku",
            "haphazard",
            "huh",
            "hyphen",
            "iatrogenic",
            "icebox",
            "injury",
            "ivory",
            "ivy",
            "jackpot",
            "jaundice",
            "jawbreaker",
            "jaywalk",
            "jazz",
            "jazzed",
            "jazziest",
            "jazzy",
            "jelly",
            "jigsaw",
            "jinx",
            "jiujitsu",
            "jive",
            "jockey",
            "jogging",
            "jokes",
            "joking",
            "jovial",
            "joyful",
            "juicy",
            "jukebox",
            "jumbo",
            "kayak",
            "kazoo",
            "keyhole",
            "khaki",
            "kilobyte",
            "kiosk",
            "kitsch",
            "kiwifruit",
            "klutz",
            "knapsack",
            "larynx",
            "lengths",
            "lucky",
            "luxury",
            "lymph",
            "marquis",
            "matrix",
            "megahertz",
            "microwave",
            "mnemonic",
            "mystify",
            "myth",
            "naphtha",
            "nightclub",
            "nowadays",
            "numbskull",
            "nymph",
            "onyx",
            "ovary",
            "oxidize",
            "oxygen",
            "pajama",
            "peekaboo",
            "phlegm",
            "pixel",
            "pizazz",
            "pneumonia",
            "polka",
            "pshaw",
            "psyche",
            "puppy",
            "puzzling",
            "quacking",
            "quartz",
            "queue",
            "quips",
            "quixotic",
            "quiz",
            "quizzes",
            "quorum",
            "razzmatazz",
            "rhubarb",
            "rhythm",
            "rickshaw",
            "schnapps",
            "scratch",
            "shiv",
            "snazzy",
            "sphinx",
            "spritz",
            "squawk",
            "staff",
            "strength",
            "strengths",
            "stretch",
            "stronghold",
            "stymied",
            "subway",
            "swivel",
            "syndrome",
            "thriftless",
            "thumbscrew",
            "topaz",
            "transcript",
            "transgress",
            "transplant",
            "triphthong",
            "twelfth",
            "twelfths",
            "unknown",
            "unworthy",
            "unzip",
            "uptown",
            "vaporize",
            "vex",
            "vixen",
            "vodka",
            "voodoo",
            "vortex",
            "voyeurism",
            "walkway",
            "waltz",
            "wave",
            "wavy",
            "waxy",
            "wellspring",
            "wheezy",
            "whiskey",
            "whizzing",
            "whomever",
            "wimpy",
            "witchcraft",
            "wizard",
            "woozy",
            "wristwatch",
            "wyvern",
            "xylophone",
            "yachtsman",
            "yippee",
            "yoked",
            "youthful",
            "yummy",
            "zap",
            "zephyr",
            "zigzag",
            "zigzagging",
            "zilch",
            "zipper",
            "zit",
            "zodiac",
            "zombie",
    };

    static {
        for (int i = 0; i < guessWords.length; i++)
            guessWords[i] = guessWords[i].toUpperCase();
    }

    private Random rand;
    private UtilsChat utilsChat;
    private GameService gameService;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        rand = ServiceManager.provideUnchecked(Random.class);
        gameService = ServiceManager.provideUnchecked(GameService.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final MessageChannel channel = param.message.getChannel();

        if (param.args.length == 0) {
            if (gameService.getById(channel.getId() + '+' + guild.getId(), Character.class, String.class).isPresent()) {
                utilsChat.sendInfo(channel, "A hangman game is already running in this channel!");
                return;
            }

            gameService.register(new GameService.Game<Character, String>() {
                private final String id = channel.getId() + '+' + guild.getId();
                private final String orgWord = guessWords[rand.nextInt(guessWords.length)];
                private String word = orgWord;
                private String showedWord = word.replaceAll("\\S", "?");

                private int lives = 10;
                private String msg = null;

                @Override
                public @NotNull String getName() {
                    return "hangman";
                }

                @Override
                public @NotNull String getId() {
                    return id;
                }

                @Override
                public @NotNull GameState check(@NotNull Character character) {
                    if (!word.contains(character.toString())) {
                        lives--;
                        if (lives <= 0) {
                            msg = "The word was `" + orgWord + "`!";
                            return GameState.GAME_OVER;
                        }
                        msg = '`' + showedWord + '`' +
                                "\nYou have " + lives + " left!";
                        return GameState.WRONG;
                    }

                    do {
                        final int index = word.indexOf(character.toString());
                        showedWord = showedWord.substring(0, index) + word.charAt(index) + showedWord.substring(index + 1);
                        word = word.replaceFirst(character.toString(), "\u001B");
                    }
                    while (word.contains(character.toString()));

                    if (word.matches("^\u001B+$")) {
                        msg = '`' + showedWord + '`' +
                                "\nYou had " + lives + " left!";
                        return GameState.WIN;
                    }

                    msg = '`' + showedWord + '`';
                    return GameState.OKAY;
                }

                @Override
                public @NotNull Optional<String> getMsg() {
                    return Optional.ofNullable(msg);
                }
            });

            utilsChat.sendInfo(channel, "Started a game of hangman!\nGuess with `@" +
                    Objects.requireNonNull(guild.getMemberById(channel.getJDA().getSelfUser().getId())).getEffectiveName() +
                    " g`!"
            );
        } else {
            if (!gameService.getById(channel.getId() + '+' + guild.getId(), Character.class, String.class).isPresent())
                return;

            if (gameService.removeById(channel.getId() + '+' + guild.getId()))
                utilsChat.sendInfo(channel, "Hangman game endet!");
        }
    }
}
