package at.lost_inc.ugandaknucklesbot.Util;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Provides methods for hashing strings
 */
public final class Hash {
    private Hash() {
    }

    /**
     * Generates the md5 hash from the given string.
     * <br><br>
     * <b>WARNING: md5 is mathematically insecure, and because of that,
     * it should only be used for for example generating codes from random numbers!</b>
     *
     * @param md5 {@link String} to generate hash from
     * @return md5 hash
     */
    public static @NotNull String getMD5Hash(@NotNull String md5) {
        return getHash(md5, "MD5");
    }

    /**
     * Generates the sha1 hash from the given string.
     *
     * @param sha1 {@link String} to generate hash from
     * @return sha1 hash
     */
    public static @NotNull String getSHA1Hash(@NotNull String sha1) {
        return getHash(sha1, "SHA-1");
    }

    /**
     * Generates the sha256 hash from the given string
     *
     * @param sha256 {@link String} to generate hash from
     * @return sha256 hash
     */
    public static @NotNull String getSHA256Hash(@NotNull String sha256) {
        return getHash(sha256, "SHA-256");
    }

    private static @NotNull String getHash(@NotNull String str, @NotNull String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] array = md.digest(str.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : array) {
                stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return "";
    }
}
