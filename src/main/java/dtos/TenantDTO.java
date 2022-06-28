package dtos;

import entities.Tenant;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


public class TenantDTO {

    private int id;
    private String name;
    private int phone;
    private String job;


    public TenantDTO(String name, int phone, String job) {      //todo look into username ? onetoone
        this.name = name;
        this.phone = phone;
        this.job = job;

    }

    public static List<TenantDTO> getDtos(List<Tenant> tenants){
        List<TenantDTO> tdtos = new ArrayList<>();
        tenants.forEach(t->tdtos.add(new TenantDTO(t)));
        return tdtos;
    }

    public TenantDTO(Tenant t) {
        if (t != null) {
            this.id = t.getId();
            this.name = t.getName();
            this.phone = t.getPhone();
            this.job = t.getJob();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantDTO tenantDTO = (TenantDTO) o;
        return id == tenantDTO.id && phone == tenantDTO.phone && Objects.equals(name, tenantDTO.name) && Objects.equals(job, tenantDTO.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, job);
    }

    @Override
    public String toString() {
        return "TenantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", job='" + job + '\'' +
                '}';
    }
}
