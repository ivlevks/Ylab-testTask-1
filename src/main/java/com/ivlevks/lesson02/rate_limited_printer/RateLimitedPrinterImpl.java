package com.ivlevks.lesson02.rate_limited_printer;

public class RateLimitedPrinterImpl implements RateLimitedPrinter{
    private final int interval;
    private long previousTimePrint = 0;

    public RateLimitedPrinterImpl(int interval) {
        this.interval = interval;
    }

    @Override
    public void print(String message){
        long currentTime = System.currentTimeMillis();
        if (previousTimePrint == 0 || currentTime - previousTimePrint >= interval) {
            previousTimePrint = currentTime;
            System.out.println(message);
        }
    }
}
