package com.strand.ecocert.api.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import com.strand.ecocert.data.entity.CollectionCenter;
import com.strand.ecocert.data.process.Collection;
import com.strand.ecocert.data.users.Farmer;
import com.sun.jersey.api.NotFoundException;

@Path("collect")
public class CollectionService {
	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public CollectionService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}
	
	@GET
	@Path("{collectionId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Collection getCollection(@PathParam("collectionId") int collectionId) {
		Collection collection = entityManager.find(Collection.class, collectionId);
		if(collection==null) 
			throw new NotFoundException();
		return collection;
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Collection> getCollections() {
		Query query = entityManager.createQuery("Select c from Collection c");
	    List<Collection> collections = query.getResultList();
		return collections;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection addCollection(
			String jsonString
			) throws JsonParseException, JsonMappingException, IOException, NumberFormatException, JSONException {
		
		entityManager.getTransaction().begin();
				
		JSONObject jsonObject = new JSONObject(jsonString);
		
		String membershipId = jsonObject.getString("membershipId");
		Farmer farmer = entityManager.createNamedQuery("Farmer.findByMembershipId", Farmer.class)
				.setParameter("membershipId", membershipId)
				.getSingleResult();
		
		int ccCode = Integer.parseInt(jsonObject.getString("ccCode"));
		CollectionCenter collectionCenter = entityManager.createNamedQuery("CollectionCenter.findByCcCode", CollectionCenter.class)
				.setParameter("ccCode", ccCode)
				.getSingleResult();
		
		Collection collection = new ObjectMapper().readValue(jsonObject.toString(), Collection.class);
		collection.setFarmer(farmer);
		collection.setCollectionCenter(collectionCenter);
		if(collection.getTimestamp()==null)
			collection.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
		
		entityManager.persist(collection);
		entityManager.getTransaction().commit();
		return collection;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection updateCollection(JSONObject jsonObject) throws JSONException, JsonParseException, JsonMappingException, IOException {
		//JSONObject jsonObject = new JSONObject(jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		
		entityManager.getTransaction().begin();
		int collectionId = Integer.parseInt(jsonObject.get("collectionId").toString());
		Collection collection = entityManager.find(Collection.class, collectionId);
		if(collection==null)
			throw new NotFoundException();
		objectMapper.readerForUpdating(collection).readValue(jsonObject.toString());
		entityManager.persist(collection);
		entityManager.getTransaction().commit();
		
		return collection;
	}
}
