package edu.uoc.locuocomotive.controller;

import edu.uoc.locuocomotive.model.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class LocUOComotiveController {

    //TODO: Define the attributes
    private List<Station> stations;
    private List<Route> routes;
    private List<Train> trains;
    private List<Ticket> tickets;
    private List<Passenger> passengers;
    private Station currentStation;
    public LocUOComotiveController(String stationsFile, String routesFile, String trainsFile) {
        this.stations = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.trains = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.passengers = new ArrayList<>();

        loadTrains(trainsFile);
        loadStations(stationsFile);
        loadRoutes(routesFile);
    }

    private void loadStations(String stationsFile) {
        // Get the file from the resources folder
        InputStream is = getClass().getResourceAsStream("/data/" + stationsFile);

        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + stationsFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                // Create the station and add it to the list of stations
                addStation(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5], Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));
            }
            currentStation = stations.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRoutes(String routesFile) {
        // Get the file from the resources folder
        InputStream is = getClass().getResourceAsStream("/data/" + routesFile);

        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + routesFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by character :
                String[] parts = line.split("=");
                String[] parts2 = parts[0].split("\\|");

                // Create the route and add it to the list of routes
                addRoute(parts2[0], Integer.parseInt(parts2[1]), parts[1].split("\\|"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTrains(String trainsFile) {
        // Get the file from the resources folder
        InputStream is = getClass().getResourceAsStream("/data/" + trainsFile);

        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + trainsFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                int[] seatsPerCar = new int[parts.length - 2];

                for (int i = 2; i < parts.length; i++) {
                    seatsPerCar[i - 2] = Integer.parseInt(parts[i]);
                }

                addTrain(Integer.parseInt(parts[0]), parts[1], seatsPerCar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStation(int id, String name, String city, int openingYear, String type, String image, int positionX, int positionY) {
        Station station = new Station(id, name, city, openingYear, image, positionX, positionY, type);
        stations.add(station);
    }

    public void addRoute(String id, int trainId, String... stationsAndTimes) {
        List<Schedule> schedules = new ArrayList<>();

        for (String stationAndTime: stationsAndTimes) {
            String[] parts = stationAndTime.split("\\[");
            int stationId = Integer.parseInt(parts[0]);

            String[] scheduleTimesString = parts[1].replace("]", "").split(",");


            LocalTime[] scheduleTimes = Arrays.stream(scheduleTimesString).map(LocalTime::parse).toArray(LocalTime[]::new);

            Schedule schedule = new Schedule(stationId, scheduleTimes);
            schedules.add(schedule);
        }

        Route route = new Route(id, trainId, schedules);
        routes.add(route);
    }

    public void addTrain(int id, String model, int... cars) {
        Train train = new Train(id, model, cars);
        trains.add(train);
    }

    public List<String> getStationsInfo() {
        List<String> information = new ArrayList<>();
        for(Station station : stations){
            String stationInfo = station.getId()+"|"+station.getName()+"|"+station.getCity()+"|"+station.getImageFilename()+"|"+station.getxCoord()+"|"+station.getyCoord();
            information.add(stationInfo);
        }
        return information;
    }

    public String[] getSeatTypes() {
        CarriageClass[] classes = CarriageClass.values();
        String[] seatTypes = new String[classes.length];
        for(int i=0; i< classes.length; i++){
            seatTypes[i] = classes[i].name();
        }

        return seatTypes;
    }

    public List<String> getRoutesByStation(int stationId) {
        List<String> routesFinalList = new ArrayList<>();
        String arrivalStationName = "";
        for(Station station : stations){
            if(station.getId() == stationId){
                arrivalStationName = station.getName();
                break;
            }
        }
        for(Route route : routes){
            List<String> routeStrings = new ArrayList<>();
            List<Schedule> schedules = route.getSchedules();
            boolean stationIdFound = false;
            int departureIndex = 0;
            int arrivalIndex = 0;

            // Check if this route has the current station followed by the stationId to search
            for(int i=0; i < schedules.size(); i++){
                Schedule schedule = schedules.get(i);

                if(schedule.getDestinationId() == currentStation.getId()){
                   departureIndex = i;
                   for(int j = i; j < schedules.size(); j++){
                       schedule = schedules.get(j);
                       if(schedule.getDestinationId() == stationId){
                           arrivalIndex = j;
                           stationIdFound = true;
                           break;
                       }
                   }
                }
            }

            // If it is found, iterate and store Strings
            // departureIndex
            // arrivalIndex

            if(stationIdFound){
                List<LocalTime> departureHours = new ArrayList<>();
                List<LocalTime> arrivalHours = new ArrayList<>();

                for(int i = 0; i < schedules.size(); i++){
                    if(i == departureIndex){
                        for(int j = 0; j < schedules.get(i).getTimes().size(); j++){
                            departureHours.add(schedules.get(i).getTimes().get(j));
                        }
                    } else if (i == arrivalIndex) {
                        for(int j = 0; j < schedules.get(i).getTimes().size(); j++){
                            arrivalHours.add(schedules.get(i).getTimes().get(j));
                        }
                    }
                }

                for(int i = 0; i < departureHours.size(); i++){
                    LocalTime departure = departureHours.get(i);
                    LocalTime arrival = arrivalHours.get(i);

                    Duration duration = Duration.between(departure, arrival);

                    // If the arrival is on the next day
                    if(arrival.isBefore(departure) || arrival.equals(departure)){
                        duration = duration.plusHours(24);
                    }

                    long travelHours = duration.toHours();
                    int ticketCost = (int) (30 * travelHours);

                    String routeString = departure + "-" + arrival + "|" + route.getId() + "|" + ticketCost + "|" + currentStation.getId() + "|" + stationId + "|" + currentStation.getName() + "|" + arrivalStationName;
                    routeStrings.add(routeString);
                }
            }

            routesFinalList.addAll(routeStrings);
        }

        routesFinalList.sort((route1, route2) -> {
            // Extract departure times from route strings
            LocalTime departureTime1 = LocalTime.parse(route1.split("\\|")[0].split("-")[0]);
            LocalTime departureTime2 = LocalTime.parse(route2.split("\\|")[0].split("-")[0]);
            // Compare departure times
            return departureTime1.compareTo(departureTime2);
        });

        return routesFinalList;
    }

    public void addPassenger(String passport, String name, String surname, LocalDate birthdate, String email) throws Exception {
        for(Passenger passenger : passengers){
            if(Objects.equals(passenger.getPassport(), passport)){
                passenger.updatePassangerInfo(name, surname, birthdate, email);
                return;
            }
        }
        Passenger passenger = new Passenger(passport, name, surname, birthdate, email, currentStation);
        passengers.add(passenger);
    }

    public void createTicket(String passport, String routeId, LocalTime departureTime, LocalTime arrivalTime, double cost, int originStationId, int destinationStationId, String selectedSeatType) throws Exception {
        Passenger owner = null;
        int trainId = 0;
        for(Passenger passenger : passengers){
            if(Objects.equals(passenger.getPassport(), passport)){
                owner = passenger;
            }
        }
        boolean routeFound = false;
        for(Route route : routes){
            if(Objects.equals(route.getId(), routeId)){
                routeFound = true;
                trainId = route.getTrainId();
                break;
            }
        }
        if(!routeFound){
            throw new Exception("INVALID ROUTE");
        }

        Seat ticketSeat = getFirstSeatAvailable(trainId, selectedSeatType);

        Station originStation = null;
        Station arrivalStation = null;
        for(Station station : stations){
            if(station.getId() == originStationId){
                originStation = station;
            } else if (station.getId() == destinationStationId) {
                arrivalStation = station;
            }
        }

        Ticket ticket = new Ticket(routeId, owner, ticketSeat, cost, departureTime, arrivalTime, originStation, arrivalStation);
        tickets.add(ticket);

        // Simulate travel
        owner.updateLocation(arrivalStation);
        currentStation = arrivalStation;
        ticketSeat.assignSeat(null);

    }

    public void buyTicket(String passport, String name, String surname, LocalDate birthdate, String email,
                            String routeId, LocalTime departureTime, LocalTime arrivalTime, double cost, int originStationId, int destinationStationId, String selectedSeatType) throws Exception {

        addPassenger(passport, name, surname, birthdate, email);
        createTicket(passport, routeId, departureTime, arrivalTime, cost, originStationId, destinationStationId, selectedSeatType);

    }

    public List<String> getAllTickets() {
        List<String> ticketsString = new ArrayList<>();
        for(Ticket ticket : tickets){
            String ticketInfo = ticket.getRouteId()+"|"+ticket.getDepartureTime()+"|"+ticket.getDepartureStation().getName()+"|"+ticket.getArrivalTime()+"|"+ticket.getArrivalStation().getName()+"|"+ticket.getSeat().getId()+"|"+ticket.getPrice();
            ticketsString.add(ticketInfo);
        }
        return ticketsString;
    }

    public String getPassengerInfo(String passport) {
        String passengerInfo = "";
        for(Passenger passenger : passengers){
            if(Objects.equals(passenger.getPassport(), passport)){
                passengerInfo = passenger.getPassport()+"|"+passenger.getFirstName()+"|"+passenger.getLastName()+"|"+passenger.getBirthDate()+"|"+passenger.getEmail();
            }
        }
        return passengerInfo;
    }

    public String getTrainInfo(int trainId) {
        String trainInfo = "";
        for(Train train : trains){
            if(train.getId() == trainId){
                trainInfo = train.getId()+"|"+train.getModel()+"|"+train.getCarriages().size();
            }
        }
        return trainInfo;
    }

    public List<String> getPassengerTickets(String passport) {
        List<String> ticketsString = new ArrayList<>();
        for(Passenger passenger : passengers){
            if(passenger.getPassport() == passport){
                for(Ticket purchasedTicket : passenger.getPurchasedTickets()){
                    String ticketInfo = purchasedTicket.getRouteId()+"|"+purchasedTicket.getDepartureTime()+"|"+purchasedTicket.getDepartureStation().getName()+"|"+purchasedTicket.getArrivalTime()+"|"+purchasedTicket.getArrivalStation().getName()+purchasedTicket.getSeat().getId()+"|"+purchasedTicket.getPrice();
                    ticketsString.add(ticketInfo);
                }
            }
        }
        return ticketsString;
    }

    public List<String> getRouteDeparturesInfo(String routeId) {
        List<String> routeDeparturesInfo = new ArrayList<>();
        for(Route route : routes){
            if(Objects.equals(route.getId(), routeId)){
                for(Schedule schedule : route.getSchedules()){
                    StringBuilder departureInfoSB = new StringBuilder(schedule.getDestinationId() + "|[");

                    for(int i=0; i < schedule.getTimes().size(); i++){
                       LocalTime time = schedule.getTimes().get(i);
                       departureInfoSB.append(time);
                       if(i < schedule.getTimes().size()-1){
                           departureInfoSB.append(", ");
                       }
                    }
                    departureInfoSB.append("]");
                    routeDeparturesInfo.add(departureInfoSB.toString());
                }
            }
        }
        return routeDeparturesInfo;
    }

    public int getCurrentStationId() {
        return currentStation.getId();
    }


    // Additional methods
    public Seat getFirstSeatAvailable(int trainId, String seatType) throws Exception {
        // Iterate trains until the train is found
        for(Train train : trains){
            if(train.getId() == trainId){
                // Iterate carriages until the carriage is found
                for(Carriage carriage : train.getCarriages()){
                    if(carriage.getCarriageClass().toString().equalsIgnoreCase(seatType)){
                        // Iterate seats until the first available is found
                        for(Seat seat : carriage.getSeats()){
                            if(seat.isAvailable()){
                                //seat.setAvailable(false);
                                return seat;
                            }
                        }
                    }
                }
            }
        }
        throw new Exception("NO SEAT HAS BEEN FOUND");
    }

}
