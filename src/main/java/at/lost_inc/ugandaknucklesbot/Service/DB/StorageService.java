package at.lost_inc.ugandaknucklesbot.Service.DB;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface StorageService {

    /**
     * Get the {@link DataStore} with the given name.
     * Implicitly creates it, if non-existent.
     * @param name Name of the {@link DataStore}.
     * @return The {@link DataStore}.
     */
    @NotNull DataStore getDataStore(String name);

    /**
     * Get the names of all the {@link DataStore}s.
     * @return Array of names.
     */
    @NotNull String[] getDataStores();

    /**
     * A container for data.
     * Data can be inserted, updated, deleted and searched.
     *
     * Additionally, you can get the Object behind the DataStore directly (not recommended, implementation defined).
     */
    abstract class DataStore {
        protected DataStore() {
        }

        public abstract void insert(JsonObject @NotNull ... object);
        public abstract @NotNull List<JsonObject> find(@NotNull JsonObject query);
        public abstract Optional<JsonObject> updateFirst(@NotNull JsonObject query, @NotNull JsonObject object);
        public abstract List<JsonObject> updateAll(@NotNull JsonObject query, @NotNull JsonObject object);
        public abstract Optional<JsonObject> deleteFirst(@NotNull JsonObject query);
        public abstract List<JsonObject> deleteAll(@NotNull JsonObject query);

        public abstract Object getNativeStructure();
    }
}
