package com.repo.depo.controller;

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.repo.depo.authentication.DSResponse;
import com.repo.depo.authentication.SmartGWTDSResponse;
import com.repo.depo.model.ApplicationUser;
import com.repo.depo.repository.ApplicationUserRepository;

@RestController
@RequestMapping("/applicationuser")
public class ApplicationUserController {
	
	private ApplicationUserRepository applicationUserRepository;
	
	public ApplicationUserController(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@RequestMapping(value="/insert", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> insert(@Valid @RequestBody ApplicationUser applicationUser) {
		ObjectId objectId = new ObjectId();
		applicationUser.setId(objectId.toString());
		this.applicationUserRepository.insert(applicationUser);
		Object[] data = new Object[1];
		data[0] = applicationUser;
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(data);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value="/update", method=RequestMethod.PUT,produces = "application/json", headers =
	    { "Accept=application/json", "Content-Type=application/json" }, consumes = "application/json" )
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> update(@RequestBody ApplicationUser applicationUser) {
		this.applicationUserRepository.save(applicationUser);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody public ResponseEntity<SmartGWTDSResponse> delete(@PathVariable("id") String id) {
		this.applicationUserRepository.deleteById(id);
		DSResponse dsResponse = new DSResponse();
    	dsResponse.setData(null);
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);		
	}

	
	@RequestMapping(value ="/application/{appName}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<SmartGWTDSResponse> getColumns(@PathVariable("appName") String appName) {
		DSResponse dsResponse = new DSResponse();
		List<ApplicationUser> applicationUser = null;
		if(appName.equalsIgnoreCase("null"))
		{
			applicationUser = this.applicationUserRepository.findAll();	
		}
		else
		{
			applicationUser = this.applicationUserRepository.findByAppName(appName);
		}
    	dsResponse.setData(applicationUser.toArray());
    	SmartGWTDSResponse response = new SmartGWTDSResponse();
    	response.setResponse(dsResponse);
        return ResponseEntity.accepted().body(response);
	}

}
