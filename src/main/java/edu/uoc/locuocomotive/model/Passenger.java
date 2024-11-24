package edu.uoc.locuocomotive.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Passenger {
    private String passport;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private List<Ticket> purchasedTickets = new ArrayList<>();
    private Station currentStation;
    public Passenger(String passport, String firstName, String lastName, LocalDate birthDate, String email, Station currentStation) throws Exception {
        setPassport(passport);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setEmail(email);
        setCurrentStation(currentStation);
    }

    // Getters and Setters
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) throws Exception {
        if(passport.isEmpty() || passport == null){
            throw new Exception("PASSPORT CANNOT BE NULL OR EMPTY");
        }
        this.passport = passport;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws Exception {
        if(firstName.isEmpty() || firstName == null){
            throw new Exception("NAME CANNOT BE NULL OR EMPTY");
        }

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws Exception {
        if(lastName.isEmpty() || lastName == null){
            throw new Exception("SURNAME CANNOT BE NULL OR EMPTY");
        }

        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) throws Exception {
        if(birthDate == null){
            throw new Exception("SURNAME CANNOT BE NULL OR EMPTY");
        }

        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception{
        if(isValidEmail(email)){
            this.email = email;
        } else {
            throw new Exception("INVALID EMAIL FORMAT");
        }
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void addTickets(Ticket purchasedTickets) {
        this.purchasedTickets.add(purchasedTickets);
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    // Methods
    public void updatePassangerInfo(String firstName, String lastName, LocalDate birthDate, String email) throws Exception {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setEmail(email);
    }

    private boolean isValidEmail(String email){
        if(email.isEmpty()){
            return true;
        }
        // Divide email
        String[] parts = email.split("@");
        if(parts.length !=2){
            return false;
        }

        String usernameRegex = "^[A-Za-z0-9._%+-]+$";
        String domainRegex = "^[a-z0-9.-]+$";
        String extensionRegex = "^[a-z]{2,3}$";

        // Check username
        if(!parts[0].matches(usernameRegex)){
            return false;
        }

        // Divide domain
        String[] domainParts = parts[1].split("\\.");
        if(domainParts.length !=2){
            return false;
        }

        // Check domain
        if(!domainParts[0].matches(domainRegex)){
            return false;
        }
        // Check extension
        if(!domainParts[1].matches(extensionRegex)){
            return false;
        }

        return true;
    }

    public void updateLocation(Station newStation){
        this.currentStation = newStation;
    }
}
