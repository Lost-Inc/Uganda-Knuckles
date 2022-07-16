package at.lost_inc.ugandaknucklesbot.Service.DB;

import org.jetbrains.annotations.NotNull;

public class MemoryDatabaseImpl extends AbstractDatabaseService {
    @Override
    public @NotNull DataStore getDataStore(String name) {
        return null;
    }

    @Override
    public @NotNull String[] getDataStores() {
        return new String[0];
    }
}
