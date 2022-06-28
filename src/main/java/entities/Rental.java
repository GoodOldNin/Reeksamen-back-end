package entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;


@Entity
@NamedQuery(name = "Rental.deleteAllRows", query = "DELETE from Rental r")
@Table(name = "rental")
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "start_date")
    private int startDate;

    @NotNull
    @Column(name = "end_date")
    private int endDate;

    @NotNull
    @Column(name = "price_annual")
    private int priceAnnual;

    @NotNull
    @Column(name = "deposit")
    private int deposit;

    @NotNull
    @Column(name = "contact_person")
    private String contactPerson;

    @ManyToMany(mappedBy = "rentals", cascade = {CascadeType.PERSIST})
    private List<Tenant> tenants = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false)
    private House house;


    public Rental() {
    }

    public Rental(int startDate, int endDate, int priceAnnual, int deposit, String contactPerson) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public int getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(int priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public void addToTenants(Tenant tenant) {
        this.tenants.add(tenant);
    }



    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
        house.addToRental(this);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceAnnual=" + priceAnnual +
                ", deposit=" + deposit +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
}