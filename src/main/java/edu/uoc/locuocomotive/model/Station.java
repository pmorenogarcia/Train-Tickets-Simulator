package edu.uoc.locuocomotive.model;

public class Station {
    private int id;
    private String name;
    private String city;
    private int openingYear;
    private String imageFilename;
    private int xCoord;
    private int yCoord;
    private StationType type;

    public Station(int id, String name, String city, int openingYear, String imageFilename, int xCoord, int yCoord, String type) {
        setId(id);
        setName(name);
        setCity(city);
        setOpeningYear(openingYear);
        setImageFilename(imageFilename);
        setxCoord(xCoord);
        setyCoord(yCoord);
        setType(type);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getOpeningYear() {
        return openingYear;
    }
    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }
    public String getImageFilename() {
        return imageFilename;
    }
    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
    public int getxCoord() {
        return xCoord;
    }
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }
    public int getyCoord() {
        return yCoord;
    }
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    public StationType getType() {
        return type;
    }
    public void setType(String type) {
        try {
            this.type = StationType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("INVALID STATION TYPE");
        }
    }
}

