package com.ivlevks.lesson02.StatsAccumulator;

public interface StatsAccumulator {

    void add(int value);

    Integer getMin();

    Integer getMax();

    int getCount();

    Double getAvg();
}
