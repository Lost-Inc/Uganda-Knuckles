package at.lost_inc.ugandaknucklesbot.Util;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Function;

/**
 * "Library" for converting text into leet speech
 *
 * @author sudo200
 */
public final class Leetify {
    private static final Hashtable<Character, String> LeetTable = new Hashtable<>();

    private static final Function<CharSequence, String> replacer;

    static {
        LeetTable.put('a', "4");
        LeetTable.put('A', "4");
        LeetTable.put('b', "8");
        LeetTable.put('B', "8");
        LeetTable.put('e', "3");
        LeetTable.put('E', "8");
        LeetTable.put('g', "6");
        LeetTable.put('G', "6");
        LeetTable.put('l', "1");
        LeetTable.put('L', "1");
        LeetTable.put('o', "0");
        LeetTable.put('O', "0");
        LeetTable.put('p', "9");
        LeetTable.put('P', "9");
        LeetTable.put('s', "5");
        LeetTable.put('S', "5");
        LeetTable.put('t', "7");
        LeetTable.put('T', "7");
        LeetTable.put('z', "2");
        LeetTable.put('Z', "2");

        replacer = CharacterReplaceFactory.getReplacer(LeetTable);
    }

    private Leetify() {
    }

    @Contract(" -> new")
    public static @NotNull Dictionary<Character, String> getLeetTable() {
        return new Hashtable<>(LeetTable);
    }

    public static @NotNull String leetify(@NotNull CharSequence text) {
        return replacer.apply(text);
    }
}
