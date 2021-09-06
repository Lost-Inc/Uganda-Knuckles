package at.sudo200.ugandaknucklesbot.Commands.Classes.JSONTypeClasses;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class RedditTopApiResponse {
    public String kind;
    public Data data;

    public static class Data {
        public Long dist;
        public String modhash;
        public String geo_filter;
        public Child[] children;

        public static class Child {
            public String kind;
            public PostData data;

            public static class PostData {
                public String subreddit;
                public String selftext;
                public String author_fullname;
                public Boolean saved;
                public Long gilded;
                public Boolean clicked;
                public String title;
                public String subreddit_name_prefixed;
                public Boolean hidden;
                public Long pwls;
                public Long downs;
                public Boolean hide_score;
                public String name;
                public Boolean quarantine;
                public String link_flair_text_color;
                public Double upvote_ratio;
                public String subreddit_type;
                public Long ups;
                public Long total_awards_received;
                public Boolean is_original_content;
                public Boolean is_reddit_media_domain;
                public Boolean is_meta;
                public Boolean can_mod_post;
                public Long score;
                public Long edited;
                public Boolean is_self;
                public Long created;
                public String link_flair_type;
                public Long wls;
                public String author_flair_type;
                public String domain;
                public Boolean allow_live_comments;
                public String selftext_html;
                public Boolean archived;
                public Boolean no_follow;
                public Boolean is_crosspostable;
                public Boolean pinned;
                public Boolean over_18;
                public Boolean media_false;
                public Boolean can_gild;
                public Boolean spoiler;
                public Boolean locked;
                public Boolean visited;
                public String subreddit_id;
                public Boolean author_is_blocked;
                public String link_flair_background_color;
                public String id;
                public Boolean is_robot_indexable;
                public String author;
                public Long num_comments;
                public Boolean send_replies;
                public String whitelist_status;
                public Boolean contest_mode;
                public Boolean author_patreon_flair;
                public String permalink;
                public String parent_whitelist_status;
                public Boolean stickied;
                public String url;
                public Long subreddit_subscribers;
                public Long created_utc;
                public Long num_crossposts;
                public Boolean is_video;
            }
        }
    }
}
