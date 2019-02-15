package com.strand.ecocert.api.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strand.ecocert.data.constants.CollectionStatus;
import com.strand.ecocert.data.constants.MoistureContentCalculationType;
import com.strand.ecocert.data.entity.Factory;
import com.strand.ecocert.data.process.BatchProduction;
import com.strand.ecocert.data.process.Collection;
import com.strand.ecocert.util.Utility;
import com.sun.jersey.api.NotFoundException;

@Path("batch")
public class BatchService {

	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public BatchService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
	}
	
	@GET
	@Path("{batchId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public BatchProduction getBatch(@PathParam("batchId") int batchId) {
		BatchProduction batch = entityManager.find(BatchProduction.class, batchId);
		if(batch==null) 
			throw new NotFoundException();
		return batch;
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<BatchProduction> getAllBatches() {
		Query query = entityManager.createQuery("Select b from BatchProduction b");
	    List<BatchProduction> batchProductions = query.getResultList();
		return batchProductions;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BatchProduction addBatch(
			String jsonString
			) throws JsonParseException, JsonMappingException, IOException, NumberFormatException, JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		entityManager.getTransaction().begin();
		
		JSONArray jsonArray = jsonObject.getJSONArray("collectionIds");
		
		List<Collection> collections = new ArrayList<Collection>();
		float quantity = 0.0f;
		int batchSize  = jsonArray.length();
		
		float []moistureContents = new float[batchSize];
		for(int i=0;i<batchSize;i++) {
			int collectionId = jsonArray.getInt(i);
			Collection collection = entityManager.find(Collection.class, collectionId);
			moistureContents[i] = collection.getMoistureContent();
			quantity += collection.getQuantity();
			collection.setStatus(CollectionStatus.TRANSFERRED);
			collections.add(collection);
		}
		int factoryId = jsonObject.getInt("factoryId");
		Factory factory = entityManager.find(Factory.class, factoryId);
		
		BatchProduction batch = new BatchProduction();
		batch.setFactoryId(factory);
		
		Timestamp transferTimestamp = null;
		if(!jsonObject.isNull("transferTimestamp"))
			transferTimestamp = Timestamp.valueOf(jsonObject.getString("transferTimestamp"));
		if(transferTimestamp==null)
			batch.setTransferTimestamp(Timestamp.valueOf(LocalDateTime.now()));
		else
			batch.setTransferTimestamp(transferTimestamp);
		
		batch.setproductions(collections);
		batch.setWeight(quantity);
		
		MoistureContentCalculationType type;
		if(jsonObject.isNull("moistureContentCalculationType"))
			type = MoistureContentCalculationType.AVERAGE;
		else
			type = MoistureContentCalculationType.valueOf(jsonObject.getString("moistureContentCalculationType"));
		
		float moistureContent = 0.0f;
		if(MoistureContentCalculationType.MANUAL.equals(type)) {
			moistureContent = (float) jsonObject.getDouble("moistureContent");
		} else {
			moistureContent = Utility.getAggregateValue(moistureContents, type);
		}
		batch.setMoistureContent(moistureContent);
		
		entityManager.persist(batch);
		entityManager.getTransaction().commit();
		return batch;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BatchProduction updateBatch(JSONObject jsonObject) throws JSONException, JsonParseException, JsonMappingException, IOException {
		//JSONObject jsonObject = new JSONObject(jsonString);
		ObjectMapper objectMapper = new ObjectMapper();
		
		entityManager.getTransaction().begin();
		int batchId = Integer.parseInt(jsonObject.get("collectionId").toString());
		BatchProduction batch = entityManager.find(BatchProduction.class, batchId);
		if(batch==null)
			throw new NotFoundException();
		objectMapper.readerForUpdating(batch).readValue(jsonObject.toString());
		entityManager.persist(batch);
		entityManager.getTransaction().commit();
		
		return batch;
	}
}
