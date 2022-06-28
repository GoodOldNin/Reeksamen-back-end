package facades;

import dtos.RentalDTO;
import entities.Rental;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class RentalFacade {

    private static EntityManagerFactory emf;
    private static RentalFacade instance;

    public RentalFacade() {
    }

    public static RentalFacade getRentalFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    public List<RentalDTO> seeAllRentals(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r", Rental.class);
            List<Rental> rentals = query.getResultList();
            List<RentalDTO> rdtos = RentalDTO.getDtos(rentals);
            return rdtos;
        }finally {
            em.close();
        }
    }

    public RentalDTO updateRentals(Rental rental){
        EntityManager em = emf.createEntityManager();
        try {
            Rental r = em.find(Rental.class, rental.getId());
            r.setStartDate(rental.getStartDate());
            r.setEndDate(rental.getEndDate());
            r.setPriceAnnual(rental.getPriceAnnual());
            r.setDeposit(rental.getDeposit());
            r.setContactPerson(rental.getContactPerson());
            r.setTenants(rental.getTenants());          //TODO?
            em.persist(r);
            return new RentalDTO(r);
        }finally {
            em.close();
        }
    }

    public RentalDTO deleteRental(int id){
        EntityManager em = emf.createEntityManager();
        try {
            Rental r = em.find(Rental.class, id);
            em.getTransaction().begin();
            em.remove(r);
            em.getTransaction().commit();
            return new RentalDTO(r);
        }finally {
            em.close();
        }
    }

    public RentalDTO createRental(Rental rental){
        EntityManager em = emf.createEntityManager();
        try {
            Rental r = new Rental(rental.getStartDate(), rental.getEndDate(), rental.getPriceAnnual(), rental.getDeposit(), rental.getContactPerson());
            r.setTenants(rental.getTenants());
            r.setHouse(rental.getHouse());
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            return new RentalDTO(r);
        }finally {
            em.close();
        }
    }

    public RentalDTO getRentalById(int id){
        EntityManager em = emf.createEntityManager();
        try {
            Rental r = em.find(Rental.class, id);
            return new RentalDTO((r));
        }finally {
            em.close();
        }
    }
}
