// class name : org.elasticsearch.river.mongodb.Slurper
// method name: assignCollections()
// side       : merge
// region     : lines 286-331 (affected lines: [286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 297, 298, 302, 315, 322, 331])
class Slurper {
    void assignCollections__() {
  286:     	DB adminDb; <== AFFECTED
  287:     	if(!definition.getMongoAdminAuthDatabase().isEmpty()) { <== AFFECTED
  288: 	    	adminDb = mongo.getDB(definition.getMongoAdminAuthDatabase()); <== AFFECTED
  289:     	} else { <== AFFECTED
  290: 	    	adminDb = mongo.getDB(MongoDBRiver.MONGODB_ADMIN_DATABASE); <== AFFECTED
  291:     	} <== AFFECTED
  292:     	 <== AFFECTED
  293:     	if(!definition.getMongoLocalAuthDatabase().isEmpty()) { <== AFFECTED
  294:     		logger.info("Local DB auth against "+definition.getMongoLocalAuthDatabase()+" user: "+definition.getMongoLocalUser()); <== AFFECTED
  295: 	    	oplogDb = mongo.getDB(definition.getMongoLocalAuthDatabase()); <== AFFECTED
  296:     	} else {
  297:     		logger.info("Local DB auth against local user: "+definition.getMongoLocalUser()); <== AFFECTED
  298: 	    	oplogDb = mongo.getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE); <== AFFECTED
  299:     	}
  300: 
  301:         if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty()) {
  302:             logger.debug("Authenticate {} with {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, definition.getMongoAdminUser()); <== AFFECTED
  303: 
  304:             CommandResult cmd = adminDb.authenticateCommand(definition.getMongoAdminUser(), definition.getMongoAdminPassword()
  305:                     .toCharArray());
  306:             if (!cmd.ok()) {
  307:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, cmd.getErrorMessage());
  308:                 // Can still try with mongoLocal credential if provided.
  309:                 // return false;
  310:             }
  311:             oplogDb = adminDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE);
  312:         }
  313: 
  314:         if (!definition.getMongoLocalUser().isEmpty() && !definition.getMongoLocalPassword().isEmpty() && !oplogDb.isAuthenticated()) {
  315:             logger.debug("Authenticate {} with {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, definition.getMongoLocalUser()); <== AFFECTED
  316:             CommandResult cmd = oplogDb.authenticateCommand(definition.getMongoLocalUser(), definition.getMongoLocalPassword()
  317:                     .toCharArray());
  318:             if (!cmd.ok()) {
  319:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, cmd.getErrorMessage());
  320:                 return false;
  321:             }
  322:             oplogDb = oplogDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE); <== AFFECTED
  323:         }
  324: 
  325:         Set<String> collections = oplogDb.getCollectionNames();
  326:         if (!collections.contains(MongoDBRiver.OPLOG_COLLECTION)) {
  327:             logger.error("Cannot find " + MongoDBRiver.OPLOG_COLLECTION + " collection. Please check this link: http://goo.gl/2x5IW");
  328:             return false;
  329:         }
  330:         oplogCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_COLLECTION);
  331:         oplogRefsCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_REFS_COLLECTION); <== AFFECTED
    }
}
