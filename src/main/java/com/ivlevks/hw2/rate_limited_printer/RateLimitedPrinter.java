package com.ivlevks.hw2.rate_limited_printer;

public class RateLimitedPrinter {
    private final int interval;
    private long previousTimePrint = 0;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message){
        long currentTime = System.currentTimeMillis();
        if (previousTimePrint == 0 || currentTime - previousTimePrint >= interval) {
            previousTimePrint = currentTime;
            System.out.println(message);
        }
    }
}
