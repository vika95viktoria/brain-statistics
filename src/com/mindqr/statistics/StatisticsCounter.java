package com.mindqr.statistics;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiFunction;

public class StatisticsCounter {
    private String fileName;

    public StatisticsCounter(String fileName) {
        this.fileName = fileName;
    }

    private double[] countFromFile(BiFunction<String, Double, Double> countFunction,
                                   BiFunction<Double, Double, Double> finalCountFunction,
                                   double[] mean) {
        int length = 0;
        double[] elements = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            double count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (count == 0) {
                    length = split.length;
                    elements = new double[length];
                    for (int i = 0; i < length; i++) {
                        elements[i] = 0.0;
                    }
                }
                for (int i = 1; i < split.length; i++) {
                    double parameter = 0;
                    if (mean != null) {
                        parameter = mean[i];
                    }
                    elements[i] = elements[i] + countFunction.apply(split[i], parameter);
                }
                count++;
            }
            for (int i = 0; i < length; i++) {
                elements[i] = finalCountFunction.apply(elements[i], count);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

    private double countDeviationPart(String element, Double expected) {
        return Math.pow(Double.parseDouble(element) - expected, 2);
    }

    private double countExpectedPart(String element, Double expected) {
        return Double.parseDouble(element);
    }

    private double finalCountExpected(double element, double count) {
        return element / count;
    }

    private double finalCountDeviation(double element, double count) {
        return Math.pow(element / count, 0.5 * 0.5);
    }

    public double[] countExpectedValues() {
        return countFromFile(this::countExpectedPart, this::finalCountExpected, null);
    }

    public double[] countStandardDeviation(double[] mean) {
        return countFromFile(this::countDeviationPart, this::finalCountDeviation, mean);
    }
}
