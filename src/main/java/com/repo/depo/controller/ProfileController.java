//package com.repo.depo.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mongodb.BasicDBObject;
//import com.repo.depo.authentication.DSResponse;
//import com.repo.depo.authentication.SmartGWTDSResponse;
//
//@RestController
//@RequestMapping("/profile")
//public class ProfileController {
//	
//	@Autowired
//	private MongoTemplate mongoTemplate;
//	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value ="/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
//	public ResponseEntity<SmartGWTDSResponse> getAll(@PathVariable("id") String id) {
//		Query query = new Query();
//		query.addCriteria(new Criteria("id").is(id));
//		List<BasicDBObject> data = mongoTemplate.findById(id, BasicDBObject.class, "Column");
//		DSResponse dsResponse = new DSResponse();
//    	dsResponse.setData(data.toArray());
//    	SmartGWTDSResponse response = new SmartGWTDSResponse();
//    	response.setResponse(dsResponse);
//        return ResponseEntity.accepted().body(response);
//	}
//
//}
