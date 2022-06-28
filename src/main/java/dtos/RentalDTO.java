package dtos;

import entities.House;
import entities.Rental;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


public class RentalDTO {
    private int id;
    private int startDate;
    private int endDate;
    private int priceAnnual;
    private int deposit;
    private String contactPerson;
    private List<TenantDTO> tenants = new ArrayList<>();
    private HouseDTO house;



    public RentalDTO(int startDate, int endDate, int priceAnnual, int deposit, String contactPerson) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
    }

    public static List<RentalDTO> getDtos(List<Rental> rentals) {
        List<RentalDTO> rdtos = new ArrayList<>();
        rentals.forEach(r -> rdtos.add(new RentalDTO(r)));
        return rdtos;
    }

    public RentalDTO(Rental r) {
        if (r != null) {
            this.id = r.getId();
            this.startDate = r.getStartDate();
            this.endDate = r.getEndDate();
            this.priceAnnual = r.getPriceAnnual();
            this.deposit = r.getDeposit();
            this.contactPerson = r.getContactPerson();
            this.tenants = TenantDTO.getDtos(r.getTenants());
        }
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

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }

    public List<TenantDTO> getTenants() {
        return tenants;
    }

    /*public void setTenantDTOS(List<TenantDTO> tenantDTOS) {           trying addTenants insteed
        this.tenantDTOS = tenantDTOS;
    }*/

    public void addTenants(TenantDTO tenants) {
    this.tenants.add(tenants);}


    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDTO rentalDTO = (RentalDTO) o;
        return id == rentalDTO.id && startDate == rentalDTO.startDate && endDate == rentalDTO.endDate && priceAnnual == rentalDTO.priceAnnual && deposit == rentalDTO.deposit && Objects.equals(contactPerson, rentalDTO.contactPerson) && Objects.equals(tenants, rentalDTO.tenants);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, startDate, endDate, priceAnnual, deposit, contactPerson, tenants);
    }

    @Override
    public String toString() {
        return "RentalDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceAnnual=" + priceAnnual +
                ", deposit=" + deposit +
                ", contactPerson='" + contactPerson + '\'' +
                ", tenants=" + tenants +
                '}';
    }
}

