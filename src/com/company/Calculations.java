package com.company;

import TileClasses.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is to perform calculations using the Bellman Equation
 */
public class Calculations {
    /**
     * Environment to perform calculations on
     */
    private Environment environment;
    /**
     * Mapping of the utilities to each tile in the environment
     */
    private EnvironmentUtilities environmentUtilities;
    /**
     * Discount factor
     */
    private double gamma;

    /**
     * Constructor for this class
     * @param environmentUtilities
     */
    public Calculations(EnvironmentUtilities environmentUtilities) {
        this.environmentUtilities = environmentUtilities;
        this.environment = environmentUtilities.getEnvironment();
        this.gamma = environmentUtilities.getGamma();
    }

    /**
     * Returns the ActionUtility with the highest Utility at a particular tile
     * @param row
     * @param col
     * @return
     * @throws Exception
     */
    public ActionUtility getHighestActionUtility(int row, int col) throws Exception {
        List<ActionUtility> actionUtilities = new ArrayList<ActionUtility>();
        actionUtilities.add(new ActionUtility("Up", getActionUtility(row, col, "Up")));
        actionUtilities.add(new ActionUtility("Right", getActionUtility(row, col, "Right")));
        actionUtilities.add(new ActionUtility("Down", getActionUtility(row, col, "Down")));
        actionUtilities.add(new ActionUtility("Left", getActionUtility(row, col, "Left")));
        ActionUtility highest = null;
        double highestUtility = -1000.00;
        for(int i = 0; i < 4; i++) {
            if(actionUtilities.get(i).getUtility() > highestUtility) {
                highestUtility = actionUtilities.get(i).getUtility();
                highest = actionUtilities.get(i);
            }
        }
        return highest;
    }

    /**
     * Returns the Utility of performing a particular action at the specified row and column
     * @param row
     * @param col
     * @param action
     * @return
     * @throws Exception
     */
    public double getActionUtility(int row, int col, String action) throws Exception {
        double utility = 0;
        if(action.equals("Up")) {
            if(checkWithinBound(row, col, "Up")) {
                utility += 0.8*environmentUtilities.getUtility(row-1, col);
            } else {
                utility += 0.8*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Left")) {
                utility += 0.1*environmentUtilities.getUtility(row, col-1);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Right")) {
                utility += 0.1*environmentUtilities.getUtility(row, col+1);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
        } else if(action.equals("Right")) {
            if(checkWithinBound(row, col, "Right")) {
                utility += 0.8*environmentUtilities.getUtility(row, col+1);
            } else {
                utility += 0.8*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Up")) {
                utility += 0.1*environmentUtilities.getUtility(row-1, col);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Down")) {
                utility += 0.1*environmentUtilities.getUtility(row+1, col);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
        } else if(action.equals("Down")) {
            if(checkWithinBound(row, col, "Down")) {
                utility += 0.8*environmentUtilities.getUtility(row + 1, col);
            } else {
                utility += 0.8*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Left")) {
                utility += 0.1*environmentUtilities.getUtility(row, col-1);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Right")) {
                utility += 0.1*environmentUtilities.getUtility(row, col+1);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
        } else if (action.equals("Left")) {
            if(checkWithinBound(row, col, "Left")) {
                utility += 0.8*environmentUtilities.getUtility(row, col-1);
            } else {
                utility += 0.8*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Up")) {
                utility += 0.1*environmentUtilities.getUtility(row-1, col);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
            if(checkWithinBound(row, col, "Down")) {
                utility += 0.1*environmentUtilities.getUtility(row+1, col);
            } else {
                utility += 0.1*environmentUtilities.getUtility(row, col);
            }
        } else {
            throw new Exception("Invalid action name");
        }
        utility = environment.getTile(row, col).getReward() + (gamma * utility);
        return utility;
    }

    /**
     * Checks if the specified row and column are within the boundaries of the environment
     * @param row
     * @param col
     * @param direction
     * @return
     * @throws Exception
     */
    private boolean checkWithinBound(int row, int col, String direction) throws Exception {
        if(direction.equals("Up")) {
            return (row-1 >= 0 && !environment.getTile(row-1, col).getTileType().equals("WALL"));
        } else if(direction.equals("Right")) {
            return (col + 1 < environment.getColumns() && !environment.getTile(row, col + 1).getTileType().equals("WALL"));
        } else if(direction.equals("Down")) {
            return (row+1 < environment.getRows() && !environment.getTile(row+1, col).getTileType().equals("WALL"));
        } else if (direction.equals("Left")) {
            return (col-1 >= 0 && environment.getTile(row, col-1).getTileType().equals("WALL"));
        } else {
            throw new Exception("Invalid Direction");
        }
    }

    /**
     * Setter for the environment utilities
     * @param environmentUtilities
     */
    public void changeEnvironmentUtilities(EnvironmentUtilities environmentUtilities) {
        this.environmentUtilities = environmentUtilities;
    }
}
