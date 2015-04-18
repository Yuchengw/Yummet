# Yummet
Development Environment.

Please Contribute the Master branch very carefully, otherwise, be careful of your kneecaps;)

===============================Platform Specification============================

This document is for platform code.
The whole platform is divided to packages:

1. Entities: It contains all the platform entities (I only finish the basic stuff so far)
1) EntityObject: Base class for all the entities whose information needs to be saved
2) OrderObject: Not Completely done half way
3) PostComment: Entity Object for the post comment. 
4) PostObject: Entity Object for the Post. 
5) RecurEventInfo: Did not touch so far (Please ignore it)
6) UserObject: To hold all user related information

2. Enums: Hold all enums needs for the platform object. All the enum will have both db and api values. 
1) DMLEvents: CRUD activities
2) PostStatus: Open, Close, Expire, Removed
3) TimeUnit: Half done but need more change based on RecurEventInfo

3. main_platform: Please ignore this for now. It was for old framework.

4. Mongodb.entities: It contains all the reflectionDbObject for all platform entities  (I only finish the basic stuff so far)
1) DBEntityObject: Base class for all the db representation of platform entities
2) DBOrderObject: Not complete
3) DBPostComment: PostComment db representation. UserObject and Parent post information are all in reference id instead of the whole object.
4) DBPostObject: PostObject db representation. UserObject and other entity information is delegated to reference id instead of the whole object. We will do the lazy load.
5) DBUserObject: UserObject db representation.

5. Adapters: It has all the DB related information
1) DatabaseProvider: The interface for the DB operations. In the future, we can change it to other DB tools.
2) MongoDbProvider: Implementation of DatabaseProvider for mongo db. It includes CRUD operations. It meets the basic requirement, but still need:
	a) Bulk support for some function
	b) Transaction add
	c) Lock (I think mongo db did it automatically but need double check)
3) DBContext: Platform DB caller
4) IDBContext: Interface for the platform db adapter

6. func: Platform operations information
1) BulkEntityOperations: Platform to do the bulk save, load... etc. It will call the DmlOperationWrapper and DmlValidationHandler to finish the work.
2) DmlOperationWrapper: Hold the information for the DMLValidation result, and return the correct set of information to the caller. For example, the valid entity set from the input.
3) DmlValidationHandler: Valid the entities before doing the real db work so that we can fail fast.

7. Relationships: Hold the relationship information between entities. It is not done, and will make the changes.

8. Transhbin: Contain all old framework information which I did not plan to use any more.


@copy Yumment.inc
