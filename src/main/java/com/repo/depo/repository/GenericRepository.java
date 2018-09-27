package com.repo.depo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.repo.depo.model.AbstractEntityObject;

public interface GenericRepository extends MongoRepository<AbstractEntityObject, String> {
	
	Optional<AbstractEntityObject> findById(String id);

	void deleteById(String id);

}
