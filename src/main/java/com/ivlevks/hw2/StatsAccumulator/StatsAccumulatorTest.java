package com.ivlevks.hw2.StatsAccumulator;

public class StatsAccumulatorTest {

    public static void main(String[] args) {
        StatsAccumulator statsAccumulator = new StatsAccumulatorImpl();

        System.out.println(statsAccumulator.getAvg());
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getCount());
        System.out.println();

        statsAccumulator.add(-100);
        statsAccumulator.add(2);
        statsAccumulator.add(4);
        System.out.println(statsAccumulator.getAvg());
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getCount());
        System.out.println();

        statsAccumulator.add(0);
        statsAccumulator.add(3);
        statsAccumulator.add(8);
        System.out.println(statsAccumulator.getAvg());
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getCount());
    }
}
