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

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strand.ecocert.data.entity.CollectionCenter;
import com.sun.jersey.api.NotFoundException;

@Path("center")
public class CollectionCenterService {

	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public CollectionCenterService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}
	
	@GET
	@Path("{ccId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public CollectionCenter getWashingStation(
			@PathParam("ccId") int ccId) {
	    CollectionCenter washingStation = entityManager.find(CollectionCenter.class, ccId);
	    if(washingStation==null)
	    	throw new NotFoundException();
		return washingStation;
	}
	
	/**
	 * @return - return all the farmer in the database.
	 */
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CollectionCenter> getCollectionCenters() {
	    Query query = entityManager.createQuery("Select c from CollectionCenter c");
	    List<CollectionCenter> collectionCenters = query.getResultList();
		return collectionCenters;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CollectionCenter addCoOperative(String jsonString) throws JsonParseException, JsonMappingException, IOException {
	    entityManager.getTransaction( ).begin( );
	    CollectionCenter collectionCenter = new ObjectMapper().readValue(jsonString, CollectionCenter.class);
	    entityManager.persist(collectionCenter);
	    entityManager.getTransaction( ).commit( );
	    entityManager.close( );
		return collectionCenter;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CollectionCenter updateCoOperative(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {	
	    entityManager.getTransaction( ).begin( );
	    
	    JSONObject jsonObject = new JSONObject(jsonString);
	    CollectionCenter collectionCenter = entityManager.find(CollectionCenter.class, Integer.parseInt(jsonObject.get("ccId").toString()));
	    if(collectionCenter==null) 
	    	throw new NotFoundException();

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.readerForUpdating(collectionCenter).readValue(jsonString);
	    entityManager.persist(collectionCenter);
	    entityManager.getTransaction( ).commit( );
		return collectionCenter;
	}
		
	@DELETE
	@Path("{ccId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CollectionCenter removeCoOperative(
			@PathParam("ccId") int ccId
			) {
	    entityManager.getTransaction().begin();	    
	    CollectionCenter collectionCenter = entityManager.find(CollectionCenter.class, ccId);
	    if(collectionCenter==null)
	    	throw new NotFoundException();
	    entityManager.remove(collectionCenter);
	    entityManager.getTransaction().commit();
	    entityManager.close();
		return collectionCenter;
	}
	
}
