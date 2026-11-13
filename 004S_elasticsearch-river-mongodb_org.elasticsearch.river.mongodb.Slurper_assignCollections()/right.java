class Slurper {
    void assignCollections__() {
        DB adminDb = mongo.getDB(MongoDBRiver.MONGODB_ADMIN_DATABASE);
        oplogDb = mongo.getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE);

        if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty()) {
            logger.debug("Authenticate {} with {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, definition.getMongoAdminUser());

            CommandResult cmd = adminDb.authenticateCommand(definition.getMongoAdminUser(), definition.getMongoAdminPassword()
                    .toCharArray());
            if (!cmd.ok()) {
                logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_ADMIN_DATABASE, cmd.getErrorMessage());
            }
            oplogDb = adminDb.getMongo().getDB(MongoDBRiver.MONGODB_LOCAL_DATABASE);
        }

        if (!definition.getMongoLocalUser().isEmpty() && !definition.getMongoLocalPassword().isEmpty() && !oplogDb.isAuthenticated()) {
            logger.debug("Authenticate {} with {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, definition.getMongoLocalUser());
            CommandResult cmd = oplogDb.authenticateCommand(definition.getMongoLocalUser(), definition.getMongoLocalPassword()
                    .toCharArray());
            if (!cmd.ok()) {
                logger.error("Autenticatication failed for {}: {}", MongoDBRiver.MONGODB_LOCAL_DATABASE, cmd.getErrorMessage());
                return false;
            }
        }

        Set<String> collections = oplogDb.getCollectionNames();
        if (!collections.contains(MongoDBRiver.OPLOG_COLLECTION)) {
            logger.error("Cannot find " + MongoDBRiver.OPLOG_COLLECTION + " collection. Please check this link: http://goo.gl/2x5IW");
            return false;
        }
        oplogCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_COLLECTION);
        oplogRefsCollection = oplogDb.getCollection(MongoDBRiver.OPLOG_REFS_COLLECTION);

        slurpedDb = mongo.getDB(definition.getMongoDb());
        if (!definition.getMongoAdminUser().isEmpty() && !definition.getMongoAdminPassword().isEmpty() && adminDb.isAuthenticated()) {
            slurpedDb = adminDb.getMongo().getDB(definition.getMongoDb());
        }

    }
}
