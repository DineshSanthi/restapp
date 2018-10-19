package com.repo.depo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.repo.depo.model.Column;
import com.repo.depo.model.Relation;
import com.repo.depo.model.Table;
import com.repo.depo.repository.ColumnRepository;

@RestController
@RequestMapping("/column")
public class ColumnController {
	
	private ColumnRepository columnRepository;
	
	@Autowired
	private RelationController relationController;

	public ColumnController(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	@RequestMapping(value ="/all", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<SmartGWTDSResponse> getAll() {
		DSResponse dsResponse = new DSResponse();
    	List<Column> tables = this.columnRepository.findAll();
    	dsResponse.setData(tables.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value="/insert", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> insert(@Valid @RequestBody Column column) {
		this.columnRepository.insert(column);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value="/update", method=RequestMethod.PUT,produces = "application/json", headers =
	    { "Accept=application/json", "Content-Type=application/json" }, consumes = "application/json" )
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> update(@RequestBody Column column) {
		this.columnRepository.save(column);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> delete(@PathVariable("id") String id) {
		this.columnRepository.deleteById(id);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value ="/{collectionName}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<SmartGWTDSResponse> getTables(@PathVariable("collectionName") String tableName) {
		DSResponse dsResponse = new DSResponse();
    	List<Column> columns = this.columnRepository.findByTableName(tableName);
    	dsResponse.setData(columns.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value ="/definition/{collectionName}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Object[]> getTableDefinition(@PathVariable("collectionName") String tableName) {
    	List<Column> columns = this.columnRepository.findByTableName(tableName);
    	ResponseEntity<List<Relation>> relationList = relationController.getRelation(tableName);
 	    //List<Object[]> holder = new ArrayList<>();
 	    
 	    Object[] definition = new Object[2];
 	    definition[0] = columns;
 	    definition[1] = relationList.getBody();
 	    //Collections.addAll(holder, columns);
 	    //Collections.addAll(holder, );
        return ResponseEntity.accepted().body(definition);
	}
	

}
