package com.company;

import TileClasses.Environment;

import java.io.Serializable;

/**
 * Class to keep track of the utilities at each tile in the whole environment
 */
public class EnvironmentUtilities implements Serializable {
    /**
     * The environment to be represented
     */
    private Environment environment;
    /**
     * A 2D array of ActionUtility object to keep track of the best action to take at each tile in the whole environment
     */
    private ActionUtility[][] environmentUtilities;
    /**
     * Discount factor
     */
    private double gamma;

    /**
     * Constructor for this class
     * @param environment
     * @param gamma
     */
    public EnvironmentUtilities(Environment environment, double gamma) {
        this.environment = environment;
        this.gamma = gamma;
        int noOfRows = environment.getRows();
        int noOfCols = environment.getColumns();
        environmentUtilities = new ActionUtility[noOfRows][noOfCols];
        for(int i = 0; i < noOfRows; i++){
            for(int j = 0; j <noOfCols; j++) {
                environmentUtilities[i][j] = new ActionUtility(null, 0.00);
            }
        }
    }

    /**
     * Getter for the ActionUtility class at the specified row and column
     * @param row
     * @param col
     * @return
     */
    public ActionUtility getActionUtility(int row, int col) {
        return environmentUtilities[row][col];
    }

    /**
     * Getter for the utility at the specified row and column
     * @param row
     * @param col
     * @return
     */
    public double getUtility(int row, int col) {
        return environmentUtilities[row][col].getUtility();
    }

    /**
     * Getter for the action to be taken at the specified row and column
     * @param row
     * @param col
     * @return
     */
    public String getAction(int row, int col){
        return environmentUtilities[row][col].getAction();
    }

    /**
     * Returns the environment
     * @return
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Getter for the discount factor
     * @return
     */
    public double getGamma() {
        return this.gamma;
    }

    /**
     * Setter for the action utility at the specified row and column
     * @param row
     * @param col
     * @param au
     */
    public void setActionUtility(int row, int col, ActionUtility au) {
        environmentUtilities[row][col] = au;
    }

    /**
     * Setter for the utility at the specified row and column
     * @param row
     * @param col
     * @param utility
     */
    public void setUtility (int row, int col, double utility) {
        environmentUtilities[row][col].setUtility(utility);
    }

    /**
     * Setter for the action at the specified row and column
     * @param row
     * @param col
     * @param action
     */
    public void setAction(int row, int col, String action){
        environmentUtilities[row][col].setAction(action);
    }

    /**
     * This function displays the utilities map
     */
    public void displayUtilities() {
        double utility;
        int noOfRows = environment.getRows();
        int noOfColumns = environment.getColumns();

        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                if(!environment.getTile(i, j).getTileType().equals("WALL")) {
                    utility = environmentUtilities[i][j].getUtility();
                    System.out.println("("+i+","+j+")"+utility);
                }
            }
        }
    }

    /**
     * This function gies the plot of the optimal policy
     */
    public void displayPolicy() {
        int noOfRows = environment.getRows();
        int noOfColumns = environment.getColumns();

        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                if (!environment.getTile(i, j).getTileType().equals("WALL")) {
                    String action = environmentUtilities[i][j].getAction();
                    switch(action){
                        case "Up":
                            action = "^";
                            break;
                        case "Right":
                            action = ">";
                            break;
                        case "Left":
                            action = "<";
                            break;
                        case "Down":
                            action = "v";
                            break;
                    }
                    System.out.print("[" + action + "]");
                }
                else {
                    System.out.print("[X]");
                }
            }
            System.out.print("\n");
        }
    }
}
