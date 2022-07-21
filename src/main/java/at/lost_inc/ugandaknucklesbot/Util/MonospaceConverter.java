package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Function;

public final class MonospaceConverter {
    private static final Hashtable<Character, String> monospaceTable = new Hashtable<>();
    private static final Function<CharSequence, String> replacer;

    static {
        monospaceTable.put('a', "\uD835\uDE8A");
        monospaceTable.put('b', "\uD835\uDE8B");
        monospaceTable.put('c', "\uD835\uDE8C");
        monospaceTable.put('d', "\uD835\uDE8D");
        monospaceTable.put('e', "\uD835\uDE8E");
        monospaceTable.put('f', "\uD835\uDE8F");
        monospaceTable.put('g', "\uD835\uDE90");
        monospaceTable.put('h', "\uD835\uDE91");
        monospaceTable.put('i', "\uD835\uDE92");
        monospaceTable.put('j', "\uD835\uDE93");
        monospaceTable.put('k', "\uD835\uDE94");
        monospaceTable.put('l', "\uD835\uDE95");
        monospaceTable.put('m', "\uD835\uDE96");
        monospaceTable.put('n', "\uD835\uDE97");
        monospaceTable.put('o', "\uD835\uDE98");
        monospaceTable.put('p', "\uD835\uDE99");
        monospaceTable.put('q', "\uD835\uDE9A");
        monospaceTable.put('r', "\uD835\uDE9B");
        monospaceTable.put('s', "\uD835\uDE9C");
        monospaceTable.put('t', "\uD835\uDE9D");
        monospaceTable.put('u', "\uD835\uDE9E");
        monospaceTable.put('v', "\uD835\uDE9F");
        monospaceTable.put('w', "\uD835\uDEA0");
        monospaceTable.put('x', "\uD835\uDEA1");
        monospaceTable.put('y', "\uD835\uDEA2");
        monospaceTable.put('z', "\uD835\uDEA3");

        monospaceTable.put('1', "\uD835\uDFF7");
        monospaceTable.put('2', "\uD835\uDFF8");
        monospaceTable.put('3', "\uD835\uDFF9");
        monospaceTable.put('4', "\uD835\uDFFA");
        monospaceTable.put('5', "\uD835\uDFFB");
        monospaceTable.put('6', "\uD835\uDFFC");
        monospaceTable.put('7', "\uD835\uDFFD");
        monospaceTable.put('8', "\uD835\uDFFE");
        monospaceTable.put('9', "\uD835\uDFFF");
        monospaceTable.put('0', "\uD835\uDFF6");

        monospaceTable.put('A', "\uD835\uDE70");
        monospaceTable.put('B', "\uD835\uDE71");
        monospaceTable.put('C', "\uD835\uDE72");
        monospaceTable.put('D', "\uD835\uDE73");
        monospaceTable.put('E', "\uD835\uDE74");
        monospaceTable.put('F', "\uD835\uDE75");
        monospaceTable.put('G', "\uD835\uDE76");
        monospaceTable.put('H', "\uD835\uDE77");
        monospaceTable.put('I', "\uD835\uDE78");
        monospaceTable.put('J', "\uD835\uDE79");
        monospaceTable.put('K', "\uD835\uDE7A");
        monospaceTable.put('L', "\uD835\uDE7B");
        monospaceTable.put('M', "\uD835\uDE7C");
        monospaceTable.put('N', "\uD835\uDE7D");
        monospaceTable.put('O', "\uD835\uDE7E");
        monospaceTable.put('P', "\uD835\uDE7F");
        monospaceTable.put('Q', "\uD835\uDE80");
        monospaceTable.put('R', "\uD835\uDE81");
        monospaceTable.put('S', "\uD835\uDE82");
        monospaceTable.put('T', "\uD835\uDE83");
        monospaceTable.put('U', "\uD835\uDE84");
        monospaceTable.put('V', "\uD835\uDE85");
        monospaceTable.put('W', "\uD835\uDE86");
        monospaceTable.put('X', "\uD835\uDE87");
        monospaceTable.put('Y', "\uD835\uDE88");
        monospaceTable.put('Z', "\uD835\uDE89");

        replacer = CharacterReplaceFactory.getReplacer(monospaceTable);
    }

    private MonospaceConverter() {
    }

    @Contract(" -> new")
    public static @NotNull Dictionary<Character, String> getMonospaceTable() {
        return new Hashtable<>(monospaceTable);
    }

    public static @NotNull String monospaceify(@NotNull CharSequence text) {
        return replacer.apply(text);
    }
}
