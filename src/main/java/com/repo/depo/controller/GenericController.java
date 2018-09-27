package com.repo.depo.controller;

import java.util.List;

import javax.validation.Valid;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.repo.depo.authentication.DSResponse;
import com.repo.depo.authentication.SmartGWTDSResponse;

@RestController
@RequestMapping("/generic")
public class GenericController {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/{collectionName}/insert", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> insert(@PathVariable("collectionName") String collectionName, @Valid @RequestBody String jsonString) {
	    Document doc = Document.parse(jsonString);
	    mongoTemplate.insert(doc, collectionName);
		DSResponse dsResponse = new DSResponse();
		dsResponse.setData(null);
		SmartGWTDSResponse response = new SmartGWTDSResponse();
		response.setResponse(dsResponse);
       return ResponseEntity.accepted().body(response);		
	}
	

	@RequestMapping(value ="/{collectionName}/all", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<SmartGWTDSResponse> getAll(@PathVariable("collectionName") String collectionName) {
	    List<BasicDBObject> data = mongoTemplate.findAll(BasicDBObject.class, collectionName);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(data.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value="/{collectionName}/update", method=RequestMethod.PUT,produces = "application/json", headers =
	    { "Accept=application/json", "Content-Type=application/json" }, consumes = "application/json" )
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> update(@PathVariable("collectionName") String collectionName, @Valid @RequestBody String jsonString) {
	    Document doc = Document.parse(jsonString);
	    mongoTemplate.save(doc, collectionName);
		DSResponse dsResponse = new DSResponse();
		dsResponse.setData(null);
		SmartGWTDSResponse response = new SmartGWTDSResponse();
		response.setResponse(dsResponse);
       return ResponseEntity.accepted().body(response);			
	}
	
	@RequestMapping(value="/{collectionName}/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> delete(@PathVariable("collectionName") String collectionName, @PathVariable("id") String id) {
		Query query = new Query();
		query.addCriteria(new Criteria("id").is(id));
		mongoTemplate.remove(query, collectionName);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}
	
}
