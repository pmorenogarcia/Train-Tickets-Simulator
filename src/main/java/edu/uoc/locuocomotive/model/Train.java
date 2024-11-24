package edu.uoc.locuocomotive.model;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private int id;
    private String model;
    private List<Carriage> carriages;

    public Train(int id, String model, int... cars) {
        setId(id);
        setModel(model);
        setCarriages(cars);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public List<Carriage> getCarriages() {
        return carriages;
    }
    public void setCarriages(int... cars) {
        this.carriages = new ArrayList<>();
        for(int i=0; i<cars.length; i++){
            String carriageId = "C" + (i+1);
            Carriage carriage = new Carriage(carriageId, cars[i]);
            this.carriages.add(carriage);
        }
    }
}
