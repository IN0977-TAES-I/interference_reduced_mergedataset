// class name : org.elasticsearch.river.mongodb.Slurper
// method name: assignCollections()
// side       : left
// region     : lines 286-331 (affected lines: [286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 297, 298, 302, 315, 322, 331])
class Slurper {
    void assignCollections__() {
  286: 	    	adminDb = mongo.getDB(MongoDBRiver.MONGODB_ADMIN_DATABASE); <== AFFECTED
  287:     	} <== AFFECTED
  288:     	 <== AFFECTED
  289:     	if(!definition.getMongoLocalAuthDatabase().isEmpty()) { <== AFFECTED
  290:     		logger.info("Local DB auth against "+definition.getMongoLocalAuthDatabase()+" user: "+definition.getMongoLocalUser()); <== AFFECTED
  291: 	    	oplogDb = mongo.getDB(definition.getMongoLocalAuthDatabase()); <== AFFECTED
  292:     	} else { <== AFFECTED
  293:     		logger.info("Local DB auth against local user: "+definition.getMongoLocalUser()); <== AFFECTED
  294: 	    	oplogDb = mongo.getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE); <== AFFECTED
  295:     	} <== AFFECTED
  296: 
  297:         if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty()) { <== AFFECTED
  298:             logger.info("Authenticate {} with {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, definition.getMongoAdminUser()); <== AFFECTED
  299: 
  300:             CommandResult cmd = adminDb.authenticateCommand(definition.getMongoAdminUser(), definition.getMongoAdminPassword()
  301:                     .toCharArray());
  302:             if (!cmd.ok()) { <== AFFECTED
  303:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, cmd.getErrorMessage());
  304:                 // Can still try with mongoLocal credential if provided.
  305:                 // return false;
  306:             }
  307:             oplogDb = adminDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE);
  308:         }
  309: 
  310:         if (!definition.getMongoLocalUser().isEmpty() && !definition.getMongoLocalPassword().isEmpty() && !oplogDb.isAuthenticated()) {
  311:             logger.info("Authenticate {} with {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, definition.getMongoLocalUser());
  312:             CommandResult cmd = oplogDb.authenticateCommand(definition.getMongoLocalUser(), definition.getMongoLocalPassword()
  313:                     .toCharArray());
  314:             if (!cmd.ok()) {
  315:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, cmd.getErrorMessage()); <== AFFECTED
  316:                 return false;
  317:             }
  318:             oplogDb = oplogDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE);
  319:         }
  320: 
  321:         Set<String> collections = oplogDb.getCollectionNames();
  322:         if (!collections.contains(MongoDBRiver.OPLOG_COLLECTION)) { <== AFFECTED
  323:             logger.error("Cannot find " + MongoDBRiver.OPLOG_COLLECTION + " collection. Please check this link: http://goo.gl/2x5IW");
  324:             return false;
  325:         }
  326:         oplogCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_COLLECTION);
  327: 
  328:         slurpedDb = mongo.getDB(definition.getMongoDb());
  329:         if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty() && adminDb.isAuthenticated()) {
  330:             slurpedDb = adminDb.getMongo().getDB(definition.getMongoDb());
  331:         } <== AFFECTED
    }
}
