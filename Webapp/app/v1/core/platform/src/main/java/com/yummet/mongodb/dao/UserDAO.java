package com.yummet.mongodb.dao;

import com.yummet.mongodb.entities.DBUserObject;

/**
 * The MongoDb Interface for the User Object
 * It only extends MongoDbInterface and it does not hav other extensions
 * @author jassica
 *
 */
@Deprecated
public interface UserDAO extends MongoDbInterface<DBUserObject> {
}
