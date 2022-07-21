package at.lost_inc.ugandaknucklesbot.Service.DB;

import com.google.gson.JsonObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MongoDBDatabaseImpl extends AbstractDatabaseService {
    private static final String DATABASE = "Uganda_Knuckles";
    private static final String URL_PROPERTY = "at.lost_inc.ugandaknucklesbot.db.url";

    private final MongoClient client = MongoClients.create(
            MongoClientSettings.builder()
                    .codecRegistry(
                            CodecRegistries.fromRegistries(
                                    MongoClientSettings.getDefaultCodecRegistry(),
                                    CodecRegistries.fromProviders(
                                            PojoCodecProvider.builder().register(JsonObject.class).build()
                                    )
                            )
                    )
                    .applyConnectionString(new ConnectionString(System.getProperty(URL_PROPERTY)))
                    .build()
    );
    private final MongoDatabase db = client.getDatabase(DATABASE);

    public MongoDBDatabaseImpl() {
    }

    @Override
    public @NotNull DataStore getDataStore(String name) {
        final boolean collectionExists = db.listCollectionNames()
                .into(new HashSet<>()).contains(name);

        if (!collectionExists)
            db.createCollection(name);
        return new MongoDBDatastoreImpl(db.getCollection(name, JsonObject.class));
    }

    @Override
    public @NotNull String[] getDataStores() {
        final List<String> dataStores = new ArrayList<>();
        db.listCollectionNames().forEach(dataStores::add);
        return dataStores.toArray(new String[0]);
    }

    private static class MongoDBDatastoreImpl extends DataStore {
        private final MongoCollection<JsonObject> collection;

        MongoDBDatastoreImpl(MongoCollection<JsonObject> collection) {
            this.collection = collection;
        }

        @Override
        public void insert(JsonObject @NotNull ... object) {
            collection.insertMany(Arrays.asList(object));
        }

        @Override
        public @NotNull List<JsonObject> find(@NotNull JsonObject query) {
            final List<JsonObject> items = new ArrayList<>();

            return items;
        }

        @Override
        public @NotNull List<JsonObject> find() {
            final List<JsonObject> items = new ArrayList<>();
            collection.find().forEach(items::add);
            return items;
        }

        @Override
        public Optional<JsonObject> updateFirst(@NotNull JsonObject query, @NotNull JsonObject object) {
            return Optional.empty();
        }

        @Override
        public List<JsonObject> updateAll(@NotNull JsonObject query, @NotNull JsonObject object) {
            return null;
        }

        @Override
        public Optional<JsonObject> deleteFirst(@NotNull JsonObject query) {
            return Optional.empty();
        }

        @Override
        public List<JsonObject> deleteAll(@NotNull JsonObject query) {
            return null;
        }

        @Override
        public Object getNativeStructure() {
            return collection;
        }
    }
}
