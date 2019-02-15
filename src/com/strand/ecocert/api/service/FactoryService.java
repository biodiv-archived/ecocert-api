package com.strand.ecocert.api.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
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
import com.strand.ecocert.data.entity.CoOperative;
import com.strand.ecocert.data.entity.Factory;
import com.sun.jersey.api.NotFoundException;

@Path("factory")
public class FactoryService {
	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public FactoryService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}
	
	@GET
	@Path("{factoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Factory getCollection(@PathParam("factoryId") int factoryId) {
		Factory factory = entityManager.find(Factory.class, factoryId);
		if(factory==null) 
			throw new NotFoundException();
		return factory;
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Factory> getCollections() {
		Query query = entityManager.createQuery("Select c from Factory c");
	    List<Factory> collections = query.getResultList();
		return collections;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Factory addCollection(
			String jsonString
			) throws JsonParseException, JsonMappingException, IOException, NumberFormatException, JSONException {
		
		entityManager.getTransaction().begin();
				
		JSONObject jsonObject = new JSONObject(jsonString);
			
		int coOperativeId = Integer.parseInt(jsonObject.getString("coOperativeId"));
		CoOperative coOperative = entityManager.createNamedQuery("CoOperative.findByCoOperativeId", CoOperative.class)
				.setParameter("coOperativeId", coOperativeId)
				.getSingleResult();
		
		Factory factory = new ObjectMapper().readValue(jsonObject.toString(), Factory.class);
		factory.setCoOperative(coOperative);
		
		entityManager.persist(factory);
		entityManager.getTransaction().commit();
		return factory;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Factory updateCollection(String jsonString) throws JSONException, JsonParseException, JsonMappingException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		
		entityManager.getTransaction().begin();
		int factoryId = Integer.parseInt(jsonObject.get("factoryId").toString());
		Factory factory = entityManager.find(Factory.class, factoryId);
		if(factory==null)
			throw new NotFoundException();
		objectMapper.readerForUpdating(factory).readValue(jsonObject.toString());
		entityManager.persist(factory);
		entityManager.getTransaction().commit();
		
		return factory;
	}
}
