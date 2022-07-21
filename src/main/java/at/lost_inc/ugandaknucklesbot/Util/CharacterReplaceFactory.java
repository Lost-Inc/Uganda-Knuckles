package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.NotNull;

import java.util.Dictionary;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CharacterReplaceFactory {
    private CharacterReplaceFactory() {
    }

    public static @NotNull Function<CharSequence, String> getReplacer(@NotNull Dictionary<Character, String> replaceDictionary) {
        return text -> {
            final StringBuilder builder = new StringBuilder();

            for (char ch : text.chars().mapToObj(i -> (char) i).collect(Collectors.toList())) {
                final String replaced = replaceDictionary.get(ch);
                builder.append(replaced == null ? ch : replaced);
            }

            return builder.toString();
        };
    }
}
