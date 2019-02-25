package com.strand.ecocert.api.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strand.ecocert.data.users.CCPerson;
import com.sun.jersey.api.NotFoundException;

/**
 * 
 * @author vilay
 *
 */

@Path("ccperson")
public class CCPersonService {

	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public CCPersonService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}

	@GET
	@Path("{ccPersonId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public CCPerson getCCPerson(@PathParam("ccPersonId") int ccPersonId) {
		entityManager.find(CCPerson.class, ccPersonId);
		return null;
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CCPerson> getCCPersons() {
		return entityManager.createQuery("Select c from CCPerson c").getResultList();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CCPerson addCCPerson(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		entityManager.getTransaction().begin();
		
		CCPerson ccPerson = new ObjectMapper().readValue(jsonString, CCPerson.class);
		entityManager.persist(ccPerson);
		
		entityManager.getTransaction().commit();
		return ccPerson;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CCPerson updateCCPerson(String jsonString) throws JSONException, JsonProcessingException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		
		entityManager.getTransaction().begin();
		CCPerson ccPerson = entityManager.find(CCPerson.class, Integer.parseInt(jsonObject.get("userId").toString()));
		if(ccPerson==null)
			throw new NotFoundException();
		objectMapper.readerForUpdating(ccPerson).readValue(jsonString);
		entityManager.persist(ccPerson);
		entityManager.getTransaction().commit();
		
		return ccPerson;
	}
	
	
	@DELETE
	@Path("{userId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public CCPerson removeCCPerson(@PathParam("userId") int userId) {
		entityManager.getTransaction().begin();
	    CCPerson ccPerson = entityManager.find(CCPerson.class, userId);
	    if(ccPerson==null)
	    	throw new NotFoundException();
	    entityManager.remove(ccPerson);
	    entityManager.getTransaction().commit();
		return ccPerson;
	}
}
