package TileClasses;

import java.io.Serializable;

/**
 * Class that represents a single tile in the environment
 */
public class Tile implements Serializable {
    /**
     * tileType represnts if the tile is green, brown, white or is a wall
     */
    private String tileType;
    /**
     * reward represents the reward of that particular tile
     */
    private double reward;

    /**
     * Constructor for tile
     * @param tileType
     * @param reward
     */
    public Tile(String tileType, double reward) {
        this.tileType = tileType;
        this.reward = reward;
    }

    /**
     * Setter for tileType
     * @param tileType
     */
    public void setTileType(String tileType) {
        this.tileType = tileType;
    }

    /**
     * Setter for reward
     * @param reward
     */
    public void setReward(double reward) {
        this.reward = reward;
    }

    /**
     * getter for tileType
     * @return
     */
    public String getTileType() {
        return tileType;
    }

    /**
     * Getter for reward
     * @return
     */
    public double getReward() {
        return reward;
    }
}
