package com.repo.depo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.repo.depo.authentication.DSResponse;
import com.repo.depo.authentication.SmartGWTDSResponse;

@RestController
@RequestMapping("/data")
public class DataController {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/{entityName}/insert", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> insert(@PathVariable("entityName") String collectionName, @Valid @RequestBody String jsonString) {
	    Document doc = Document.parse(jsonString);
	    mongoTemplate.insert(doc, collectionName);
		DSResponse dsResponse = new DSResponse();
		dsResponse.setData(null);
		SmartGWTDSResponse response = new SmartGWTDSResponse();
		response.setResponse(dsResponse);
       return ResponseEntity.accepted().body(response);		
	}
	

	@RequestMapping(value ="/{entityName}/all", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<SmartGWTDSResponse> getAll(@PathVariable("entityName") String collectionName) {
	    List<BasicDBObject> data = mongoTemplate.findAll(BasicDBObject.class, collectionName);
	    for (int i = 0; i < data.size(); i++) {
	    	data.get(i).put("id",data.get(i).getObjectId("_id").toString());
	    	data.get(i).remove("_id");
		}
	    DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(data.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}
	

	
	
	@RequestMapping(value ="/{entityName}/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<SmartGWTDSResponse> getAll(@PathVariable("entityName") String collectionName,@PathVariable("id") String id) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		List<BasicDBObject> data = mongoTemplate.find(query, BasicDBObject.class, collectionName);
		DSResponse dsResponse = new DSResponse();
		dsResponse.setData(data.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value="/{entityName}/update/{id}", method=RequestMethod.PUT,produces = "application/json", headers =
	    { "Accept=application/json", "Content-Type=application/json" }, consumes = "application/json" )
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> update(@PathVariable("entityName") String collectionName,@PathVariable("id") String id, @Valid @RequestBody String jsonString) {
	    Document doc = Document.parse(jsonString);
	    Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		DBObject dbDoc = new BasicDBObject();
		doc.remove("id");
		mongoTemplate.getConverter().write(doc, dbDoc);
		
		Update update = Update.fromDBObject(dbDoc);
	    mongoTemplate.upsert(query, update, collectionName);
		
		DSResponse dsResponse = new DSResponse();
		dsResponse.setData(null);
		SmartGWTDSResponse response = new SmartGWTDSResponse();
		response.setResponse(dsResponse);
       return ResponseEntity.accepted().body(response);			
	}
	
	@RequestMapping(value="/{entityName}/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> delete(@PathVariable("entityName") String collectionName, @PathVariable("id") String id) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		mongoTemplate.remove(query, collectionName);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}
	
}
