package at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses;

import at.lost_inc.ugandaknucklesbot.Util.Author;

@Author("sudo200")
public class WikipediaSummaryRestAPI {
    public String type;
    public Titles titles;
    public Long pageid;
    public String extract;
    public String extract_html;
    public Thumbnail thumbnail;
    public OriginalImage originalimage;
    public String lang;
    public String dir;
    public String timestamp;
    public String description;
    public Coordinates coordinates;

    public static class Titles {
        public String canonical;
        public String normalized;
        public String display;
    }

    public static class Thumbnail {
        public String source;
        public Long width;
        public Long height;
    }

    public static class OriginalImage {
        public String source;
        public Long width;
        public Long height;
    }

    public static class Coordinates {
        public Double lat;
        public Double lon;
    }


    public static class Error {
        public String type;
        public String title;
        public String method;
        public String detail;
        public String uri;
    }
}
