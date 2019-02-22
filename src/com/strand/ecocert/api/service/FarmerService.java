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
import com.strand.ecocert.data.users.Farmer;
import com.strand.ecocert.filter.JWTTokenNeeded;
import com.sun.jersey.api.NotFoundException;

/**
 * 
 * @author vilay
 *
 * This class provide the service to farmer table.
 * Root path for api is http://<host>:8080/ecocert/api/farmer/
 * Different annotation provides different service.
 * 1. POST   - Add the farmer record.
 * 2. DELETE - Delete the farmer record
 * 3. PUT    - Edit the farmer record.
 * 4. GET    - Get the farmer record.
 * 
 * apart from the root path, we have '/all' as well 
 * to get complete record or to delete complete record.
 */
@Path("farmer")
public class FarmerService {

	
	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public FarmerService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}

	/**
	 * Get farmer with given farmer code and farmer number.
	 * @param farmerCode
	 * @param farmNumber
	 * @return JSON of the given farmer.
	 */
	
	@GET
	@Path("{farmerId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JWTTokenNeeded
	public Farmer getFarmer(@PathParam("farmerId") int farmerId) {
		
		
		Farmer farmer = entityManager.find(Farmer.class, farmerId);
		if(farmer==null)
			throw new NotFoundException();
		return farmer;
	}

	/**
	 * @return - return all the farmer in the database.
	 */
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Farmer> getFarmers() {
	    Query query = entityManager.createQuery("Select f from Farmer f");
	    List<Farmer> farmers = query.getResultList();
		return farmers;
	}
		
	/**
	 * Add the new farmer record in the database.
	 * Farmer code and farmNumber has to be unique. its a composite key.
	 * @return - the added farmer in JSON format
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Farmer addFarmer(
			String jsonObject
			) throws JsonParseException, JsonMappingException, IOException {
		entityManager.getTransaction().begin();
		
		Farmer farmer = new ObjectMapper().readValue(jsonObject, Farmer.class);
		entityManager.persist(farmer);
		
		entityManager.getTransaction().commit();
		return farmer;
	}

	/**
	 * Edit farmers info.
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Farmer updateFarmer(String jsonString) throws JSONException, JsonParseException, JsonMappingException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		
		entityManager.getTransaction().begin();
		Farmer farmer = entityManager.find(Farmer.class, Integer.parseInt(jsonObject.get("farmerId").toString()));
		if(farmer==null)
			throw new NotFoundException();
		objectMapper.readerForUpdating(farmer).readValue(jsonString);
		entityManager.persist(farmer);
		entityManager.getTransaction().commit();
		
		return farmer;
	}
	
	/**
	 * Remove farmer with given farmerCode and farmNmber.
	 * @param farmerCode
	 * @param farmNumber
	 * @return JSON of removed farmer.
	 */
	@DELETE
	@Path("{farmerId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Farmer removeFarmer(
			@PathParam("farmerId") int farmerId
			) {
	    entityManager.getTransaction().begin();
	    Farmer farmer = entityManager.find(Farmer.class, farmerId);
	    if(farmer==null)
	    	throw new NotFoundException();
	    entityManager.remove(farmer);
	    entityManager.getTransaction().commit();
		return farmer;
	}
	
	/**
	 * This method is dangerous to call, 
	 * as it removes all the row from farmer table. 
	 * @return
	 */
	@DELETE
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Farmer> removeFarmers(){
	    entityManager.getTransaction().begin();
	    
	    Query query = entityManager.createQuery("Select f from Farmer f");
	    List<Farmer> farmers = query.getResultList();
	    for(Farmer farmer : farmers)
	    	entityManager.remove(farmer);
		
		entityManager.getTransaction().commit();
	    return farmers;
	}
}
