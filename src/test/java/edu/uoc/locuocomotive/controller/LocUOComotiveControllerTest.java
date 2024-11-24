package edu.uoc.locuocomotive.controller;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocUOComotiveControllerTest {

    private LocUOComotiveController controller;

    @BeforeEach
    public void setUp() {
        controller = new LocUOComotiveController("stations.txt", "routes.txt", "trains.txt");
    }

    @Test
    @Order(1)
    @Tag("basic")
    @DisplayName("Basic - addPassenger")
    public void testAddPassenger() {
        try {
            // Add new passenger
            controller.addPassenger("12345678A", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "");

            String result = controller.getPassengerInfo("12345678A");

            String[] parts = result.split("\\|");
            assertEquals(4, parts.length);
            assertEquals("12345678A", parts[0]);
            assertEquals("UOC", parts[1]);
            assertEquals("Traveller", parts[2]);
            assertEquals("1994-01-01", parts[3]);

            // Update an existing passenger
            controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "hello@uoc.edu");

            result = controller.getPassengerInfo("12345678A");

            parts = result.split("\\|");
            assertEquals(5, parts.length);
            assertEquals("12345678A", parts[0]);
            assertEquals("Name", parts[1]);
            assertEquals("Surname", parts[2]);
            assertEquals("1990-12-31", parts[3]);
            assertEquals("hello@uoc.edu", parts[4]);

            // Bad passport
            assertThrows(Exception.class, () -> controller.addPassenger("", "Name", "Surname", LocalDate.of(1990, 12, 31), ""));
            assertThrows(Exception.class, () -> controller.addPassenger(null, "Name", "Surname", LocalDate.of(1990, 12, 31), ""));

            // Bad name
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "", "Surname", LocalDate.of(1990, 12, 31), ""));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", null, "Surname", LocalDate.of(1990, 12, 31), ""));

            // Bad surname
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "", LocalDate.of(1990, 12, 31), ""));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", null, LocalDate.of(1990, 12, 31), ""));

            // Bad birthdate
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", null, ""));

            // Bad email
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a"));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a@"));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a@b"));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a@b."));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a@b.c"));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a@b.c."));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a?@b.com"));
            assertThrows(Exception.class, () -> controller.addPassenger("12345678A", "Name", "Surname", LocalDate.of(1990, 12, 31), "a  b@b.com"));

        } catch (Exception e) {
            fail("Exception thrown when adding a passenger: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @Tag("basic")
    @DisplayName("Basic - addStation")
    public void testAddStation() {
        try {
            List<String> result = controller.getStationsInfo();

            assertEquals(8, result.size());

            String[] parts = result.get(0).split("\\|");
            assertEquals(6, parts.length);
            assertEquals("1", parts[0]);
            assertEquals("Barcelona Sants", parts[1]);
            assertEquals("Barcelona", parts[2]);
            assertEquals("barcelona.jpg", parts[3]);
            assertEquals(200, Integer.parseInt(parts[4]));
            assertEquals(402, Integer.parseInt(parts[5]));

            parts = result.get(1).split("\\|");
            assertEquals(6, parts.length);
            assertEquals("2", parts[0]);
            assertEquals("Madrid Atocha", parts[1]);
            assertEquals("Madrid", parts[2]);
            assertEquals("madrid.jpg", parts[3]);
            assertEquals(147, Integer.parseInt(parts[4]));
            assertEquals(410, Integer.parseInt(parts[5]));

            // Add new station
            controller.addStation(100, "Barcelona Sants2", "Barcelona2", 1975, "UNDERGROUND", "barcelona2.jpg", 300, 400);

            result = controller.getStationsInfo();

            assertEquals(9, result.size());

            parts = result.get(8).split("\\|");
            assertEquals(6, parts.length);
            assertEquals("100", parts[0]);
            assertEquals("Barcelona Sants2", parts[1]);
            assertEquals("Barcelona2", parts[2]);
            assertEquals("barcelona2.jpg", parts[3]);
            assertEquals(300, Integer.parseInt(parts[4]));
            assertEquals(400, Integer.parseInt(parts[5]));
        } catch (Exception e) {
            fail("Exception thrown when adding a station: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @Tag("basic")
    @DisplayName("Basic - addTrain")
    public void testAddTrain() {
        try {
            String result = controller.getTrainInfo(1);
            String[] parts = result.split("\\|");

            assertEquals(3, parts.length);
            assertEquals("1", parts[0]);
            assertEquals("S-103", parts[1]);
            assertEquals("7", parts[2]);

            result = controller.getTrainInfo(6);
            parts = result.split("\\|");

            assertEquals(3, parts.length);
            assertEquals("6", parts[0]);
            assertEquals("AVLO", parts[1]);
            assertEquals("14", parts[2]);

            // Add new train
            controller.addTrain(100, "Renfe", 10, 50, 50, 0, 30, 50, 50, 50, 50);

            result = controller.getTrainInfo(100);

            parts = result.split("\\|");
            assertEquals(3, parts.length);
            assertEquals("100", parts[0]);
            assertEquals("Renfe", parts[1]);
            assertEquals("9", parts[2]);
        } catch (Exception e) {
            fail("Exception thrown when adding a train: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    @Tag("basic")
    @DisplayName("Basic - getSeatTypes")
    public void testGetSeatTypes() {
        try {
            String[] result = controller.getSeatTypes();

            assertEquals(3, result.length);
            assertEquals("FIRST_CLASS", result[0]);
            assertEquals("SECOND_CLASS", result[1]);
            assertEquals("THIRD_CLASS", result[2]);
        } catch (Exception e) {
            fail("Exception thrown when getting seat types: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @Tag("advanced")
    @DisplayName("Advanced - addRoute")
    public void testAddRoute() {
        try {
            List<String> result = controller.getRouteDeparturesInfo("EU31");

            assertEquals(3, result.size());

            assertEquals("7|[08:00, 10:30]", result.get(0));
            assertEquals("6|[10:30, 14:30]", result.get(1));
            assertEquals("3|[14:30, 18:30]", result.get(2));

            // Add new route
            controller.addRoute("EU99", 1, "2[08:00,10:30]", "1[10:30,13:00]");

            result = controller.getRouteDeparturesInfo("EU99");

            assertEquals(2, result.size());

            assertEquals("2|[08:00, 10:30]", result.get(0));
            assertEquals("1|[10:30, 13:00]", result.get(1));
        } catch (Exception e) {
            fail("Exception thrown when adding a route: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    @Tag("advanced")
    @DisplayName("Advanced - createTicket")
    public void testCreateTicket() {
        try {
            controller.addPassenger("12345678A", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "");
            controller.createTicket("12345678A", "EU11", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "FIRST_CLASS");

            List<String> result = controller.getAllTickets();

            assertEquals(1, result.size());

            String[] parts = result.get(0).split("\\|");
            assertEquals(7, parts.length);
            assertEquals("EU11", parts[0]);
            assertEquals("08:00", parts[1]);
            assertEquals("Madrid Atocha", parts[2]);
            assertEquals("10:30", parts[3]);
            assertEquals("Barcelona Sants", parts[4]);
            assertEquals("C1-1", parts[5]);
            assertEquals("60.0", parts[6]);

            controller.addPassenger("00000000T", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "");
            controller.createTicket("00000000T", "EU11", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "FIRST_CLASS");

            result = controller.getAllTickets();

            assertEquals(2, result.size());

            parts = result.get(1).split("\\|");
            assertEquals(7, parts.length);
            assertEquals("C1-1", parts[5]);

            controller.createTicket("00000000T", "EU11", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "THIRD_CLASS");

            result = controller.getAllTickets();

            assertEquals(3, result.size());

            parts = result.get(2).split("\\|");
            assertEquals(7, parts.length);
            assertEquals("C6-1", parts[5]);

            controller.createTicket("00000000T", "EU11", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "SECOND_CLASS");

            result = controller.getAllTickets();

            assertEquals(4, result.size());

            parts = result.get(3).split("\\|");
            assertEquals(7, parts.length);
            assertEquals("C10-1", parts[5]);

            // Bad route
            assertThrows(Exception.class, () -> controller.createTicket("12345678A", "EU99", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "FIRST_CLASS"));

            // Create a train with only third class
            controller.addTrain(100, "Useless", 50, 50);
            controller.addRoute("EU99", 100, "2[08:00,10:30]", "1[10:30,13:00]");

            // No empty seat
            assertThrows(Exception.class, () -> controller.createTicket("12345678A", "EU99", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "FIRST_CLASS"));
            assertThrows(Exception.class, () -> controller.createTicket("12345678A", "EU99", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 2, 1, "SECOND_CLASS"));


        } catch (Exception e) {
            fail("Exception thrown when creating a ticket: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    @Tag("advanced")
    @DisplayName("Advanced - buyTicket")
    void testBuyTicket() {
        try {
            assertEquals(1, controller.getCurrentStationId());

            controller.buyTicket("12345678A", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "", "EU12", LocalTime.of(19, 30), LocalTime.of(2, 30), 60, 1, 2, "FIRST_CLASS");

            List<String> result = controller.getAllTickets();

            assertEquals(1, result.size());

            String[] parts = result.get(0).split("\\|");
            assertEquals(7, parts.length);
            assertEquals("EU12", parts[0]);
            assertEquals("19:30", parts[1]);
            assertEquals("Barcelona Sants", parts[2]);
            assertEquals("02:30", parts[3]);
            assertEquals("Madrid Atocha", parts[4]);
            assertEquals("C1-1", parts[5]);
            assertEquals("60.0", parts[6]);

            List<String> tickets = controller.getPassengerTickets("12345678A");

            assertEquals(1, tickets.size());
            parts = result.get(0).split("\\|");
            assertEquals(7, parts.length);
            assertEquals("EU12", parts[0]);
            assertEquals("19:30", parts[1]);
            assertEquals("Barcelona Sants", parts[2]);
            assertEquals("02:30", parts[3]);
            assertEquals("Madrid Atocha", parts[4]);
            assertEquals("C1-1", parts[5]);
            assertEquals("60.0", parts[6]);

            assertEquals(2, controller.getCurrentStationId());
        } catch (Exception e) {
            fail("Exception thrown when buying a ticket: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    @Tag("special")
    @DisplayName("Special - getRoutesByStation")
    public void testGetRoutesByStation() {
        try {
            List<String> result = controller.getRoutesByStation(2);

            assertEquals(4, result.size());
            assertEquals("02:30-09:30|EU12|210|1|2|Barcelona Sants|Madrid Atocha", result.get(0));
            assertEquals("02:30-14:30|EU21|360|1|2|Barcelona Sants|Madrid Atocha", result.get(1));
            assertEquals("14:30-02:30|EU21|360|1|2|Barcelona Sants|Madrid Atocha", result.get(2));
            assertEquals("19:30-02:30|EU12|210|1|2|Barcelona Sants|Madrid Atocha", result.get(3));

            result = controller.getRoutesByStation(3);

            assertEquals(2, result.size());
            assertEquals("10:30-13:00|EU11|60|1|3|Barcelona Sants|Gare du Nord", result.get(0));
            assertEquals("13:00-18:00|EU11|150|1|3|Barcelona Sants|Gare du Nord", result.get(1));

            assertEquals(1, controller.getCurrentStationId());

            controller.buyTicket("12345678A", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "", "EU11", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 1, 3, "FIRST_CLASS");

            assertEquals(3, controller.getCurrentStationId());

            result = controller.getRoutesByStation(1);

            assertEquals(2, result.size());
            assertEquals("17:30-19:30|EU12|60|3|1|Gare du Nord|Barcelona Sants", result.get(0));
            assertEquals("19:30-02:30|EU12|210|3|1|Gare du Nord|Barcelona Sants", result.get(1));

            result = controller.getRoutesByStation(4);

            assertEquals(2, result.size());
            assertEquals("13:00-20:30|EU11|210|3|4|Gare du Nord|Berlin Hauptbahnhof", result.get(0));
            assertEquals("18:00-23:00|EU11|150|3|4|Gare du Nord|Berlin Hauptbahnhof", result.get(1));

            controller.buyTicket("12345678A", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "", "EU11", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 3, 4, "FIRST_CLASS");

            assertEquals(4, controller.getCurrentStationId());

            result = controller.getRoutesByStation(7);

            assertEquals(0, result.size());

            result = controller.getRoutesByStation(8);

            assertEquals(6, result.size());

            assertEquals("02:30-14:30|EU41|360|4|8|Berlin Hauptbahnhof|Warszawa Centralna", result.get(0));
            assertEquals("10:30-05:30|EU42|570|4|8|Berlin Hauptbahnhof|Warszawa Centralna", result.get(1));
            assertEquals("14:30-02:30|EU41|360|4|8|Berlin Hauptbahnhof|Warszawa Centralna", result.get(2));
            assertEquals("17:30-17:30|EU42|720|4|8|Berlin Hauptbahnhof|Warszawa Centralna", result.get(3));
            assertEquals("20:30-23:00|EU11|60|4|8|Berlin Hauptbahnhof|Warszawa Centralna", result.get(4));
            assertEquals("23:00-01:30|EU11|60|4|8|Berlin Hauptbahnhof|Warszawa Centralna", result.get(5));

            controller.buyTicket("12345678A", "UOC", "Traveller", LocalDate.of(1994, 1, 1), "", "EU42", LocalTime.of(8, 0), LocalTime.of(10, 30), 60, 4, 8, "FIRST_CLASS");

            assertEquals(8, controller.getCurrentStationId());
        } catch (Exception e) {
            fail("Exception thrown when getting routes by station: " + e.getMessage());
        }
    }

}
