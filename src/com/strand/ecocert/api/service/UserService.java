package com.strand.ecocert.api.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strand.ecocert.data.users.User;
import com.sun.jersey.api.NotFoundException;

@Path("user")
public class UserService {

	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public UserService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}

	/**
	 * Get User with given User code and farm number.
	 * @param UserCode
	 * @param farmNumber
	 * @return JSON of the given User.
	 */
	
	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public User getUser(@PathParam("userId") int UserId) {
				
		User User = entityManager.find(User.class, UserId);
		if(User==null)
			throw new NotFoundException();
		return User;
	}

	/**
	 * @return - return all the User in the database.
	 */
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
	    Query query = entityManager.createQuery("Select f from User f");
	    List<User> Users = query.getResultList();
		return Users;
	}
		
	/**
	 * Add the new User record in the database.
	 * User code and farmNumber has to be unique. its a composite key.
	 * @return - the added User in JSON format
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User addUser(
			String jsonObject
			) throws JsonParseException, JsonMappingException, IOException {
		entityManager.getTransaction().begin();
		
		User User = new ObjectMapper().readValue(jsonObject, User.class);
		entityManager.persist(User);
		
		entityManager.getTransaction().commit();
		return User;
	}

	/**
	 * Edit Users info.
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(JSONObject jsonObject) throws JSONException, JsonParseException, JsonMappingException, IOException {
		//JSONObject jsonObject = new JSONObject(jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		
		entityManager.getTransaction().begin();
		User User = entityManager.find(User.class, Integer.parseInt(jsonObject.get("UserId").toString()));
		if(User==null)
			throw new NotFoundException();
		objectMapper.readerForUpdating(User).readValue(jsonObject.toString());
		entityManager.persist(User);
		entityManager.getTransaction().commit();
		
		return User;
	}
	
	/**
	 * Remove User with given UserCode and farmNmber.
	 * @param UserCode
	 * @param farmNumber
	 * @return JSON of removed User.
	 */
	@DELETE
	@Path("{userId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public User removeUser(
			@PathParam("userId") int UserId
			) {
	    entityManager.getTransaction().begin();
	    User User = entityManager.find(User.class, UserId);
	    if(User==null)
	    	throw new NotFoundException();
	    entityManager.remove(User);
	    entityManager.getTransaction().commit();
		return User;
	}
	
	/**
	 * This method is dangerous to call, 
	 * as it removes all the row from User table. 
	 * @return
	 */
	@DELETE
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> removeUsers(){
	    entityManager.getTransaction().begin();
	    
	    Query query = entityManager.createQuery("Select f from User f");
	    List<User> Users = query.getResultList();
	    for(User User : Users)
	    	entityManager.remove(User);
		
		entityManager.getTransaction().commit();
	    return Users;
	}
}
