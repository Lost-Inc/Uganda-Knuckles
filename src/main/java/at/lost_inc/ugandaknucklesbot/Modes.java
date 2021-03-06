package at.lost_inc.ugandaknucklesbot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public enum Modes {
    TRACE(1 << 0),
    DEBUG(1 << 0),
    DEBUG_DB(1 << 0),
    PRODUCTION(1 << 4);

    final int stability;

    Modes(int stability) {
        this.stability = stability;
    }

    /**
     * Returns the corresponding constant named after the given string.
     * If there was no match, or <code>str</code> is <code>null</code>,
     * it returns the constant with the highest stability level.
     */
    public static @NotNull Modes getFromString(@Nullable String str) {
        try {
            return Modes.valueOf(Modes.class, Objects.requireNonNull(str).toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            Modes highestStability = null;
            for (final Modes mode0 : Modes.class.getEnumConstants()) {
                if (highestStability == null)
                    highestStability = mode0;

                if (mode0.stability > highestStability.stability)
                    highestStability = mode0;
            }

            return Objects.requireNonNull(highestStability);
        }
    }

    public int getStability() {
        return stability;
    }

    public boolean equals(@NotNull String str) {
        return this.toString().equalsIgnoreCase(str);
    }
}
