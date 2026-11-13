// class name : org.elasticsearch.river.mongodb.Slurper
// method name: assignCollections()
// side       : right
// region     : lines 286-331 (affected lines: [286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 297, 298, 302, 315, 322, 331])
class Slurper {
    void assignCollections__() {
  286:         DB adminDb = mongo.getDB(MongoDBRiver.MONGODB_ADMIN_DATABASE); <== AFFECTED
  287:         oplogDb = mongo.getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE); <== AFFECTED
  288:  <== AFFECTED
  289:         if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty()) { <== AFFECTED
  290:             logger.debug("Authenticate {} with {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, definition.getMongoAdminUser()); <== AFFECTED
  291:  <== AFFECTED
  292:             CommandResult cmd = adminDb.authenticateCommand(definition.getMongoAdminUser(), definition.getMongoAdminPassword() <== AFFECTED
  293:                     .toCharArray()); <== AFFECTED
  294:             if (!cmd.ok()) { <== AFFECTED
  295:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, cmd.getErrorMessage()); <== AFFECTED
  296:                 // Can still try with mongoLocal credential if provided.
  297:                 // return false; <== AFFECTED
  298:             } <== AFFECTED
  299:             oplogDb = adminDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE);
  300:         }
  301: 
  302:         if (!definition.getMongoLocalUser().isEmpty() && !definition.getMongoLocalPassword().isEmpty() && !oplogDb.isAuthenticated()) { <== AFFECTED
  303:             logger.debug("Authenticate {} with {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, definition.getMongoLocalUser());
  304:             CommandResult cmd = oplogDb.authenticateCommand(definition.getMongoLocalUser(), definition.getMongoLocalPassword()
  305:                     .toCharArray());
  306:             if (!cmd.ok()) {
  307:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, cmd.getErrorMessage());
  308:                 return false;
  309:             }
  310:         }
  311: 
  312:         Set<String> collections = oplogDb.getCollectionNames();
  313:         if (!collections.contains(MongoDBRiver.OPLOG_COLLECTION)) {
  314:             logger.error("Cannot find " + MongoDBRiver.OPLOG_COLLECTION + " collection. Please check this link: http://goo.gl/2x5IW");
  315:             return false; <== AFFECTED
  316:         }
  317:         oplogCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_COLLECTION);
  318:         oplogRefsCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_REFS_COLLECTION);
  319: 
  320:         slurpedDb = mongo.getDB(definition.getMongoDb());
  321:         if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty() && adminDb.isAuthenticated()) {
  322:             slurpedDb = adminDb.getMongo().getDB(definition.getMongoDb()); <== AFFECTED
  323:         }
  324: 
  325:         // Not necessary as local user has access to all databases.
  326:         // http://docs.mongodb.org/manual/reference/local-database/
  327:         // if (!mongoDbUser.isEmpty() && !mongoDbPassword.isEmpty()
  328:         // && !slurpedDb.isAuthenticated()) {
  329:         // logger.info("Authenticate {} with {}", mongoDb, mongoDbUser);
  330:         // CommandResult cmd = slurpedDb.authenticateCommand(mongoDbUser,
  331:         // mongoDbPassword.toCharArray()); <== AFFECTED
    }
}
