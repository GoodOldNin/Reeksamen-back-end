package entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@NamedQuery(name = "Tenant.deleteAllRows", query = "DELETE from Tenant t")
@Table(name = "tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "phone")
    private int phone;

    @NotNull
    @Column(name = "job")
    private String job;



    @JoinTable(name = "tenants_rentals", joinColumns = {
            @JoinColumn(name = "rental", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "tenant", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Rental> rentals = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "user_name")
    private User user;

    public Tenant() {
    }

    public Tenant(String name, int phone, String job) {
        this.name = name;
        this.phone = phone;
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addToRental(Rental rental) {
        this.rentals.add(rental);
        rental.addToTenants(this);
    }

    @Override
    public String toString() {      //TODO
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", job='" + job + '\'' +
                '}';
    }
}
