package at.lost_inc.ugandaknucklesbot.Util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public final class JsonObjectFactory {
    private static final Logger logger = LoggerFactory.getLogger(JsonObjectFactory.class);
    private static final Gson gson = new Gson();

    private JsonObjectFactory() {
    }

    public static @NotNull JsonObject fromMap(@NotNull Map<String, JsonElement> map) {
        final JsonObject object = new JsonObject();
        map.forEach(object::add);
        return object;
    }

    public static @NotNull JsonObject fromObject(@NotNull Object obj) {
        final JsonObject jsonObject = new JsonObject();

        for(final Field field : obj.getClass().getDeclaredFields())
            try {
                field.setAccessible(true);
                final Object fieldVal = field.get(obj);
                if(fieldVal instanceof Number)
                    jsonObject.addProperty(field.getName(), (Number) fieldVal);
                else if(fieldVal instanceof String)
                    jsonObject.addProperty(field.getName(), (String) fieldVal);
                else if(fieldVal instanceof Character)
                    jsonObject.addProperty(field.getName(), (Character) fieldVal);
                else if(fieldVal instanceof Boolean)
                    jsonObject.addProperty(field.getName(), (Boolean) fieldVal);
            } catch (Exception e) {
                logger.error("Error while building JsonObject!", e);
            }

        return jsonObject;
    }
}
