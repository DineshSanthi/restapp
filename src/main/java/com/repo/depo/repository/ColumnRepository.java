package com.repo.depo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.repo.depo.model.Column;

@Repository
public interface ColumnRepository extends MongoRepository<Column, String>{

	Optional<Column> findById(String id);

	List<Column> findByTableName(String tableName);
	
	void deleteById(String id);
}
