package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.NotNull;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Function;

public final class BoldConverter {
    private static final Dictionary<Character, String> boldTable = new Hashtable<>();
    private static final Function<CharSequence, String> replacer;

    static {
        boldTable.put('a', "\uD835\uDC1A");
        boldTable.put('b', "\uD835\uDC1B");
        boldTable.put('c', "\uD835\uDC1C");
        boldTable.put('d', "\uD835\uDC1D");
        boldTable.put('e', "\uD835\uDC1E");
        boldTable.put('f', "\uD835\uDC1F");
        boldTable.put('g', "\uD835\uDC20");
        boldTable.put('h', "\uD835\uDC21");
        boldTable.put('i', "\uD835\uDC22");
        boldTable.put('j', "\uD835\uDC23");
        boldTable.put('k', "\uD835\uDC24");
        boldTable.put('l', "\uD835\uDC25");
        boldTable.put('m', "\uD835\uDC26");
        boldTable.put('n', "\uD835\uDC27");
        boldTable.put('o', "\uD835\uDC28");
        boldTable.put('p', "\uD835\uDC29");
        boldTable.put('q', "\uD835\uDC2A");
        boldTable.put('r', "\uD835\uDC2B");
        boldTable.put('s', "\uD835\uDC2C");
        boldTable.put('t', "\uD835\uDC2D");
        boldTable.put('u', "\uD835\uDC2E");
        boldTable.put('v', "\uD835\uDC2F");
        boldTable.put('w', "\uD835\uDC30");
        boldTable.put('x', "\uD835\uDC31");
        boldTable.put('y', "\uD835\uDC32");
        boldTable.put('z', "\uD835\uDC33");

        boldTable.put('1', "\uD835\uDFCF");
        boldTable.put('2', "\uD835\uDFD0");
        boldTable.put('3', "\uD835\uDFD1");
        boldTable.put('4', "\uD835\uDFD2");
        boldTable.put('5', "\uD835\uDFD3");
        boldTable.put('6', "\uD835\uDFD4");
        boldTable.put('7', "\uD835\uDFD5");
        boldTable.put('8', "\uD835\uDFD6");
        boldTable.put('9', "\uD835\uDFD7");
        boldTable.put('0', "\uD835\uDFCE");

        boldTable.put('A', "\uD835\uDC00");
        boldTable.put('B', "\uD835\uDC01");
        boldTable.put('C', "\uD835\uDC02");
        boldTable.put('D', "\uD835\uDC03");
        boldTable.put('E', "\uD835\uDC04");
        boldTable.put('F', "\uD835\uDC05");
        boldTable.put('G', "\uD835\uDC06");
        boldTable.put('H', "\uD835\uDC07");
        boldTable.put('I', "\uD835\uDC08");
        boldTable.put('J', "\uD835\uDC09");
        boldTable.put('K', "\uD835\uDC0A");
        boldTable.put('L', "\uD835\uDC0B");
        boldTable.put('M', "\uD835\uDC0C");
        boldTable.put('N', "\uD835\uDC0D");
        boldTable.put('O', "\uD835\uDC0E");
        boldTable.put('P', "\uD835\uDC0F");
        boldTable.put('Q', "\uD835\uDC10");
        boldTable.put('R', "\uD835\uDC11");
        boldTable.put('S', "\uD835\uDC12");
        boldTable.put('T', "\uD835\uDC13");
        boldTable.put('U', "\uD835\uDC14");
        boldTable.put('V', "\uD835\uDC15");
        boldTable.put('W', "\uD835\uDC16");
        boldTable.put('X', "\uD835\uDC17");
        boldTable.put('Y', "\uD835\uDC18");
        boldTable.put('Z', "\uD835\uDC19");

        replacer = CharacterReplaceFactory.getReplacer(boldTable);
    }

    private BoldConverter() {
    }

    public static @NotNull String boldify(@NotNull CharSequence text) {
        return replacer.apply(text);
    }
}
