package com.yummet.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yummet.mongodb.entities.DBUserObject;

@Deprecated
@Repository
public interface UserRepository extends MongoRepository<DBUserObject, String>{

}
