package rest;

import com.google.gson.*;
import entities.*;
import dtos.*;
import facades.*;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.*;
import javax.ws.rs.*;


import utils.EMF_Creator;


@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final HouseFacade hf = HouseFacade.getHouseFacade(EMF);
    private static final RentalFacade rf = RentalFacade.getRentalFacade(EMF);
    private static final TenantFacade tf = TenantFacade.getTenantFacade(EMF);



    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    //see all endpoints

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("seeAllHouses")
    public Response seeAllHousesEndpoint(){
        return Response.ok().entity(GSON.toJson(hf.seeAllHouses())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("seeAllTenants")
    public Response seeAllTenantsEndpoint(){
        return Response.ok().entity(GSON.toJson(tf.seeAllTenants())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("seeAllRentals")
    public Response seeAllRentalsEndpoint(){
        return Response.ok().entity(GSON.toJson(rf.seeAllRentals())).build();
    }

    //get by id

    @GET
    @Path("houses/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHousesById(@PathParam("id") int id) {
        return Response.ok()
                .entity(GSON.toJson(hf.getHouseById(id)))
                .build();
    }

    @GET
    @Path("rentals/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentalsById(@PathParam("id") int id) {
        return Response.ok()
                .entity(GSON.toJson(rf.getRentalById(id)))
                .build();
    }

    @GET
    @Path("tenants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTenantsById(@PathParam("id") int id) {
        return Response.ok()
                .entity(GSON.toJson(tf.getTenantById(id)))
                .build();
    }

    //create tests

    @POST
    @Path("houses/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHouse(String jsonContext) {
        House h = GSON.fromJson(jsonContext, House.class);
        return Response.ok()
                .entity(GSON.toJson(hf.createHouse(h)))
                .build();
    }

    @POST
    @Path("rentals/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRental(String jsonContext) {
        Rental r = GSON.fromJson(jsonContext, Rental.class);
        return Response.ok()
                .entity(GSON.toJson(rf.createRental(r)))
                .build();
    }

    @POST
    @Path("tenants/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTenant(String jsonContext) {
        Tenant t = GSON.fromJson(jsonContext, Tenant.class);
        return Response.ok()
                .entity(GSON.toJson(tf.createTenant(t)))
                .build();
    }

    //delete

    @DELETE
    @Path("deleteHouse/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteHouseById(@PathParam("id") int id) {
        hf.deleteHouse(id);
        return "{\"removedId\":" + id + "}";
    }

    @DELETE
    @Path("deleteTenant/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTenantById(@PathParam("id") int id) {
        tf.deleteTenant(id);
        return "{\"removedId\":" + id + "}";
    }

    @DELETE
    @Path("deleteRental/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteRentalById(@PathParam("id") int id) {
        rf.deleteRental(id);
        return "{\"removedId\":" + id + "}";
    }





}