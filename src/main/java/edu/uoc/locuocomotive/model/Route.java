package edu.uoc.locuocomotive.model;

import java.util.ArrayList;
import java.util.List;


public class Route {
    private String id;
    private int trainId;
    private List<Schedule> schedules = new ArrayList<>();

    public Route(String id, int trainId, List<Schedule> schedules) {
        setId(id);
        setTrainId(trainId);
        setSchedules(schedules);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
