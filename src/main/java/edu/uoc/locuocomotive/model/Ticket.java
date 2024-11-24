package edu.uoc.locuocomotive.model;

import java.time.LocalTime;

public class Ticket {
    private String routeId;
    private Passenger owner;
    private Seat seat;
    private double price;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Station departureStation;
    private Station arrivalStation;

    public Ticket(String routeId, Passenger owner, Seat seat, double price, LocalTime departureTime, LocalTime arrivalTime, Station departureStation, Station arrivalStation) {
        setRouteId(routeId);
        setSeat(seat);
        setOwner(owner);
        setPrice(price);
        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);
        setDepartureStation(departureStation);
        setArrivalStation(arrivalStation);
    }

    // Getters and Setters
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Passenger getOwner() {
        return owner;
    }

    public void setOwner(Passenger owner) {
        if(owner != null){
            this.owner = owner;
            this.assignTicket();
        }

    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }


    // Methods
    public void assignTicket(){
        seat.assignSeat(this.owner);
        owner.addTickets(this);
    }
}
