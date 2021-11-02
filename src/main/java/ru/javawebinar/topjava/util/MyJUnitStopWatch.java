package ru.javawebinar.topjava.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class MyJUnitStopWatch extends Stopwatch {

    private List<String> list;

    public MyJUnitStopWatch(List<String> list) {
        this.list = list;
    }

    private static void logInfo(Description description, String status, long nanos, List<String> list) {
        String testName = description.getMethodName();
        String message = String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos));
        System.out.println(message);
        list.add(message);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos, this.list);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos, this.list);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos, this.list);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos, this.list);
    }
}