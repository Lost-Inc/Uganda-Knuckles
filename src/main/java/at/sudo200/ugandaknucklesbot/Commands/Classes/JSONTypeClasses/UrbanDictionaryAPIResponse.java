package at.sudo200.ugandaknucklesbot.Commands.Classes.JSONTypeClasses;

public class UrbanDictionaryAPIResponse {
    public DefinitionObject[] list;

    public static class DefinitionObject {
        public String definition;
        public String permalink;
        public Long thumbs_up;
        public String[] sound_urls;
        public String author;
        public String word;
        public Long defid;
        public String current_vote;
        public String written_on;
        public String example;
        public Long thumbs_down;
    }
}
