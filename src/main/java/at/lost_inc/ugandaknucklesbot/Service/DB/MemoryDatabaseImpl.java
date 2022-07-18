package at.lost_inc.ugandaknucklesbot.Service.DB;

import at.lost_inc.ugandaknucklesbot.Util.JsonObjectFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryDatabaseImpl extends AbstractDatabaseService {
    private final Map<String, DataStore> stores = new ConcurrentHashMap<>();

    @Override
    public @NotNull DataStore getDataStore(String name) {
        if(stores.containsKey(name))
            return stores.get(name);

        final DataStore store = new MemoryDataStore();
        stores.put(name, store);
        return store;
    }

    @Override
    public @NotNull String[] getDataStores() {
        return stores.keySet().toArray(new String[0]);
    }

    private static class MemoryDataStore extends DataStore {
        private final Set<JsonObject> objects = Collections.synchronizedSet(new LinkedHashSet<>());

        private static boolean lazilyCompareObjects(@NotNull JsonObject object, @NotNull JsonObject compare) {
            for(final Map.Entry<String, JsonElement> entry : compare.entrySet()) {
                if (!object.has(entry.getKey())) // Check if object got keys from comparison
                    return false;

                final JsonElement objectElement = object.get(entry.getKey());
                final JsonElement compareElement = entry.getValue();

                if(objectElement == compareElement)
                    continue;
                if(objectElement == null || compareElement == null)
                    return false;
                if(!compareElement.equals(objectElement))
                    return false;
            }
            return true;
        }

        @Override
        public void insert(JsonObject @NotNull ... object) {
            objects.addAll(Arrays.asList(object));
        }

        @Override
        public @NotNull List<JsonObject> find(@NotNull JsonObject query) {
            final Set<String> keys = query.keySet();
            final List<JsonObject> results = new LinkedList<>();
            for(final JsonObject object : objects)
                if(lazilyCompareObjects(object, query))
                    results.add(object);
            return results;
        }

        @Override
        public @NotNull List<JsonObject> find() {
            return new LinkedList<>(objects);
        }

        @Override
        public Optional<JsonObject> updateFirst(@NotNull JsonObject query, @NotNull JsonObject object) {
            for(final JsonObject object1 : objects)
                if(lazilyCompareObjects(object1, query)) {
                    final JsonObject copyObject1 = object1.deepCopy();
                    for(final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        if(object1.has(entry.getKey()))
                            object1.remove(entry.getKey());
                        if(!entry.getValue().isJsonNull())
                            object1.add(entry.getKey(), entry.getValue());
                    }
                    return Optional.of(copyObject1);
                }

            return Optional.empty();
        }

        @Override
        public List<JsonObject> updateAll(@NotNull JsonObject query, @NotNull JsonObject object) {
            final List<JsonObject> results = new LinkedList<>();

            for (final JsonObject object1 : objects)
                if(lazilyCompareObjects(object1, query)) {
                    results.add(object1.deepCopy());
                    for(final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        if(object1.has(entry.getKey()))
                            object1.remove(entry.getKey());
                        if(!entry.getValue().isJsonNull())
                            object1.add(entry.getKey(), entry.getValue());
                    }
                }

            return results;
        }

        @Override
        public Optional<JsonObject> deleteFirst(@NotNull JsonObject query) {
            for(final JsonObject object : objects)
                if(lazilyCompareObjects(object, query)) {
                    objects.remove(object);
                    return Optional.of(object);
                }

            return Optional.empty();
        }

        @Override
        public List<JsonObject> deleteAll(@NotNull JsonObject query) {
            final List<JsonObject> results = new LinkedList<>();

            objects.removeIf(object -> {
                final boolean rm = lazilyCompareObjects(object, query);
                if(rm) results.add(object);
                return rm;
            });

            return results;
        }

        @Override
        public Object getNativeStructure() {
            return objects;
        }
    }
}
