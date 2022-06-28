package entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@NamedQuery(name = "House.deleteAllRows", query = "DELETE from House h")
@Table(name = "house")
public class House implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "address")
    private String address;
    @NotNull
    @Column(name = "city")
    private String city;
    @NotNull
    @Column(name = "number_of_rooms")
    private int numberOfRooms;

    @OneToMany(mappedBy = "house",cascade =CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();


    public House() {
    }

    public House(String address, String city, int numberOfRooms) {
        this.address = address;
        this.city = city;
        this.numberOfRooms = numberOfRooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addToRental(Rental rental) {
        this.rentals.add(rental);
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rentals=" + rentals +
                '}';
    }
}
