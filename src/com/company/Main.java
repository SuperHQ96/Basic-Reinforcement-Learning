package com.company;

import TileClasses.Environment;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {
    /**
     * Main function to run either value iteration or policy iteration
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //runProgram("Value Iteration");
        runProgram("Policy Iteration");
    }

    /**
     * Function to run either value iteration or policy iteration
     * @param choice
     * @throws Exception
     */
    public static void runProgram(String choice) throws Exception {
        Environment environment = generateEnvironment();
        double gamma = 0.99;
        double error = 0.1;

        if(choice.equals("Value Iteration")) {
            EnvironmentUtilities environmentUtilities = valueIteration(environment, gamma, error);
            System.out.println("Value Iteration===========================================================");
            System.out.println();
            System.out.println("Optimal Policy:");
            environmentUtilities.displayPolicy();
            System.out.println();
            System.out.println("Utilities:");
            environmentUtilities.displayUtilities();
        }
        if(choice.equals("Policy Iteration")) {
            EnvironmentUtilities environmentUtilities = policyIteration(environment, gamma, 12);
            System.out.println("Policy Iteration===========================================================");
            System.out.println();
            System.out.println("Optimal Policy:");
            environmentUtilities.displayPolicy();
            System.out.println();
            System.out.println("Utilities:");
            environmentUtilities.displayUtilities();
        }
    }

    /**
     * Value Iteration Function
     * @param environment
     * @param gamma
     * @param error
     * @return
     * @throws Exception
     */
    public static EnvironmentUtilities valueIteration(Environment environment, double gamma, double error) throws Exception {
        int iterations = 0;
        double[][] utilities;
        List<double[][]> utilityLog = new ArrayList<double[][]>();
        double newUtility;
        double curUtility;
        int noOfRows = environment.getRows();
        int noOfCols = environment.getColumns();
        double error2;
        double highestError;
        double stoppingCriteria = error*((1.00 - gamma) / gamma);
        EnvironmentUtilities newEnvironmentUtilities = new EnvironmentUtilities(environment, gamma);
        EnvironmentUtilities environmentUtilities;
        Calculations calculator = new Calculations(newEnvironmentUtilities);

        do{
            environmentUtilities = (EnvironmentUtilities) deepCopy(newEnvironmentUtilities);
            calculator.changeEnvironmentUtilities(environmentUtilities);
            utilities = new double[noOfRows][noOfCols];
            highestError = 0.00;
            for(int i = 0 ; i < noOfRows ; i++) {
                for(int j = 0 ; j < noOfCols ; j++) {
                    if(environment.getTile(i, j).getTileType().equals("WALL")) {
                        continue;
                    }

                    newEnvironmentUtilities.setActionUtility(i,j,calculator.getHighestActionUtility(i, j));
                    newUtility = newEnvironmentUtilities.getUtility(i,j);
                    curUtility = environmentUtilities.getUtility(i,j);

                    error2 = Math.abs(newUtility - curUtility);

                    highestError = Math.max(highestError, error2);

                    utilities[i][j] = newUtility;
                }
            }
            utilityLog.add(utilities);
            iterations++;
        } while(highestError >= stoppingCriteria);

        System.out.println("Number of iterations: " + iterations);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        double m;
        for(int i = 0; i < utilityLog.size(); i++) {

            m = utilityLog.get(i)[0][0];
            for(int r=0;r<6;r++){
                for(int k=0;k<6;k++)
                    if(utilityLog.get(i)[r][k] > m){
                        m = utilityLog.get(i)[r][k];
                    }
            }
            dataset.addValue( m , "Highest Utility" , Integer.toString(i) );

        }

        Plot chart = new Plot("Utility vs Iterations" ,"Highest Utility Estimate vs Number of Iterations", dataset);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        return newEnvironmentUtilities;
    }

    /**
     * Policy Iteration Function
     * @param environment
     * @param gamma
     * @param iterations
     * @return
     * @throws Exception
     */
    public static EnvironmentUtilities policyIteration(Environment environment, double gamma, int iterations) throws Exception {
        HashMap<Integer, String> actions = new HashMap<Integer, String>();
        actions.put(0, "Up");
        actions.put(1, "Down");
        actions.put(2, "Left");
        actions.put(3, "Right");
        double[][] utilities;
        Random rand = new Random();
        List<double[][]> utilityLog = new ArrayList<double[][]>();
        boolean unchanged;
        String tempAction;
        int noOfIterations = 0;
        int noOfRows = environment.getRows();
        int noOfColumns = environment.getColumns();
        ActionUtility newActionUtility;
        double newUtility;
        double curUtility;
        EnvironmentUtilities newEnvironmentUtilities = new EnvironmentUtilities(environment, gamma);
        EnvironmentUtilities environmentUtilities;
        for (int i=0; i<noOfRows; i++) {
            for (int j=0; j<noOfColumns; j++) {
                if(environment.getTile(i, j).getTileType().equals("WALL")) {
                    continue;
                }
                String action = actions.get(rand.nextInt(4));
                newEnvironmentUtilities.getActionUtility(i, j).setAction(action);
            }
        }
        Calculations calculator = new Calculations(newEnvironmentUtilities);
        do {
            for (int i=0;i<iterations;i++) {
                environmentUtilities = (EnvironmentUtilities) deepCopy(newEnvironmentUtilities);
                calculator.changeEnvironmentUtilities(environmentUtilities);

                for (int j = 0; j < noOfRows; j++) {
                    for (int k = 0; k < noOfColumns; k++) {
                        if(environment.getTile(j, k).getTileType().equals("WALL")) {
                            continue;
                        }

                        tempAction = environmentUtilities.getAction(j, k);
                        newUtility = calculator.getActionUtility(j, k, tempAction);
                        newEnvironmentUtilities.setUtility(j, k, newUtility);
                    }
                }
            }
            unchanged = true;
            environmentUtilities = (EnvironmentUtilities) deepCopy(newEnvironmentUtilities);
            calculator.changeEnvironmentUtilities(environmentUtilities);

            utilities = new double[noOfRows][noOfColumns];

            for (int i = 0; i < noOfRows; i++) {
                for (int j = 0; j < noOfColumns; j++) {
                    if(environment.getTile(i, j).getTileType().equals("WALL")) {
                        continue;
                    }
                    newActionUtility = calculator.getHighestActionUtility(i, j);
                    newUtility = newActionUtility.getUtility();
                    curUtility = environmentUtilities.getUtility(i, j);

                    if (newUtility > curUtility) {
                        newEnvironmentUtilities.setAction(i, j, newActionUtility.getAction());
                        unchanged = false;
                    }

                    utilities[i][j] = curUtility;
                }
            }
            utilityLog.add(utilities);
            noOfIterations++;
        }while(unchanged != true);
        System.out.println("Number of iterations: " + noOfIterations);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        double m;
        for(int i = 0; i < utilityLog.size(); i++) {

            m = utilityLog.get(i)[0][0];
            for(int r=0;r<6;r++){
                for(int k=0;k<6;k++)
                    if(utilityLog.get(i)[r][k] > m){
                        m = utilityLog.get(i)[r][k];
                    }
            }
            dataset.addValue( m , "Highest Utility" , Integer.toString(i) );

        }

        Plot chart = new Plot("Utility vs Iterations" ,"Highest Utility Estimate vs Number of Iterations", dataset);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        return newEnvironmentUtilities;
    }

    /**
     * Generates the initial environment as specified in the assignment description
     * @return
     */
    public static Environment generateEnvironment() {
        Environment environment = new Environment(6,6);

        environment.setTileType(0, 0, "GREEN");
        environment.setTileReward(0,0, 1);
        environment.setTileType(0, 1, "WALL");
        environment.setTileReward(0,1,0);
        environment.setTileType(0, 2, "GREEN");
        environment.setTileReward(0,2,1);
        environment.setTileType(0, 5, "GREEN");
        environment.setTileReward(0,5,1);

        environment.setTileType(1, 1, "BROWN");
        environment.setTileReward(1,1,-1);
        environment.setTileType(1, 3, "GREEN");
        environment.setTileReward(1,3,1);
        environment.setTileType(1, 4, "WALL");
        environment.setTileReward(1,4,0);
        environment.setTileType(1, 5, "BROWN");
        environment.setTileReward(1,5,-1);

        environment.setTileType(2, 2, "BROWN");
        environment.setTileReward(2,2,-1);
        environment.setTileType(2, 4, "GREEN");
        environment.setTileReward(2,4,1);

        environment.setTileType(3, 3, "BROWN");
        environment.setTileReward(3,3,-1);
        environment.setTileType(3, 5, "GREEN");
        environment.setTileReward(3,5,1);

        environment.setTileType(4, 1, "WALL");
        environment.setTileReward(4,1,0);
        environment.setTileType(4, 2, "WALL");
        environment.setTileReward(4,2,0);
        environment.setTileType(4, 3, "WALL");
        environment.setTileReward(4,3,0);
        environment.setTileType(4, 4, "BROWN");
        environment.setTileReward(4,4,-1);

        return environment;
    }

    /**
     * Performs deep copy for reference type objects
     */
    public static Object deepCopy(Object originalObject){
        Object newObject = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(originalObject);
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.out.println("Error:" + ex.getMessage());
            return null;
        }

        try {
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            newObject = in.readObject();
            return newObject;
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        }
    }

}
