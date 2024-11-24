package edu.uoc.locuocomotive.model;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schedule {
    private int destinationId;

    private final List<LocalTime> times = new ArrayList<>();


    // Constructor
    public Schedule(int destinationId, LocalTime... times) {
        setDestinationId(destinationId);
        setTimes(times);
    }

    // Getters and Setters
    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public List<LocalTime> getTimes() {
        return times;
    }

    public void setTimes(LocalTime... times) {
        Collections.addAll(this.times, times);
    }
}
