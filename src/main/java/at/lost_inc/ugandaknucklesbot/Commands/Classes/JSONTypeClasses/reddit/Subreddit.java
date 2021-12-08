package at.lost_inc.ugandaknucklesbot.Commands.Classes.JSONTypeClasses.reddit;

public class Subreddit {
    String kind;
    SubredditData data;

    public static class SubredditData {
        String after;
        String before;
        PostElement[] children;
        Long dist;
        String modhash;
        String geo_filter;
    }

    public static class PostElement {
        String kind;
        PostData data;
    }

    public static class PostData {
        String approved_at_utc;
        String subreddit;
        String selftext;
        String author_fullname;
        Boolean saved;
        String mod_reason_title;
        Long gilded;
        Boolean clicked;
        String title;
        String subreddit_name_prefixed;
        Boolean hidden;
        Long pwls;
        String link_flair_css_class;
        Long downs;
        Boolean hide_score;
        String name;
        Boolean quarantine;
        String link_flair_text_color;
        Float upvote_ratio;
        String author_flair_background_color;
        String subreddit_type;
        Long ups;
        Long total_awards_given;
        String author_flair_template_id;
        Boolean is_original_content;
        Boolean is_reddit_media_domain;
        Boolean is_meta;
        String link_flair_text;
        Boolean can_mod_post;
        Long score;
        Boolean is_created_from_ads_ui;
        Boolean author_premium;
        String thumbnail;
        Long edited;
        Boolean is_self;
        Long created;
        String author_flair_type;
        Long wls;
        String domain;
        Boolean allow_live_comments;
        String selftext_html;
        Boolean archived;
        Boolean no_follow;
        Boolean is_crosspostable;
        Boolean pinned;
        Boolean over_18;
        Boolean media_only;
        String link_flair_template_id;
        Boolean can_gild;
        Boolean spoiler;
        Boolean locked;
        String author_flair_text;
        Boolean visited;
        String distinguished;
        String subreddit_id;
        Boolean author_is_blocked;
        String link_flair_background_color;
        String id;
        Boolean is_robot_indexable;
        String author;
        Long num_comments;
        Boolean send_replies;
        String whitelist_status;
        Boolean contest_mode;
        Boolean author_patreon_flair;
        String author_flair_text_color;
        String permalink;
        String parent_whitelist_status;
        Boolean stickied;
        String url;
        Long subreddit_subscribers;
        Long created_utc;
        Long num_crossposts;
        Boolean is_video;
    }
}
