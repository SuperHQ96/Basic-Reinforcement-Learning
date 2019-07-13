package TileClasses;

import java.io.Serializable;

/**
 * Class to represent the environment
 */
public class Environment implements Serializable {
    /**
     * 2D array of Tile class as a representation of the environment
     */
    private Tile[][] environment;
    /**
     * Total number of rows in the environment
     */
    private int noOfRows;
    /**
     * Total number of columns in the environment
     */
    private int noOfColumns;

    /**
     * Constructor for environment
     * @param rows Number of rows in the environment
     * @param columns Number of Columns in the environment
     */
    public Environment(int rows, int columns) {
        environment = new Tile[rows][columns];
        this.noOfColumns = columns;
        this.noOfRows = rows;
        for(int i = 0; i < noOfRows; i++) {
            for(int j = 0; j < noOfColumns; j++) {
                environment[i][j] = new Tile("WHITE", -0.04);
            }
        }
    }

    /**
     * Getter for the number of rows
     * @return
     */
    public int getRows() {
        return noOfRows;
    }

    /**
     * Getter for the number of rows
     * @return
     */
    public int getColumns() {
        return noOfColumns;
    }

    /**
     * Sets the tile type for the tile in the specified row and column
     * @param row
     * @param col
     * @param tileType
     */
    public void setTileType(int row, int col, String tileType) {
        environment[row][col].setTileType(tileType);
    }

    /**
     * Sets the tile reward for the tile in the specified row and column
     * @param row
     * @param col
     * @param reward
     */
    public void setTileReward (int row, int col, double reward) {
        environment[row][col].setReward(reward);
    }

    /**
     * Returns the Tile Object at the specified row and column
     * @param row
     * @param col
     * @return
     */
    public Tile getTile(int row, int col) {
        return environment[row][col];
    }
}
