package at.lost_inc.ugandaknucklesbot.Service.Games;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

public final class SimpleGameService implements GameService {
    private final HashMap<String, GameService.Game<?, ?>> games = new HashMap<>();

    @Override
    public void register(@NotNull GameService.Game<?, ?> game) {
        games.put(game.getId(), game);
    }

    @Override
    public @NotNull <I, O> Optional<GameService.Game<I, O>> getByName(@NotNull String name, @NotNull Class<I> inClass, @NotNull Class<O> outClass) throws ClassCastException {
        return Optional.ofNullable(
                (GameService.Game<I, O>) games.values().stream()
                        .filter(game -> game.getName().equals(name))
                        .findFirst().orElse(null)
        );
    }

    @Override
    public @NotNull <I, O> Optional<GameService.Game<I, O>> getById(@NotNull String id, @NotNull Class<I> inClass, @NotNull Class<O> outClass) throws ClassCastException {
        if (!games.containsKey(id))
            return Optional.empty();
        return Optional.ofNullable((GameService.Game<I, O>) games.get(id));
    }

    @Override
    public boolean removeById(@NotNull String id) {
        return games.remove(id, games.get(id));
    }
}
