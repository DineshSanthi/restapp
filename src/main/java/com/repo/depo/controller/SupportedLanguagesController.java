package com.repo.depo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
=======
>>>>>>> 5851bc25dbd16e15d309b40a31d80f719fa65028
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.repo.depo.authentication.DSResponse;
import com.repo.depo.authentication.SmartGWTDSResponse;
import com.repo.depo.model.ApplicationProperties;
import com.repo.depo.model.Column;
import com.repo.depo.model.Relation;
import com.repo.depo.model.SupportedLanguages;
import com.repo.depo.model.Table;
import com.repo.depo.repository.ApplicationPropertiesRepository;
import com.repo.depo.repository.ColumnRepository;
import com.repo.depo.repository.SupportedLanguagesRepository;

@RestController
@RequestMapping("/supportedlanguages")
public class SupportedLanguagesController {
	
<<<<<<< HEAD
	@Autowired
	private MongoTemplate mongoTemplate;
	
=======
>>>>>>> 5851bc25dbd16e15d309b40a31d80f719fa65028
	private SupportedLanguagesRepository supportedLanguagesRepository;
	
	public SupportedLanguagesController(SupportedLanguagesRepository supportedLanguagesRepository) {
		this.supportedLanguagesRepository = supportedLanguagesRepository;
	}

	@RequestMapping(value="/insert", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> insert(@Valid @RequestBody SupportedLanguages supportedLanguages) {
		ObjectId objectId = new ObjectId();
		supportedLanguages.setId(objectId.toString());
		this.supportedLanguagesRepository.insert(supportedLanguages);
		Object[] data = new Object[1];
		data[0] = supportedLanguages;
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(data);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value="/update", method=RequestMethod.PUT,produces = "application/json", headers =
	    { "Accept=application/json", "Content-Type=application/json" }, consumes = "application/json" )
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> update(@RequestBody SupportedLanguages supportedLanguages) {
		this.supportedLanguagesRepository.save(supportedLanguages);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> delete(@PathVariable("id") String id) {
		this.supportedLanguagesRepository.deleteById(id);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	
	@RequestMapping(value ="/application/{appName}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE )
<<<<<<< HEAD
	public ResponseEntity<SmartGWTDSResponse> getColumns(@PathVariable("appName") String appName,@Valid @RequestBody String jsonString) {
		
		Document doc = Document.parse(jsonString);
		String value = doc.get("data").toString();
		Query query = new Query();
		Document docData = (Document)doc.get("data");
		if (docData.size() > 0) {
			for (String key : docData.keySet()) {
				query.addCriteria(new Criteria(key).is(docData.get(key)));
			}
		}
		List<SupportedLanguages> supportedLanguages = mongoTemplate.find(query, SupportedLanguages.class);
		DSResponse dsResponse = new DSResponse();
=======
	public ResponseEntity<SmartGWTDSResponse> getColumns(@PathVariable("appName") String appName) {
		DSResponse dsResponse = new DSResponse();
		List<SupportedLanguages> supportedLanguages = null;
		if(appName.equalsIgnoreCase("null"))
		{
			supportedLanguages = this.supportedLanguagesRepository.findAll();	
		}
		else
		{
			supportedLanguages = this.supportedLanguagesRepository.findByAppName(appName);
		}
>>>>>>> 5851bc25dbd16e15d309b40a31d80f719fa65028
    	dsResponse.setData(supportedLanguages.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}

}