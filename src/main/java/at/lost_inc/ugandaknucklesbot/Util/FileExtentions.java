package at.lost_inc.ugandaknucklesbot.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface FileExtentions {
    Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg",
            "jpeg", "png", "gif", "webp", "tiff", "svg", "apng"));

    Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList("webm",
            "flv", "vob", "avi", "mov", "wmv", "amv", "mp4", "mpg", "mpeg", "gifv"));

    Set<String> AUDIO_EXTENTIONS = new HashSet<>(Arrays.asList("wav",
            "ogg", "mp3"));
}
