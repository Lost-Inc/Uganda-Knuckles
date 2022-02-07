package at.lost_inc.ugandaknucklesbot.Service.Games;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.TypeVariable;
import java.util.*;

public interface GameService {
    /**
     * Registers a game
     * @param game Instance to register
     */
    void register(@NotNull Game<?, ?> game);

    /**
     * Get a game by its name
     * @param name the game name
     * @param inClass class of the input object
     * @param outClass class of the output object
     * @return If game with name exists -> {@link Game} wrapped in an {@link Optional}; else {@link Optional#empty()}
     * @throws ClassCastException if game with name exists, but input- and output object do not match with the game
     */
    @NotNull <I, O> Optional<Game<I, O>> getByName(@NotNull String name, @NotNull Class<I> inClass, @NotNull Class<O> outClass) throws ClassCastException;

    /**
     * Get a game by its id
     * @param id the id of the game
     * @param inClass class of the input object
     * @param outClass class of the output object
     * @return If game with name exists -> {@link Game} wrapped in an {@link Optional}; else {@link Optional#empty()}
     * @throws ClassCastException if game with name exists, but input- and output object do not match with the game
     */
    @NotNull <I, O> Optional<Game<I, O>> getById(@NotNull String id, @NotNull Class<I> inClass, @NotNull Class<O> outClass) throws ClassCastException;


    /**
     * Removes a game by its id
     * @param id the game's id
     * @return {@link Boolean#TRUE} if removed
     */
    boolean removeById(@NotNull String id);

    /**
     * Interface describing a game
     * @param <Input> Data type of data from players to the game
     * @param <Output> Data type of data from the game to the players
     */
    interface Game<Input, Output> {
        /**
         * Should just return the game's name
         * @return name of game
         */
        @NotNull String getName();


        /**
         * Returns the game's id
         * @return id
         */
        @NotNull String getId();

        /**
         * Called to check, if the player made a correct guess
         * @param input the data to check
         * @return {@link GameState#OKAY} if correct; {@link GameState#WRONG} if incorrect; {@link GameState#GAME_OVER} if game over; {@link GameState#WIN} if player won the game
         */
        @NotNull GameState check(@NotNull Input input);

        /**
         * Called after {@link Game#check(Object)} to give feedback to the player
         * @return {@link Output} wrapped in an {@link Optional}, or {@link Optional#empty()} if there is no message
         */
        @NotNull Optional<Output> getMsg();

        /**
         *  An enum containing the constants describing the different states
         *  a game can be in
         */
        enum GameState {
            OKAY,
            WRONG,
            GAME_OVER,
            WIN;

            /**
             * @return {@link Boolean#TRUE} if game is finished, that is either {@link GameState#WIN} or {@link GameState#GAME_OVER}
             */
            public boolean isOver() {
                return this.equals(GAME_OVER) || this.equals(WIN);
            }
        }
    }
}
