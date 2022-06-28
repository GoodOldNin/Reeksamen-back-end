/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;
import entities.*;


public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");


        em.getTransaction().begin();

        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);


        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.getTransaction().commit();

    }

    public static void dummyDataPopulate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();


        em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
        em.createNamedQuery("House.deleteAllRows").executeUpdate();
        em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();

        em.getTransaction().commit();
        em.getTransaction().begin();


        //   String address, String city, int numberOfRooms
        House h1 = new House("HjemmeVej10", "TorskeBy", 4);
        House h2 = new House("Ude vej 12", "Lyngby", 2);
        House h3 = new House("Langtfra 16", "Lemvig", 3);
        House h4 = new House("Lige om hjørnet", "Birkerød", 1);


        //  int startDate, int endDate, int priceAnnual, int deposit, String contactPerson
        // startDate and endDate are int, will be using DDMMYYYY format.
        Rental r1 = new Rental(21012001, 31122031, 120000, 30000, "Santa");
        Rental r2 = new Rental(11012011, 10222025, 240000, 60000, "Mette Frederiksen");
        Rental r3 = new Rental(31012021, 11012028, 360000, 90000, "Bossman");


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


        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(h1);
        em.persist(h2);
        em.persist(h3);
        em.persist(h4);

        em.getTransaction().commit();

    }
    
    public static void main(String[] args) {
        //populate();                   //first populate, then dummydata else, users go byebye, no time to fix
        //dummyDataPopulate();
    }
}
