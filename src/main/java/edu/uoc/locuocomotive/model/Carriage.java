package edu.uoc.locuocomotive.model;
import java.util.ArrayList;
import java.util.List;

public class Carriage {
    private String id;
    private List<Seat> seats;
    private CarriageClass carriageClass;
    private CarriageType carriageType;

    public Carriage(String id, int seatsNum) {
        setId(id);
        setSeats(seatsNum);
        setCarriageClass(seatsNum);
        setCarriageType(seatsNum);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Seat> getSeats() {
        return seats;
    }
    public void setSeats(int seatsNum) {
        List<Seat> seats = new ArrayList<>();
        for(int i=0; i < seatsNum; i++){
            String seatId = this.id + "-" + (i+1);
            Seat seat = new Seat(seatId);
            seats.add(seat);
        }
        this.seats = seats;
    }
    public CarriageClass getCarriageClass() {
        return carriageClass;
    }
    public void setCarriageClass(int seatsNum) {
        // Set the carriage class according to the number of seats it has
        if(seatsNum <= 20){
            this.carriageClass = CarriageClass.FIRST_CLASS;
        } else if (seatsNum <= 49) {
            this.carriageClass = CarriageClass.SECOND_CLASS;
        } else {
            this.carriageClass = CarriageClass.THIRD_CLASS;
        }
    }
    public CarriageType getType() {
        return carriageType;
    }
    public void setCarriageType(int seatsNum) {
        // Set the carriage type according to the number of seats it has
        if(seatsNum == 0){
            this.carriageType = CarriageType.RESTAURANT;
        } else {
            this.carriageType = CarriageType.PASSENGER;
        }
    }
}


