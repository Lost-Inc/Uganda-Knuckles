package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * "Library" for converting text
 * into weeb-text (*^.^*)
 *
 * @author sudo200
 * @see <a href="https://github.com/BillNickYT/OwOfy">Original Author</a>
 */

public class OwOfy {
    private static final List<String> faces = Arrays.asList(
            "(*^ω^)",
            "(◕‿◕✿)",
            "(◕ᴥ◕)",
            "ʕ•ᴥ•ʔ",
            "ʕ￫ᴥ￩ʔ",
            "(*^.^*)",
            "OwO",
            "(｡♥‿♥｡)",
            "UwU",
            "(*￣з￣)",
            ">w<",
            "^w^",
            "(つ✧ω✧)つ",
            "(/ =ω=)/"
    );

    private OwOfy() {
    }

    @Contract(" -> new")
    public static @NotNull List<String> getFaces() {
        return new ArrayList<>(faces);
    }

    /**
     * @param input   text to convert
     * @param useFace whether to add faces for extra weeb-ness
     * @return weeb-text (つ✧ω✧)つ
     * @author sudo200
     */
    public static String owofy(String input, boolean useFace) {

        String face = faces.get(new Random().nextInt(faces.size()));
        String out = input;
        out = replaceAll(replaceAll(out, "lr", "w"), "LR", "W");
        out = replaceAllWithCharAfter(out, "n", "aeiou", "ny");
        out = replaceAllWithCharAfter(out, "N", "aeiou", "Ny");
        out = replaceAllWithCharAfter(out, "N", "AEIOU", "Ny");
        out = out.replace("ove", "uv");
        if (useFace)
            out += " " + face;

        return out;
    }

    /**
     * method converting text into weeb-text
     *
     * @param input text to convert
     * @return weeb-text (つ✧ω✧)つ
     * @author sudo200
     */
    public static String owofy(String input) {
        return owofy(input, true);
    }

    private static @NotNull String replaceAll(@NotNull String text, String chars, String newChar) {

        StringBuilder out = new StringBuilder();

        for (char c : text.toCharArray())
            if (chars.contains(String.valueOf(c)))
                out.append(newChar);
            else
                out.append(c);
        return out.toString();
    }

    private static @NotNull String replaceAllWithCharAfter(@NotNull String text, String chars, String charsAfter, String newChar) {
        StringBuilder out = new StringBuilder();

        int current = 0;
        for (char c : text.toCharArray()) {
            if (chars.contains(String.valueOf(c)))
                try {
                    char after = text.charAt(current + 1);
                    if (charsAfter.contains(String.valueOf(after)))
                        out.append(newChar);
                    else
                        out.append(c);
                } catch (Exception ex) {
                    out.append(c);
                }
            else
                out.append(c);
            current++;
        }
        return out.toString();
    }
}
