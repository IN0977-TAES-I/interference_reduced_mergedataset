// class name : org.elasticsearch.river.mongodb.MongoDBRiverDefinition
// method name: parseSettings(String, String, RiverSettings, ScriptService)
// side       : right
// region     : lines 490-717 (affected lines: [490, 515, 576, 582, 617, 635, 670, 683, 686, 699, 703, 714, 717])
class MongoDBRiverDefinition {
    void parseSettings_String__String__RiverSettings__ScriptService_() {
  490:                     if (operation.equals(MongoDBRiver.OPLOG_COMMAND_OPERATION)) { <== AFFECTED
  491:                         DBObject object = (DBObject) op.get(MongoDBRiver.OPLOG_OBJECT);
  492:                         if (object.containsField(MongoDBRiver.OPLOG_CREATE_COMMAND)) {
  493:                             continue;
  494:                         }
  495:                     }
  496:                     entry.putAll(op);
  497:                 }
  498:             } catch (ClassCastException e) {
  499:                 logger.error(e.toString(), e);
  500:             }
  501:         }
  502:     }
  503: 
  504:     private Object getRefOps(Object ref) {
  505:         // db.oplog.refs.find({_id: {$gte: {oid: %ref%}}}).limit(1)
  506:         DBObject query = new BasicDBObject(MongoDBRiver.MONGODB_ID_FIELD, new BasicDBObject(QueryOperators.GTE,
  507:                 new BasicDBObject(MongoDBRiver.MONGODB_OID_FIELD, ref)));
  508:         DBObject oplog = oplogRefsCollection.findOne(query);
  509:         return oplog == null ? null : oplog.get("ops");
  510:     }
  511: 
  512:     private void processAdminCommandOplogEntry(final DBObject entry, final Timestamp<?> startTimestamp) throws InterruptedException {
  513:         if (logger.isTraceEnabled()) {
  514:             logger.trace("processAdminCommandOplogEntry - [{}]", entry);
  515:         } <== AFFECTED
  516:         DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
  517:         if (definition.isImportAllCollections()) {
  518:             if (object.containsField(MongoDBRiver.OPLOG_RENAME_COLLECTION_COMMAND_OPERATION) && object.containsField(MongoDBRiver.OPLOG_TO)) {
  519:                 String to = object.get(MongoDBRiver.OPLOG_TO).toString();
  520:                 if (to.startsWith(definition.getMongoDb())) {
  521:                     String newCollection = getCollectionFromNamespace(to);
  522:                     DBCollection coll = slurpedDb.getCollection(newCollection);
  523:                     doInitialImport(coll);
  524:                 }
  525:             }
  526:         }
  527:     }
  528: 
  529:     private String getCollectionFromNamespace(String namespace) {
  530:         if (namespace.startsWith(definition.getMongoDb()) && CharMatcher.is('.').countIn(namespace) == 1) {
  531:             return namespace.substring(definition.getMongoDb().length() + 1);
  532:         }
  533:         logger.error("Cannot get collection from namespace [{}]", namespace);
  534:         return null;
  535:     }
  536: 
  537:     private boolean isValidOplogEntry(final DBObject entry, final Timestamp<?> startTimestamp) {
  538:         if (!entry.containsField(MongoDBRiver.OPLOG_OPERATION)) {
  539:             logger.trace("[Empty Oplog Entry] - can be ignored. {}", JSONSerializers.getStrict().serialize(entry));
  540:             return false;
  541:         }
  542:         if (MongoDBRiver.OPLOG_NOOP_OPERATION.equals(entry.get(MongoDBRiver.OPLOG_OPERATION))) {
  543:             logger.trace("[No-op Oplog Entry] - can be ignored. {}", JSONSerializers.getStrict().serialize(entry));
  544:             return false;
  545:         }
  546:         String namespace = (String) entry.get(MongoDBRiver.OPLOG_NAMESPACE);
  547:         // Initial support for sharded collection -
  548:         // https://jira.mongodb.org/browse/SERVER-4333
  549:         // Not interested in operation from migration or sharding
  550:         if (entry.containsField(MongoDBRiver.OPLOG_FROM_MIGRATE) && ((BasicBSONObject) entry).getBoolean(MongoDBRiver.OPLOG_FROM_MIGRATE)) {
  551:             logger.trace("[Invalid Oplog Entry] - from migration or sharding operation. Can be ignored. {}", JSONSerializers.getStrict().serialize(entry));
  552:             return false;
  553:         }
  554:         // Not interested by chunks - skip all
  555:         if (namespace.endsWith(MongoDBRiver.GRIDFS_CHUNKS_SUFFIX)) {
  556:             return false;
  557:         }
  558: 
  559:         if (startTimestamp != null) {
  560:             Timestamp<?> oplogTimestamp = Timestamp.on(entry);
  561:             if (Timestamp.compare(oplogTimestamp, startTimestamp) < 0) {
  562:                 logger.error("[Invalid Oplog Entry] - entry timestamp [{}] before startTimestamp [{}]",
  563:                         JSONSerializers.getStrict().serialize(entry), startTimestamp);
  564:                 return false;
  565:             }
  566:         }
  567: 
  568:         boolean validNamespace = false;
  569:         if (definition.isMongoGridFS()) {
  570:             validNamespace = gridfsOplogNamespace.equals(namespace);
  571:         } else {
  572:             if (definition.isImportAllCollections()) {
  573:                 // Skip temp entry generated by map / reduce
  574:                 if (namespace.startsWith(definition.getMongoDb()) && !namespace.startsWith(definition.getMongoDb() + ".tmp.mr")) {
  575:                     validNamespace = true;
  576:                 } <== AFFECTED
  577:             } else {
  578:                 if (definition.getMongoOplogNamespace().equals(namespace)) {
  579:                     validNamespace = true;
  580:                 }
  581:             }
  582:             if (cmdOplogNamespace.equals(namespace)) { <== AFFECTED
  583:                 validNamespace = true;
  584:             }
  585: 
  586:             if (MongoDBRiver.OPLOG_ADMIN_COMMAND.equals(namespace)) {
  587:                 validNamespace = true;
  588:             }
  589:         }
  590:         if (!validNamespace) {
  591:             logger.trace("[Invalid Oplog Entry] - namespace [{}] is not valid", namespace);
  592:             return false;
  593:         }
  594:         String operation = (String) entry.get(MongoDBRiver.OPLOG_OPERATION);
  595:         if (!oplogOperations.contains(operation)) {
  596:             logger.trace("[Invalid Oplog Entry] - operation [{}] is not valid", operation);
  597:             return false;
  598:         }
  599: 
  600:         // TODO: implement a better solution
  601:         if (definition.getMongoOplogFilter() != null) {
  602:             DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
  603:             BasicDBObject filter = definition.getMongoOplogFilter();
  604:             if (!filterMatch(filter, object)) {
  605:                 logger.trace("[Invalid Oplog Entry] - filter [{}] does not match object [{}]", filter, object);
  606:                 return false;
  607:             }
  608:         }
  609:         return true;
  610:     }
  611: 
  612:     private boolean filterMatch(DBObject filter, DBObject object) {
  613:         for (String key : filter.keySet()) {
  614:             if (!object.containsField(key)) {
  615:                 return false;
  616:             }
  617:             if (!filter.get(key).equals(object.get(key))) { <== AFFECTED
  618:                 return false;
  619:             }
  620:         }
  621:         return true;
  622:     }
  623: 
  624:     private DBObject applyFieldFilter(DBObject object) {
  625:         if (object instanceof GridFSFile) {
  626:             GridFSFile file = (GridFSFile) object;
  627:             DBObject metadata = file.getMetaData();
  628:             if (metadata != null) {
  629:                 file.setMetaData(applyFieldFilter(metadata));
  630:             }
  631:         } else {
  632:             object = MongoDBHelper.applyExcludeFields(object, definition.getExcludeFields());
  633:             object = MongoDBHelper.applyIncludeFields(object, definition.getIncludeFields());
  634:         }
  635:         return object; <== AFFECTED
  636:     }
  637: 
  638:     /*
  639:      * Extract "_id" from "o" if it fails try to extract from "o2"
  640:      */
  641:     private String getObjectIdFromOplogEntry(DBObject entry) {
  642:         if (entry.containsField(MongoDBRiver.OPLOG_OBJECT)) {
  643:             DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
  644:             if (object.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
  645:                 return object.get(MongoDBRiver.MONGODB_ID_FIELD).toString();
  646:             }
  647:         }
  648:         if (entry.containsField(MongoDBRiver.OPLOG_UPDATE)) {
  649:             DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_UPDATE);
  650:             if (object.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
  651:                 return object.get(MongoDBRiver.MONGODB_ID_FIELD).toString();
  652:             }
  653:         }
  654:         return null;
  655:     }
  656: 
  657:     private DBCursor oplogCursor(final Timestamp<?> timestampOverride) throws SlurperException {
  658:         Timestamp<?> time = timestampOverride == null ? MongoDBRiver.getLastTimestamp(client, definition) : timestampOverride;
  659:         if (time == null) {
  660:             return null;
  661:         }
  662:         DBObject indexFilter = time.getOplogFilter();
  663:         if (indexFilter == null) {
  664:             return null;
  665:         }
  666: 
  667:         int options = Bytes.QUERYOPTION_TAILABLE | Bytes.QUERYOPTION_AWAITDATA | Bytes.QUERYOPTION_NOTIMEOUT
  668:         // Using OPLOGREPLAY to improve performance:
  669:         // https://jira.mongodb.org/browse/JAVA-771
  670:                 | Bytes.QUERYOPTION_OPLOGREPLAY; <== AFFECTED
  671: 
  672:         DBCursor cursor = oplogCollection.find(indexFilter).setOptions(options);
  673: 
  674:         // Toku sometimes gets stuck without this hint:
  675:         if (indexFilter.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
  676:             cursor = cursor.hint("_id_");
  677:         }
  678:         isRiverStale(cursor, time);
  679:         return cursor;
  680:     }
  681: 
  682:     private void isRiverStale(DBCursor cursor, Timestamp<?> time) throws SlurperException {
  683:         if (cursor == null || time == null) { <== AFFECTED
  684:             return;
  685:         }
  686:         if (definition.getInitialTimestamp() != null && time.equals(definition.getInitialTimestamp())) { <== AFFECTED
  687:             return;
  688:         }
  689:         DBObject entry = cursor.next();
  690:         Timestamp<?> oplogTimestamp = Timestamp.on(entry);
  691:         if (!time.equals(oplogTimestamp)) {
  692:             MongoDBRiverHelper.setRiverStatus(client, definition.getRiverName(), Status.RIVER_STALE);
  693:             throw new SlurperException("River out of sync with oplog.rs collection");
  694:         }
  695:     }
  696: 
  697:     private void addQueryToStream(final Operation operation, final Timestamp<?> currentTimestamp, final DBObject update,
  698:             final String collection) throws InterruptedException {
  699:         if (logger.isTraceEnabled()) { <== AFFECTED
  700:             logger.trace("addQueryToStream - operation [{}], currentTimestamp [{}], update [{}]", operation, currentTimestamp, update);
  701:         }
  702: 
  703:         if (collection == null) { <== AFFECTED
  704:             for (String name : slurpedDb.getCollectionNames()) {
  705:                 DBCollection slurpedCollection = slurpedDb.getCollection(name);
  706:                 for (DBObject item : slurpedCollection.find(update, findKeys)) {
  707:                     addToStream(operation, currentTimestamp, item, collection);
  708:                 }
  709:             }
  710:         } else {
  711:             DBCollection slurpedCollection = slurpedDb.getCollection(collection);
  712:             for (DBObject item : slurpedCollection.find(update, findKeys)) {
  713:                 addToStream(operation, currentTimestamp, item, collection);
  714:             } <== AFFECTED
  715:         }
  716:     }
  717:  <== AFFECTED
    }
}
