package com.iamhere.mongodb.dao;

import com.iamhere.mongodb.entities.DBUserObject;

/**
 * The MongoDb Interface for the User Object
 * It only extends MongoDbInterface and it does not hav other extensions
 * @author jassica
 *
 */
public interface UserDAO extends MongoDbInterface<DBUserObject> {
}
