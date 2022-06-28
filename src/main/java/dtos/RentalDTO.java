package dtos;

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
    private List<TenantDTO> tenantDTOS = new ArrayList<>();

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
            this.tenantDTOS = TenantDTO.getDtos(r.getTenants());
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

    public List<TenantDTO> getTenantDTOS() {
        return tenantDTOS;
    }

    public void setTenantDTOS(List<TenantDTO> tenantDTOS) {
        this.tenantDTOS = tenantDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDTO rentalDTO = (RentalDTO) o;
        return id == rentalDTO.id && startDate == rentalDTO.startDate && endDate == rentalDTO.endDate && priceAnnual == rentalDTO.priceAnnual && deposit == rentalDTO.deposit && Objects.equals(contactPerson, rentalDTO.contactPerson) && Objects.equals(tenantDTOS, rentalDTO.tenantDTOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, priceAnnual, deposit, contactPerson, tenantDTOS);
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
                ", tenantDTOS=" + tenantDTOS +
                '}';
    }
}
