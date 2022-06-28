package facades;

import dtos.HouseDTO;
import entities.House;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class HouseFacade {

    private static EntityManagerFactory emf;
    private static HouseFacade instance;

    public HouseFacade() {
    }

    public static HouseFacade getHouseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    public List<HouseDTO> seeAllHouses(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<House> query = em.createQuery("SELECT h FROM House h", House.class);
            List<House> houses = query.getResultList();
            System.out.println("houses:"+houses);
            List<HouseDTO> hdtos = HouseDTO.getDtos(houses);
            System.out.println("hdtos:"+hdtos);

            return hdtos;
        }finally {
            em.close();
        }
    }

    public HouseDTO updateHouses(House house){
        EntityManager em = emf.createEntityManager();
        try {
            House h = em.find(House.class, house.getId());
            h.setAddress(house.getAddress());
            h.setCity(house.getCity());
            h.setNumberOfRooms(house.getNumberOfRooms());
            h.setRentals(house.getRentals());
            em.persist(h);
            return new HouseDTO(h);
        }finally {
            em.close();
        }
    }

    public HouseDTO deleteHouse(int id){
        EntityManager em = emf.createEntityManager();
        try {
            House h = em.find(House.class, id);
            em.getTransaction().begin();
            em.remove(h);
            em.getTransaction().commit();
            return new HouseDTO(h);
        }finally {
            em.close();
        }
    }

    public HouseDTO createHouse(House house){
        EntityManager em = emf.createEntityManager();
        try {
            House h = new House(house.getAddress(), house.getCity(), house.getNumberOfRooms());
            h.setRentals(h.getRentals());
            em.getTransaction().begin();
            em.persist(h);
            em.getTransaction().commit();
            return new HouseDTO(h);
        }finally {
            em.close();
        }
    }

    public HouseDTO getHouseById(int id){
        EntityManager em = emf.createEntityManager();
        try {
            House h = em.find(House.class, id);
            return new HouseDTO((h));
        }finally {
            em.close();
        }
    }
}
