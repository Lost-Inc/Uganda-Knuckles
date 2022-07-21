package at.lost_inc.ugandaknucklesbot.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

/**
 * Searches for YouTube videos using an instance of <a href="https://github.com/iv-org/invidious">Invidious</a>.
 *
 * @author sudo200
 */
public class YoutubeSearcher {
    private static final String instancepoint = "https://api.invidious.io/instances.json";
    private static final String testpoint = "/api/v1/stats";
    private static final String searchpoint = "/api/v1/search";
    private final OkHttpClient client;
    private final Gson gson;
    private final Random random;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private URL invidious;

    public YoutubeSearcher(@NotNull OkHttpClient httpClient, @NotNull Gson gson, @NotNull Random random) throws MalformedURLException {
        this.random = random;
        this.gson = gson;
        client = httpClient;
        changeInstanceTimer();
    }

    private void changeInstanceTimer() {
        changeInstance();
        new Timer(true).schedule(new TimerTaskRunnable(this::changeInstanceTimer), 5 * 60 * 1000);
    }

    protected void changeInstance() {
        final Request req = new Request.Builder()
                .url(instancepoint).build();

        try (final Response res = client.newCall(req).execute()) {

            final InstanceData[] instances = Arrays.stream(gson.fromJson(res.body().charStream(), JsonArray[].class))
                    .map(element -> gson.fromJson(element.get(1), InstanceData.class))
                    .filter(instance -> instance.cors && instance.api)
                    .toArray(InstanceData[]::new);

            invidious = new URL(
                    instances[random.nextInt(instances.length)].uri
            );

            final Request req1 = new Request.Builder()
                    .url(invidious).build();

            try (final Response res1 = client.newCall(req1).execute()) {
                final ResponseBody body = res1.body();
                if (body != null) body.close();

                if (!res1.isSuccessful())
                    changeInstance();
            } catch (IOException e) {
                changeInstance();
            }
            logger.debug("New invidious instance: {}", invidious.toString());
        } catch (IOException ignored) {
        }
    }

    public @NotNull String[] search(String q) {
        try {
            String s;
            final Request req = new Request.Builder()
                    .url(invidious.getProtocol()
                            + ':'
                            + ((s = invidious.getAuthority()) != null && !s.isEmpty()
                            ? "//" + s : "") + searchpoint + "?fields=videoId&q=" + URLEncoder.encode(q, StandardCharsets.UTF_8.toString()))
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

    private static class InstanceData {
        private String flag;
        private String region;
        private boolean cors;
        private boolean api;
        private String type;
        private String uri;
    }

    private static class Video {
        public String videoId;
    }
}
