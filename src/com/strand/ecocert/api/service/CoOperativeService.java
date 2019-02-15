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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strand.ecocert.data.entity.CoOperative;
import com.sun.jersey.api.NotFoundException;

@Path("cooperative")
public class CoOperativeService {

	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public CoOperativeService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}
	
	@GET
	@Path("{coOperativeId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public CoOperative getCoOperative(
			@PathParam("coOperativeId") int coOperativeId
			) {
	    CoOperative coOperative = entityManager.find(CoOperative.class, coOperativeId);
	    if(coOperative==null)
	    	throw new NotFoundException();
		return coOperative;
	}
	
	/**
	 * @return - return all the farmer in the database.
	 */
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CoOperative> getCollectionCenters() {
	    Query query = entityManager.createQuery("Select c from CoOperative c");
	    List<CoOperative> coOperatives = query.getResultList();
		return coOperatives;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CoOperative addCoOperative(String jsonString) throws JsonParseException, JsonMappingException, IOException {
	    entityManager.getTransaction( ).begin( );
	    CoOperative coOperative = new ObjectMapper().readValue(jsonString, CoOperative.class);
	    entityManager.persist(coOperative);
	    entityManager.getTransaction( ).commit( );
	    entityManager.close( );
	    
	    ResponseBuilder responseBuilder = Response.ok(coOperative);
	    responseBuilder.header("Access-Control-Allow-Origin", "*")
	    .header("Access-Control-Allow-Methods", "POST")
	    .header("Access-Control-Max-Age", "151200")
	    .header("Access-Control-Allow-Headers", "Content-Type");
		return coOperative;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CoOperative updateCoOperative(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {	
	    entityManager.getTransaction( ).begin( );
	    
	    JSONObject jsonObject = new JSONObject(jsonString);
	    CoOperative coOperative = entityManager.find(CoOperative.class, Integer.parseInt(jsonObject.get("coOperativeId").toString()));
	    if(coOperative==null) 
	    	throw new NotFoundException();

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.readerForUpdating(coOperative).readValue(jsonString);
	    entityManager.persist(coOperative);
	    entityManager.getTransaction( ).commit( );
		return coOperative;
	}
	
	@DELETE
	@Path("{coOperativeId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public CoOperative removeCoOperative(
			@PathParam("coOperativeId") int coOperativeId
			) {
	    entityManager.getTransaction().begin();
	    
	    CoOperative coOperative = entityManager.find(CoOperative.class, coOperativeId);
	    if(coOperative==null)
	    	throw new NotFoundException();
	    entityManager.remove(coOperative);
	    entityManager.getTransaction().commit();
	    entityManager.close();
		return coOperative;
	}
}
