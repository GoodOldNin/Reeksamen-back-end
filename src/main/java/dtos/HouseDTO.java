package dtos;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import entities.House;


public class HouseDTO {
    private int id;
    private String address;
    private String city;
    private int numberOfRooms;

    public HouseDTO(String address, String city, int numberOfRooms) {
        this.address = address;
        this.city = city;
        this.numberOfRooms = numberOfRooms;
    }

    public static List<HouseDTO> getDtos(List<House> house){
        List<HouseDTO> hdtos = new ArrayList<>();
        house.forEach(h->hdtos.add(new HouseDTO(h)));
        return hdtos;
    }

    public HouseDTO(House h){
        if(h != null){
            this.id = h.getId();
            this.address = h.getAddress();
            this.city = h.getCity();
            this.numberOfRooms = h.getNumberOfRooms();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseDTO houseDTO = (HouseDTO) o;
        return id == houseDTO.id && numberOfRooms == houseDTO.numberOfRooms && Objects.equals(address, houseDTO.address) && Objects.equals(city, houseDTO.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, city, numberOfRooms);
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                '}';
    }
}
