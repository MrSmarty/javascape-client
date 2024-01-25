package com.javascape.chronjob;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Chronjob extends Job {

    public int period;

    public TimeUnit timeUnit;

    public Chronjob(String name, ArrayList<String> commands, int period, TimeUnit timeUnit) {
        super(name, commands);
        this.period = period;
        this.timeUnit = timeUnit;
    }

    public int getPeriod() {
        return period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
