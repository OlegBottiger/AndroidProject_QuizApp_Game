package com.example.iths.asobi;

/**
 * This Player class has information about a player's name.
 * It is used to check who is playing the game currently.
 */
public class Player {
    private String name;
    private static Player player=null;

    /**
     * constructor for Player
     * @param name name to initialize
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * This makes Player class singleton.
     * @param name name to set when Player is initialized
     * @return instance of Player
     */
    public static Player getPlayerInstance(String name){
        if (player == null){
            player = new Player(name);
        }
        return player;
    }

    /**
     * sets name to instance variable name.
     * @param name name to set
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * @return String name
     */
    public String getName() {
        return name;
    }

}
