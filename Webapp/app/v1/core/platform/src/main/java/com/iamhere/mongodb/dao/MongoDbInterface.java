package com.iamhere.mongodb.dao;

import java.util.List;
import java.util.Map;

@Deprecated
public interface MongoDbInterface<T> {
    /**
     * Insert records to the database
     * @param object
     * @param collectionName
     */
    public void insert(T object,String collectionName);  
    
    /**
     *  Query the collection based on the criteria. 
     *  If there is more than 1 record meet the criteria, just return the first one.
     * @param params
     * @param collectionName
     * @return
     */
    public T findOne(Map<String,Object> params,String collectionName);  
    
    /**
     *  query all the records meet the requirements
     * @param params
     * @param collectionName
     * @return
     */
    public List<T> findAll(Map<String,Object> params,String collectionName);  
    
    /**
     * Update all the records based on the requirements
     * @param params
     * @param collectionName
     */
    public void update(Map<String,Object> params,String collectionName); 
    
    /**
     * Create the collection
     * @param collectionName
     */
    public void createCollection(String collectionName);

    /**
     * Based on the requirments, remove all the records meet
     * @param params
     * @param collectionName
     */
    public void remove(Map<String,Object> params,String collectionName);
    
}
