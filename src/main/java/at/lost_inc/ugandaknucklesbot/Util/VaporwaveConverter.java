package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Function;

public final class VaporwaveConverter {
    private static final Hashtable<Character, String> vaporwaveTable = new Hashtable<>();
    private static final Function<CharSequence, String> replacer;

    static {
        vaporwaveTable.put('a', "卂");
        vaporwaveTable.put('b', "乃");
        vaporwaveTable.put('c', "匚");
        vaporwaveTable.put('d', "ᗪ");
        vaporwaveTable.put('e', "乇");
        vaporwaveTable.put('f', "千");
        vaporwaveTable.put('g', "Ꮆ");
        vaporwaveTable.put('h', "卄");
        vaporwaveTable.put('i', "丨");
        vaporwaveTable.put('j', "ﾌ");
        vaporwaveTable.put('k', "Ҝ");
        vaporwaveTable.put('l', "ㄥ");
        vaporwaveTable.put('m', "爪");
        vaporwaveTable.put('n', "几");
        vaporwaveTable.put('o', "ㄖ");
        vaporwaveTable.put('p', "卩");
        vaporwaveTable.put('q', "Ɋ");
        vaporwaveTable.put('r', "尺");
        vaporwaveTable.put('s', "丂");
        vaporwaveTable.put('t', "ㄒ");
        vaporwaveTable.put('u', "ㄩ");
        vaporwaveTable.put('v', "ᐯ");
        vaporwaveTable.put('w', "山");
        vaporwaveTable.put('x', "乂");
        vaporwaveTable.put('y', "ㄚ");
        vaporwaveTable.put('z', "乙");

        replacer = CharacterReplaceFactory.getReplacer(vaporwaveTable);
    }

    private VaporwaveConverter() {
    }

    @Contract(" -> new")
    public static @NotNull Dictionary<Character, String> getVaporwaveTable() {
        return new Hashtable<>(vaporwaveTable);
    }

    public static @NotNull String vaporwaveify(@NotNull CharSequence text) {
        return replacer.apply(text);
    }
}
