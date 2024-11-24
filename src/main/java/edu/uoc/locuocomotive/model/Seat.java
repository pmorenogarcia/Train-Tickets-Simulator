package edu.uoc.locuocomotive.model;

public class Seat {
    private String id;
    private boolean isAvailable;
    private Passenger passenger;

    public Seat(String id) {
        setId(id);
        // Initialize seats
        setAvailable(true);
        setPassenger(null);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public Passenger getPassenger() {
        return passenger;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    public void assignSeat(Passenger passenger){
        setPassenger(passenger);
    }
}
