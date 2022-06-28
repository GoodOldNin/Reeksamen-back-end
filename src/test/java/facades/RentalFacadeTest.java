package facades;

import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RentalFacadeTest {
    private static EntityManagerFactory emf;
    private static RentalFacade facade;

    public RentalFacadeTest() {
    }

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalFacade.getRentalFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();

            em.getTransaction().commit();
            em.getTransaction().begin();


            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);

            //   String address, String city, int numberOfRooms
            House h1 = new House("HjemmeVej10", "TorskeBy", 4);
            House h2 = new House("Ude vej 12", "Lyngby", 4);
            House h3 = new House("Langtfra 16", "Lemvig", 4);


            //  int startDate, int endDate, int priceAnnual, int deposit, String contactPerson
            // startDate and endDate are int, will be using DDMMYYYY format.
            Rental r1 = new Rental(01012001, 31122031, 120000, 30000, "Santa");
            Rental r2 = new Rental(01012011, 10222025, 240000, 60000, "Mette Frederiksen");
            Rental r3 = new Rental(01012021, 11012028, 360000, 90000, "Bossman");


            //    public Tenant(String name, int phone, String job) {

            Tenant t1 = new Tenant("Lars", 11111111, "Flaskesamler");
            Tenant t2 = new Tenant("Mona", 22222222, "CEO");
            Tenant t3 = new Tenant("Ole", 33333333, "studerende");



            User u1 = em.find(User.class, "user");
            User u2 = em.find(User.class, "user_admin");
            User u3 = em.find(User.class, "admin");

            t1.setUser(u3);
            t2.setUser(u2);
            t3.setUser(u1);


            r1.setHouse(h1);
            r2.setHouse(h2);
            r3.setHouse(h3);

            t1.addToRental(r1);
            t2.addToRental(r1);
            t3.addToRental(r1);
            t1.addToRental(r3);
            t3.addToRental(r3);
            t2.addToRental(r2);



            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
}
