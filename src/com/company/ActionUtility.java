package com.company;

import java.io.Serializable;

import static java.lang.System.err;

/**
 * Class to represent the utility of a particular action
 */
public class ActionUtility implements Serializable {
    /**
     * Action can be up, down, left or right
     */
    private String action;
    /**
     * Represents the utility of taking a particular action at a particular state in the environment
     */
    private double utility;

    /**
     * Constructor for this class
     * @param action
     * @param utility
     */
    public ActionUtility(String action, double utility){
        this.action = action;
        this.utility = utility;
    }

    /**
     * Getter for the action
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * Getter for the utility
     * @return
     */
    public double getUtility() {
        return utility;
    }

    /**
     * Setter for the action
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Setter for the utility
     * @param utility
     */
    public void setUtility(double utility) {
        this.utility = utility;
    }
}
