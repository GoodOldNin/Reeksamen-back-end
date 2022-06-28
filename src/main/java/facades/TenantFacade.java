package facades;

import dtos.HouseDTO;
import dtos.TenantDTO;
import entities.House;
import entities.Tenant;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class TenantFacade {


    private static EntityManagerFactory emf;
    private static TenantFacade instance;

    public TenantFacade() {
    }

    public static TenantFacade getTenantFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TenantFacade();
        }
        return instance;
    }

    public List<TenantDTO> seeAllTenants(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Tenant> query = em.createQuery("SELECT t FROM Tenant t", Tenant.class);
            List<Tenant> tenants = query.getResultList();
            List<TenantDTO> tdtos = TenantDTO.getDtos(tenants);
            return tdtos;
        }finally {
            em.close();
        }
    }

    public TenantDTO updateTenants(Tenant tenant){
        EntityManager em = emf.createEntityManager();
        try {
            Tenant t = em.find(Tenant.class, tenant.getId());
            t.setName(tenant.getName());
            t.setPhone(tenant.getPhone());
            t.setJob(tenant.getJob());
            t.setRentals(tenant.getRentals());
            em.persist(t);
            return new TenantDTO(t);
        }finally {
            em.close();
        }

    }

    public TenantDTO deleteTenant(int id){
        EntityManager em = emf.createEntityManager();
        try {
            Tenant t = em.find(Tenant.class, id);
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
            return new TenantDTO(t);
        }finally {
            em.close();
        }
    }

    public TenantDTO createTenant(Tenant tenant){
        EntityManager em = emf.createEntityManager();
        try {
            Tenant t = new Tenant(tenant.getName(), tenant.getPhone(), tenant.getJob());
            t.setRentals(t.getRentals());
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return new TenantDTO(t);
        }finally {
            em.close();
        }
    }

    public TenantDTO getTenantById(int id){
        EntityManager em = emf.createEntityManager();
        try {
            Tenant t = em.find(Tenant.class, id);
            return new TenantDTO((t));
        }finally {
            em.close();
        }
    }


    public TenantDTO showRentalsByTenant(String userName){
        EntityManager em = emf.createEntityManager();
        try {
            Tenant t = em.find(Tenant.class, userName); //TODO dunno about that one, lets test later
            return new TenantDTO((t));
        }finally {
            em.close();
        }

    }



}
