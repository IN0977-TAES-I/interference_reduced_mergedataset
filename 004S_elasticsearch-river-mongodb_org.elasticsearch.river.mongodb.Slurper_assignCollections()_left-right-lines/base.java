// class name : org.elasticsearch.river.mongodb.Slurper
// method name: assignCollections()
// side       : base
// region     : lines 286-331 (affected lines: [286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 297, 298, 302, 315, 322, 331])
class Slurper {
    void assignCollections__() {
  286:             logger.info("Authenticate {} with {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, definition.getMongoAdminUser()); <== AFFECTED
  287:  <== AFFECTED
  288:             CommandResult cmd = adminDb.authenticateCommand(definition.getMongoAdminUser(), definition.getMongoAdminPassword() <== AFFECTED
  289:                     .toCharArray()); <== AFFECTED
  290:             if (!cmd.ok()) { <== AFFECTED
  291:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, cmd.getErrorMessage()); <== AFFECTED
  292:                 // Can still try with mongoLocal credential if provided. <== AFFECTED
  293:                 // return false; <== AFFECTED
  294:             } <== AFFECTED
  295:             oplogDb = adminDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE); <== AFFECTED
  296:         }
  297:  <== AFFECTED
  298:         if (!definition.getMongoLocalUser().isEmpty() && !definition.getMongoLocalPassword().isEmpty() && !oplogDb.isAuthenticated()) { <== AFFECTED
  299:             logger.info("Authenticate {} with {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, definition.getMongoLocalUser());
  300:             CommandResult cmd = oplogDb.authenticateCommand(definition.getMongoLocalUser(), definition.getMongoLocalPassword()
  301:                     .toCharArray());
  302:             if (!cmd.ok()) { <== AFFECTED
  303:                 logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, cmd.getErrorMessage());
  304:                 return false;
  305:             }
  306:         }
  307: 
  308:         Set<String> collections = oplogDb.getCollectionNames();
  309:         if (!collections.contains(MongoDBRiver.OPLOG_COLLECTION)) {
  310:             logger.error("Cannot find " + MongoDBRiver.OPLOG_COLLECTION + " collection. Please check this link: http://goo.gl/2x5IW");
  311:             return false;
  312:         }
  313:         oplogCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_COLLECTION);
  314: 
  315:         slurpedDb = mongo.getDB(definition.getMongoDb()); <== AFFECTED
  316:         if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty() && adminDb.isAuthenticated()) {
  317:             slurpedDb = adminDb.getMongo().getDB(definition.getMongoDb());
  318:         }
  319: 
  320:         // Not necessary as local user has access to all databases.
  321:         // http://docs.mongodb.org/manual/reference/local-database/
  322:         // if (!mongoDbUser.isEmpty() && !mongoDbPassword.isEmpty() <== AFFECTED
  323:         // && !slurpedDb.isAuthenticated()) {
  324:         // logger.info("Authenticate {} with {}", mongoDb, mongoDbUser);
  325:         // CommandResult cmd = slurpedDb.authenticateCommand(mongoDbUser,
  326:         // mongoDbPassword.toCharArray());
  327:         // if (!cmd.ok()) {
  328:         // logger.error("Authentication failed for {}: {}",
  329:         // mongoDb, cmd.getErrorMessage());
  330:         // return false;
  331:         // } <== AFFECTED
    }
}
