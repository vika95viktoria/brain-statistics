package com.mindqr.statistics;


public class Main {
    public static void main(String[] args) {
        String fileName = "D://data.txt";
        StatisticsCounter statisticsCounter = new StatisticsCounter(fileName);
        double[] expectedValues = statisticsCounter.countExpectedValues();
        double[] standardDeviation = statisticsCounter.countStandardDeviation(expectedValues);
        for(int i = 1; i < expectedValues.length; i++){
            System.out.println(expectedValues[i]);
            System.out.println(standardDeviation[i]);
        }
    }
}
