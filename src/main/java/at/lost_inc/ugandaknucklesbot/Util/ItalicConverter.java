package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.NotNull;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Function;

public final class ItalicConverter {
    private static final Hashtable<Character, String> italicTable = new Hashtable<>();
    private static final Function<CharSequence, String> replacer;

    static {
        italicTable.put('a', "\uD835\uDE22");
        italicTable.put('b', "\uD835\uDE23");
        italicTable.put('c', "\uD835\uDE24");
        italicTable.put('d', "\uD835\uDE25");
        italicTable.put('e', "\uD835\uDE26");
        italicTable.put('f', "\uD835\uDE27");
        italicTable.put('g', "\uD835\uDE28");
        italicTable.put('h', "\uD835\uDE29");
        italicTable.put('i', "\uD835\uDE2A");
        italicTable.put('j', "\uD835\uDE2B");
        italicTable.put('k', "\uD835\uDE2C");
        italicTable.put('l', "\uD835\uDE2D");
        italicTable.put('m', "\uD835\uDE2E");
        italicTable.put('n', "\uD835\uDE2F");
        italicTable.put('o', "\uD835\uDE30");
        italicTable.put('p', "\uD835\uDE31");
        italicTable.put('q', "\uD835\uDE32");
        italicTable.put('r', "\uD835\uDE33");
        italicTable.put('s', "\uD835\uDE34");
        italicTable.put('t', "\uD835\uDE35");
        italicTable.put('u', "\uD835\uDE36");
        italicTable.put('v', "\uD835\uDE37");
        italicTable.put('w', "\uD835\uDE38");
        italicTable.put('x', "\uD835\uDE39");
        italicTable.put('y', "\uD835\uDE3A");
        italicTable.put('z', "\uD835\uDE3B");

        italicTable.put('A', "\uD835\uDE08");
        italicTable.put('B', "\uD835\uDE09");
        italicTable.put('C', "\uD835\uDE0A");
        italicTable.put('D', "\uD835\uDE0B");
        italicTable.put('E', "\uD835\uDE0C");
        italicTable.put('F', "\uD835\uDE0D");
        italicTable.put('G', "\uD835\uDE0E");
        italicTable.put('H', "\uD835\uDE0F");
        italicTable.put('I', "\uD835\uDE10");
        italicTable.put('J', "\uD835\uDE11");
        italicTable.put('K', "\uD835\uDE12");
        italicTable.put('L', "\uD835\uDE13");
        italicTable.put('M', "\uD835\uDE14");
        italicTable.put('N', "\uD835\uDE15");
        italicTable.put('O', "\uD835\uDE16");
        italicTable.put('P', "\uD835\uDE17");
        italicTable.put('Q', "\uD835\uDE18");
        italicTable.put('R', "\uD835\uDE19");
        italicTable.put('S', "\uD835\uDE1A");
        italicTable.put('T', "\uD835\uDE1B");
        italicTable.put('U', "\uD835\uDE1C");
        italicTable.put('V', "\uD835\uDE1D");
        italicTable.put('W', "\uD835\uDE1E");
        italicTable.put('X', "\uD835\uDE1F");
        italicTable.put('Y', "\uD835\uDE20");
        italicTable.put('Z', "\uD835\uDE21");

        replacer = CharacterReplaceFactory.getReplacer(italicTable);
    }

    private ItalicConverter() {
    }

    public static @NotNull Dictionary<Character, String> getItalicTable() {
        return new Hashtable<>(italicTable);
    }

    public static @NotNull String italicize(@NotNull CharSequence text) {
        return replacer.apply(text);
    }
}
