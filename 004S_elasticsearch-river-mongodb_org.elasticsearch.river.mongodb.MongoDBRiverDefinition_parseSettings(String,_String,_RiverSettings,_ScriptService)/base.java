



class MongoDBRiverDefinition {
    void parseSettings_String__String__RiverSettings__ScriptService_() {

    private void processAdminCommandOplogEntry(final DBObject entry, final Timestamp<?> startTimestamp) throws InterruptedException {
        if (logger.isTraceEnabled()) {
            logger.trace("processAdminCommandOplogEntry - [{}]", entry);
        }
        DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
        if (definition.isImportAllCollections()) {
            if (object.containsField(MongoDBRiver.OPLOG_RENAME_COLLECTION_COMMAND_OPERATION) && object.containsField(MongoDBRiver.OPLOG_TO)) {
                String to = object.get(MongoDBRiver.OPLOG_TO).toString();
                if (to.startsWith(definition.getMongoDb())) {
                    String newCollection = getCollectionFromNamespace(to);
                    DBCollection coll = slurpedDb.getCollection(newCollection);
                    doInitialImport(coll);
                }
            }
        }
    }

    private String getCollectionFromNamespace(String namespace) {
        if (namespace.startsWith(definition.getMongoDb()) && CharMatcher.is('.').countIn(namespace) == 1) {
            return namespace.substring(definition.getMongoDb().length() + 1);
        }
        logger.info("Cannot get collection from namespace [{}]", namespace);
        return null;
    }

    private boolean isValidOplogEntry(final DBObject entry, final Timestamp<?> startTimestamp) {
        if (MongoDBRiver.OPLOG_NOOP_OPERATION.equals(entry.get(MongoDBRiver.OPLOG_OPERATION))) {
            logger.debug("[No-op Oplog Entry] - can be ignored. {}", entry);
            return false;
        }
        String namespace = (String) entry.get(MongoDBRiver.OPLOG_NAMESPACE);



        if (entry.containsField(MongoDBRiver.OPLOG_FROM_MIGRATE) && ((BasicBSONObject) entry).getBoolean(MongoDBRiver.OPLOG_FROM_MIGRATE)) {
            logger.debug("[Invalid Oplog Entry] - from migration or sharding operation. Can be ignored. {}", entry);
            return false;
        }

        if (namespace.endsWith(MongoDBRiver.GRIDFS_CHUNKS_SUFFIX)) {
            return false;
        }

        if (startTimestamp != null) {
            Timestamp<?> oplogTimestamp = Timestamp.on(entry);
            if (Timestamp.compare(oplogTimestamp, startTimestamp) < 0) {
                logger.debug("[Invalid Oplog Entry] - entry timestamp [{}] before startTimestamp [{}]", entry, startTimestamp);
                return false;
            }
        }

        boolean validNamespace = false;
        if (definition.isMongoGridFS()) {
            validNamespace = gridfsOplogNamespace.equals(namespace);
        } else {
            if (definition.isImportAllCollections()) {

                if (namespace.startsWith(definition.getMongoDb()) && !namespace.startsWith(definition.getMongoDb() + ".tmp.mr")) {
                    validNamespace = true;
                }
            } else {
                if (definition.getMongoOplogNamespace().equals(namespace)) {
                    validNamespace = true;
                }
            }
            if (cmdOplogNamespace.equals(namespace)) {
                validNamespace = true;
            }

            if (MongoDBRiver.OPLOG_ADMIN_COMMAND.equals(namespace)) {
                validNamespace = true;
            }
        }
        if (!validNamespace) {
            logger.debug("[Invalid Oplog Entry] - namespace [{}] is not valid", namespace);
            return false;
        }
        String operation = (String) entry.get(MongoDBRiver.OPLOG_OPERATION);
        if (!oplogOperations.contains(operation)) {
            logger.debug("[Invalid Oplog Entry] - operation [{}] is not valid", operation);
            return false;
        }


        if (definition.getMongoOplogFilter() != null) {
            DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
            BasicDBObject filter = definition.getMongoOplogFilter();
            if (!filterMatch(filter, object)) {
                logger.debug("[Invalid Oplog Entry] - filter [{}] does not match object [{}]", filter, object);
                return false;
            }
        }
        return true;
    }

    private boolean filterMatch(DBObject filter, DBObject object) {
        for (String key : filter.keySet()) {
            if (!object.containsField(key)) {
                return false;
            }
            if (!filter.get(key).equals(object.get(key))) {
                return false;
            }
        }
        return true;
    }

    private DBObject applyFieldFilter(DBObject object) {
        if (object instanceof GridFSFile) {
            GridFSFile file = (GridFSFile) object;
            DBObject metadata = file.getMetaData();
            if (metadata != null) {
                file.setMetaData(applyFieldFilter(metadata));
            }
        } else {
            object = MongoDBHelper.applyExcludeFields(object, definition.getExcludeFields());
            object = MongoDBHelper.applyIncludeFields(object, definition.getIncludeFields());
        }
        return object;
    }


    private String getObjectIdFromOplogEntry(DBObject entry) {
        if (entry.containsField(MongoDBRiver.OPLOG_OBJECT)) {
            DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
            if (object.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
                return object.get(MongoDBRiver.MONGODB_ID_FIELD).toString();
            }
        }
        if (entry.containsField(MongoDBRiver.OPLOG_UPDATE)) {
            DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_UPDATE);
            if (object.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
                return object.get(MongoDBRiver.MONGODB_ID_FIELD).toString();
            }
        }
        return null;
    }

    private DBCursor oplogCursor(final Timestamp<?> timestampOverride) throws SlurperException {
        Timestamp<?> time = timestampOverride == null ? MongoDBRiver.getLastTimestamp(client, definition) : timestampOverride;
        if (time == null) {
            return null;
        }
        DBObject indexFilter = time.getOplogFilter();
        if (indexFilter == null) {
            return null;
        }

        int options = Bytes.QUERYOPTION_TAILABLE | Bytes.QUERYOPTION_AWAITDATA | Bytes.QUERYOPTION_NOTIMEOUT


                | Bytes.QUERYOPTION_OPLOGREPLAY;

        DBCursor cursor = oplogCollection.find(indexFilter).setOptions(options);


        if (indexFilter.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
            cursor = cursor.hint("_id_");
        }
        isRiverStale(cursor, time);
        return cursor;
    }

    private void isRiverStale(DBCursor cursor, Timestamp<?> time) throws SlurperException {
        if (cursor == null || time == null) {
            return;
        }
        if (definition.getInitialTimestamp() != null && time.equals(definition.getInitialTimestamp())) {
            return;
        }
        DBObject entry = cursor.next();
        Timestamp<?> oplogTimestamp = Timestamp.on(entry);
        if (!time.equals(oplogTimestamp)) {
            MongoDBRiverHelper.setRiverStatus(client, definition.getRiverName(), Status.RIVER_STALE);
            throw new SlurperException("River out of sync with oplog.rs collection");
        }
    }

    private void addQueryToStream(final Operation operation, final Timestamp<?> currentTimestamp, final DBObject update,
            final String collection) throws InterruptedException {
        if (logger.isTraceEnabled()) {
            logger.trace("addQueryToStream - operation [{}], currentTimestamp [{}], update [{}]", operation, currentTimestamp, update);
        }

        if (collection == null) {
            for (String name : slurpedDb.getCollectionNames()) {
                DBCollection slurpedCollection = slurpedDb.getCollection(name);
                for (DBObject item : slurpedCollection.find(update, findKeys)) {
                    addToStream(operation, currentTimestamp, item, collection);
                }
            }
        } else {
            DBCollection slurpedCollection = slurpedDb.getCollection(collection);
            for (DBObject item : slurpedCollection.find(update, findKeys)) {
                addToStream(operation, currentTimestamp, item, collection);
            }
        }
    }

    private String addInsertToStream(final Timestamp<?> currentTimestamp, final DBObject data) throws InterruptedException {
        return addInsertToStream(currentTimestamp, data, definition.getMongoCollection());
    }

    private String addInsertToStream(final Timestamp<?> currentTimestamp, final DBObject data, final String collection)
            throws InterruptedException {
        totalDocuments.incrementAndGet();
        addToStream(Operation.INSERT, currentTimestamp, data, collection);
        if (data == null) {
            return null;
        } else {
            return data.containsField(MongoDBRiver.MONGODB_ID_FIELD) ? data.get(MongoDBRiver.MONGODB_ID_FIELD).toString() : null;
        }
    }

    private void addToStream(final Operation operation, final Timestamp<?> currentTimestamp, final DBObject data, final String collection)
            throws InterruptedException {
        if (logger.isTraceEnabled()) {
            logger.trace("addToStream - operation [{}], currentTimestamp [{}], data [{}], collection [{}]", operation, currentTimestamp,
                    data, collection);
        }

        if (operation == Operation.DROP_DATABASE) {
            logger.info("addToStream - Operation.DROP_DATABASE, currentTimestamp [{}], data [{}], collection [{}]", currentTimestamp,
                    data, collection);
            if (definition.isImportAllCollections()) {
    }
}
