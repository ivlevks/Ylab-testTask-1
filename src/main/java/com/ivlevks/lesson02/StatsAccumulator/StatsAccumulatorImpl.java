package com.ivlevks.lesson02.StatsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator{
    private int totalSum = 0;
    private int counter = 0;
    private int minValue = Integer.MAX_VALUE;
    private int maxValue = Integer.MIN_VALUE;
    private Double average = 0d;

    @Override
    public void add(int value) {
        totalSum += value;
        counter++;
        minValue = Math.min(minValue, value);
        maxValue = Math.max(maxValue, value);
        average = (double) totalSum / (double) counter;
    }

    @Override
    public Integer getMin() {
        if (counter == 0) return null;
        return minValue;
    }

    @Override
    public Integer getMax() {
        if (counter == 0) return null;
        return maxValue;
    }

    @Override
    public int getCount() {
        return counter;
    }

    @Override
    public Double getAvg() {
        if (counter == 0) return null;

        // 3 sign after points
        double scale = 1000;
        return Math.round(average * scale) / scale;
    }
}
