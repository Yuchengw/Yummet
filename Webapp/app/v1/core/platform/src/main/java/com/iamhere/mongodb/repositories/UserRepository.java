package com.iamhere.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.iamhere.mongodb.entities.DBUserObject;

@Repository
public interface UserRepository extends MongoRepository<DBUserObject, String>{

}
