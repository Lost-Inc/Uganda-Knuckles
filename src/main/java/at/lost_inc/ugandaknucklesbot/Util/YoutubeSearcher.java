package at.lost_inc.ugandaknucklesbot.Util;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Searches for YouTube videos using an instance of <a href="https://github.com/iv-org/invidious">Invidious</a>.
 *
 * @author sudo200
 */
public class YoutubeSearcher {
    private static final String testpoint = "/api/v1/stats";
    private static final String searchpoint = "/api/v1/search?fields=videoId&q=";

    private static final URL defaultInstance;

    static {
        URL temp;
        try {
            temp = new URL("https://invidious.flokinet.to/");
        } catch (MalformedURLException e) {
            temp = null; // If this happens, please fix it, this should always work without an exception...
        }
        defaultInstance = temp;
    }

    private final URL invidious;
    private final OkHttpClient client;
    private final Gson gson;

    public YoutubeSearcher() throws MalformedURLException {
        this(defaultInstance);
    }

    public YoutubeSearcher(@NotNull URL instance) throws MalformedURLException {
        this(new OkHttpClient(), instance);
    }

    public YoutubeSearcher(@NotNull OkHttpClient httpClient) throws MalformedURLException {
        this(httpClient, defaultInstance);
    }

    public YoutubeSearcher(@NotNull OkHttpClient client, @NotNull URL instance) throws MalformedURLException {
        this(client, new Gson(), instance);
    }

    public YoutubeSearcher(@NotNull Gson gson) throws MalformedURLException {
        this(gson, defaultInstance);
    }

    public YoutubeSearcher(@NotNull OkHttpClient httpClient, @NotNull Gson gson) throws MalformedURLException {
        this(httpClient, gson, defaultInstance);
    }

    public YoutubeSearcher(@NotNull Gson gson, @NotNull URL instance) throws MalformedURLException {
        this(new OkHttpClient(), gson, instance);
    }

    public YoutubeSearcher(@NotNull OkHttpClient httpClient, @NotNull Gson gson, @NotNull URL instance) throws MalformedURLException {
        String s;
        final Request req = new Request.Builder()
                .url(
                        instance.getProtocol()
                                + ':'
                                + ((s = instance.getAuthority()) != null && !s.isEmpty()
                                ? "//" + s : "") + testpoint
                ).build();

        try (final Response res = httpClient.newCall(req).execute()) {
            final ResponseBody body = res.body();
            if (body != null) body.close();

            if (!res.isSuccessful())
                throw new MalformedURLException();
        } catch (IOException e) {
            throw new MalformedURLException(e.getMessage());
        }

        invidious = instance;
        this.gson = gson;
        client = httpClient;
    }

    public @NotNull String[] search(String q) {
        try {
            String s;
            final Request req = new Request.Builder()
                    .url(invidious.getProtocol()
                            + ':'
                            + ((s = invidious.getAuthority()) != null && !s.isEmpty()
                            ? "//" + s : "") + searchpoint + URLEncoder.encode(q, StandardCharsets.UTF_8.toString()))
                    .build();

            try (final Response res = client.newCall(req).execute()) {
                final Video[] search = gson.fromJson(res.body().charStream(), Video[].class);

                return Arrays.stream(search).map(vid -> vid.videoId).toArray(String[]::new);
            } catch (IOException e) {
                return new String[0];
            }
        } catch (UnsupportedEncodingException ignored) {
            return new String[0];
        }
    }

    private static class Video {
        public String videoId;
    }
}
