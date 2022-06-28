package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.*;
import facades.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class EndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
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

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
                .contentType("application/json")
                .when()
                .get("/info/").then()
                .statusCode(200)
                .body("msg", equalTo("Hello anonymous"));
    }

    @Test
    public void testRestForAdmin() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: admin"));
    }

    @Test
    public void testRestForUser() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user"));
    }

    @Test
    public void testAutorizedUserCannotAccesAdminPage() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then() //Call Admin endpoint as user
                .statusCode(401);
    }

    @Test
    public void testAutorizedAdminCannotAccesUserPage() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then() //Call User endpoint as Admin
                .statusCode(401);
    }

    @Test
    public void testRestForMultiRole1() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: user_admin"));
    }

    @Test
    public void testRestForMultiRole2() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user_admin"));
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }


    //basic see all tests
    @Test
    void seeAllHouses() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/seeAllHouses").then()
                .statusCode(200);
    }

    @Test
    void seeAllTenants() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/seeAllTenants").then()
                .statusCode(200);
    }

    @Test
    void seeAllRentals() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/seeAllRentals").then()
                .statusCode(200);
    }

    //basic get by id tests
    @Test
    void getHouseById() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/houses/1").then()
                .statusCode(200);
    }

    @Test
    void getRentalById() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/rentals/1").then()
                .statusCode(200);
    }

    @Test
    void getTenantById() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/tenants/1").then()
                .statusCode(200);
    }

    //create tests - this is where it gets a little tricky

    @Test
    void createHouse() {
        login("user_admin", "test");
        //String address, String city, int numberOfRooms
        HouseDTO h1 = new HouseDTO("Mosevej12","Randers",12);
        String requestBody = GSON.toJson(h1);
        System.out.println(requestBody);
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("info/houses/add")
                .then()
                .assertThat()
                .body("address", equalTo("Mosevej12"))
                .body("numberOfRooms", equalTo(12));
    }

    @Test
    void createRental() {
        login("user_admin", "test");
        //int startDate, int endDate, int priceAnnual, int deposit, String contactPerson
        RentalDTO r = new RentalDTO(01013000,01014000, 360, 12, "OleBole");
        //String name, int phone, String job
        TenantDTO t1 = new TenantDTO(new Tenant("Smølf",12345,"atVæreBlå"));
        TenantDTO t2 = new TenantDTO(new Tenant("djævel",666,"atVæreRød"));
        HouseDTO h = new HouseDTO("Mosevej12","Randers",12);
        r.addTenants(t1);
        r.addTenants(t2);
        r.setHouse(h);




        String requestBody = GSON.toJson(r);
        System.out.println(requestBody);
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("info/rentals/add")
                .then()
                .assertThat()
                .body("startDate", equalTo(01013000))
                .body("priceAnnual", equalTo(360));
    }

    @Test
    void createTenant() {
        login("user_admin", "test");
        //String name, int phone, String job
        TenantDTO t1 = new TenantDTO("Jonas", 77777, "maler");

        String requestBody = GSON.toJson(t1);
        System.out.println(requestBody);
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("info/tenants/add")
                .then()
                .assertThat()
                .body("name", equalTo("Jonas"))
                .body("phone", equalTo(77777));
    }

    //deletes

    @Test
    void deleteHouse() {
        given()
                .contentType("application/json")
                .when()
                .delete("info/deleteHouse/3")
                .then()
                .statusCode(200)
                .body("removedId", equalTo(3));
    }

    @Test
    void deleteTenant() {
        given()
                .contentType("application/json")
                .when()
                .delete("info/deleteTenant/3")
                .then()
                .statusCode(200)
                .body("removedId", equalTo(3));
    }

    @Test
    void deleteRental() {
        given()
                .contentType("application/json")
                .when()
                .delete("info/deleteRental/3")
                .then()
                .statusCode(200)
                .body("removedId", equalTo(3));
    }









}
