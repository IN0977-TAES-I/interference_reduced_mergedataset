// class name : org.elasticsearch.river.mongodb.MongoDBRiverDefinition
// method name: parseSettings(String, String, RiverSettings, ScriptService)
// side       : merge
// region     : lines 490-717 (affected lines: [490, 515, 576, 582, 617, 635, 670, 683, 686, 699, 703, 714, 717])
class MongoDBRiverDefinition {
    void parseSettings_String__String__RiverSettings__ScriptService_() {
  490:             } <== AFFECTED
  491:         }
  492:         return oplogTimestamp;
  493:     }
  494: 
  495:     @SuppressWarnings("unchecked")
  496:     private void flattenOps(DBObject entry) {
  497:         Object ref = entry.removeField(MongoDBRiver.OPLOG_REF);
  498:         Object ops = ref == null ? entry.removeField(MongoDBRiver.OPLOG_OPS) : getRefOps(ref);
  499:         if (ops != null) {
  500:             try {
  501:                 for (DBObject op : (List<DBObject>) ops) {
  502:                     String operation = (String) op.get(MongoDBRiver.OPLOG_OPERATION);
  503:                     if (operation.equals(MongoDBRiver.OPLOG_COMMAND_OPERATION)) {
  504:                         DBObject object = (DBObject) op.get(MongoDBRiver.OPLOG_OBJECT);
  505:                         if (object.containsField(MongoDBRiver.OPLOG_CREATE_COMMAND)) {
  506:                             continue;
  507:                         }
  508:                     }
  509:                     entry.putAll(op);
  510:                 }
  511:             } catch (ClassCastException e) {
  512:                 logger.error(e.toString(), e);
  513:             }
  514:         }
  515:     } <== AFFECTED
  516: 
  517:     private Object getRefOps(Object ref) {
  518:         // db.oplog.refs.find({_id: {$gte: {oid: %ref%}}}).limit(1)
  519:         DBObject query = new BasicDBObject(MongoDBRiver.MONGODB_ID_FIELD, new BasicDBObject(QueryOperators.GTE,
  520:                 new BasicDBObject(MongoDBRiver.MONGODB_OID_FIELD, ref)));
  521:         DBObject oplog = oplogRefsCollection.findOne(query);
  522:         return oplog == null ? null : oplog.get("ops");
  523:     }
  524: 
  525:     private void processAdminCommandOplogEntry(final DBObject entry, final Timestamp<?> startTimestamp) throws InterruptedException {
  526:         if (logger.isTraceEnabled()) {
  527:             logger.trace("processAdminCommandOplogEntry - [{}]", entry);
  528:         }
  529:         DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
  530:         if (definition.isImportAllCollections()) {
  531:             if (object.containsField(MongoDBRiver.OPLOG_RENAME_COLLECTION_COMMAND_OPERATION) && object.containsField(MongoDBRiver.OPLOG_TO)) {
  532:                 String to = object.get(MongoDBRiver.OPLOG_TO).toString();
  533:                 if (to.startsWith(definition.getMongoDb())) {
  534:                     String newCollection = getCollectionFromNamespace(to);
  535:                     DBCollection coll = slurpedDb.getCollection(newCollection);
  536:                     doInitialImport(coll);
  537:                 }
  538:             }
  539:         }
  540:     }
  541: 
  542:     private String getCollectionFromNamespace(String namespace) {
  543:         if (namespace.startsWith(definition.getMongoDb()) && CharMatcher.is('.').countIn(namespace) == 1) {
  544:             return namespace.substring(definition.getMongoDb().length() + 1);
  545:         }
  546:         logger.error("Cannot get collection from namespace [{}]", namespace);
  547:         return null;
  548:     }
  549: 
  550:     private boolean isValidOplogEntry(final DBObject entry, final Timestamp<?> startTimestamp) {
  551:         if (!entry.containsField(MongoDBRiver.OPLOG_OPERATION)) {
  552:             logger.trace("[Empty Oplog Entry] - can be ignored. {}", JSONSerializers.getStrict().serialize(entry));
  553:             return false;
  554:         }
  555:         if (MongoDBRiver.OPLOG_NOOP_OPERATION.equals(entry.get(MongoDBRiver.OPLOG_OPERATION))) {
  556:             logger.trace("[No-op Oplog Entry] - can be ignored. {}", JSONSerializers.getStrict().serialize(entry));
  557:             return false;
  558:         }
  559:         String namespace = (String) entry.get(MongoDBRiver.OPLOG_NAMESPACE);
  560:         // Initial support for sharded collection -
  561:         // https://jira.mongodb.org/browse/SERVER-4333
  562:         // Not interested in operation from migration or sharding
  563:         if (entry.containsField(MongoDBRiver.OPLOG_FROM_MIGRATE) && ((BasicBSONObject) entry).getBoolean(MongoDBRiver.OPLOG_FROM_MIGRATE)) {
  564:             logger.trace("[Invalid Oplog Entry] - from migration or sharding operation. Can be ignored. {}", JSONSerializers.getStrict().serialize(entry));
  565:             return false;
  566:         }
  567:         // Not interested by chunks - skip all
  568:         if (namespace.endsWith(MongoDBRiver.GRIDFS_CHUNKS_SUFFIX)) {
  569:             return false;
  570:         }
  571: 
  572:         if (startTimestamp != null) {
  573:             Timestamp<?> oplogTimestamp = Timestamp.on(entry);
  574:             if (Timestamp.compare(oplogTimestamp, startTimestamp) < 0) {
  575:                 logger.error("[Invalid Oplog Entry] - entry timestamp [{}] before startTimestamp [{}]",
  576:                         JSONSerializers.getStrict().serialize(entry), startTimestamp); <== AFFECTED
  577:                 return false;
  578:             }
  579:         }
  580: 
  581:         boolean validNamespace = false;
  582:         if (definition.isMongoGridFS()) { <== AFFECTED
  583:             validNamespace = gridfsOplogNamespace.equals(namespace);
  584:         } else {
  585:             if (definition.isImportAllCollections()) {
  586:                 // Skip temp entry generated by map / reduce
  587:                 if (namespace.startsWith(definition.getMongoDb()) && !namespace.startsWith(definition.getMongoDb() + ".tmp.mr")) {
  588:                     validNamespace = true;
  589:                 }
  590:             } else {
  591:                 if (definition.getMongoOplogNamespace().equals(namespace)) {
  592:                     validNamespace = true;
  593:                 }
  594:             }
  595:             if (cmdOplogNamespace.equals(namespace)) {
  596:                 validNamespace = true;
  597:             }
  598: 
  599:             if (MongoDBRiver.OPLOG_ADMIN_COMMAND.equals(namespace)) {
  600:                 validNamespace = true;
  601:             }
  602:         }
  603:         if (!validNamespace) {
  604:             logger.trace("[Invalid Oplog Entry] - namespace [{}] is not valid", namespace);
  605:             return false;
  606:         }
  607:         String operation = (String) entry.get(MongoDBRiver.OPLOG_OPERATION);
  608:         if (!oplogOperations.contains(operation)) {
  609:             logger.trace("[Invalid Oplog Entry] - operation [{}] is not valid", operation);
  610:             return false;
  611:         }
  612: 
  613:         // TODO: implement a better solution
  614:         if (definition.getMongoOplogFilter() != null) {
  615:             DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
  616:             BasicDBObject filter = definition.getMongoOplogFilter();
  617:             if (!filterMatch(filter, object)) { <== AFFECTED
  618:                 logger.trace("[Invalid Oplog Entry] - filter [{}] does not match object [{}]", filter, object);
  619:                 return false;
  620:             }
  621:         }
  622:         return true;
  623:     }
  624: 
  625:     private boolean filterMatch(DBObject filter, DBObject object) {
  626:         for (String key : filter.keySet()) {
  627:             if (!object.containsField(key)) {
  628:                 return false;
  629:             }
  630:             if (!filter.get(key).equals(object.get(key))) {
  631:                 return false;
  632:             }
  633:         }
  634:         return true;
  635:     } <== AFFECTED
  636: 
  637:     private DBObject applyFieldFilter(DBObject object) {
  638:         if (object instanceof GridFSFile) {
  639:             GridFSFile file = (GridFSFile) object;
  640:             DBObject metadata = file.getMetaData();
  641:             if (metadata != null) {
  642:                 file.setMetaData(applyFieldFilter(metadata));
  643:             }
  644:         } else {
  645:             object = MongoDBHelper.applyExcludeFields(object, definition.getExcludeFields());
  646:             object = MongoDBHelper.applyIncludeFields(object, definition.getIncludeFields());
  647:         }
  648:         return object;
  649:     }
  650: 
  651:     /*
  652:      * Extract "_id" from "o" if it fails try to extract from "o2"
  653:      */
  654:     private String getObjectIdFromOplogEntry(DBObject entry) {
  655:         if (entry.containsField(MongoDBRiver.OPLOG_OBJECT)) {
  656:             DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_OBJECT);
  657:             if (object.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
  658:                 return object.get(MongoDBRiver.MONGODB_ID_FIELD).toString();
  659:             }
  660:         }
  661:         if (entry.containsField(MongoDBRiver.OPLOG_UPDATE)) {
  662:             DBObject object = (DBObject) entry.get(MongoDBRiver.OPLOG_UPDATE);
  663:             if (object.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
  664:                 return object.get(MongoDBRiver.MONGODB_ID_FIELD).toString();
  665:             }
  666:         }
  667:         return null;
  668:     }
  669: 
  670:     private DBCursor oplogCursor(final Timestamp<?> timestampOverride) throws SlurperException { <== AFFECTED
  671:         Timestamp<?> time = timestampOverride == null ? MongoDBRiver.getLastTimestamp(client, definition) : timestampOverride;
  672:         if (time == null) {
  673:             return null;
  674:         }
  675:         DBObject indexFilter = time.getOplogFilter();
  676:         if (indexFilter == null) {
  677:             return null;
  678:         }
  679: 
  680:         int options = Bytes.QUERYOPTION_TAILABLE | Bytes.QUERYOPTION_AWAITDATA | Bytes.QUERYOPTION_NOTIMEOUT
  681:         // Using OPLOGREPLAY to improve performance:
  682:         // https://jira.mongodb.org/browse/JAVA-771
  683:                 | Bytes.QUERYOPTION_OPLOGREPLAY; <== AFFECTED
  684: 
  685:         DBCursor cursor = oplogCollection.find(indexFilter).setOptions(options);
  686:  <== AFFECTED
  687:         // Toku sometimes gets stuck without this hint:
  688:         if (indexFilter.containsField(MongoDBRiver.MONGODB_ID_FIELD)) {
  689:             cursor = cursor.hint("_id_");
  690:         }
  691:         isRiverStale(cursor, time);
  692:         return cursor;
  693:     }
  694: 
  695:     private void isRiverStale(DBCursor cursor, Timestamp<?> time) throws SlurperException {
  696:         if (cursor == null || time == null) {
  697:             return;
  698:         }
  699:         if (definition.getInitialTimestamp() != null && time.equals(definition.getInitialTimestamp())) { <== AFFECTED
  700:             return;
  701:         }
  702:         DBObject entry = cursor.next();
  703:         Timestamp<?> oplogTimestamp = Timestamp.on(entry); <== AFFECTED
  704:         if (!time.equals(oplogTimestamp)) {
  705:             MongoDBRiverHelper.setRiverStatus(client, definition.getRiverName(), Status.RIVER_STALE);
  706:             throw new SlurperException("River out of sync with oplog.rs collection");
  707:         }
  708:     }
  709: 
  710:     private void addQueryToStream(final Operation operation, final Timestamp<?> currentTimestamp, final DBObject update,
  711:             final String collection) throws InterruptedException {
  712:         if (logger.isTraceEnabled()) {
  713:             logger.trace("addQueryToStream - operation [{}], currentTimestamp [{}], update [{}]", operation, currentTimestamp, update);
  714:         } <== AFFECTED
  715: 
  716:         if (collection == null) {
  717:             for (String name : slurpedDb.getCollectionNames()) { <== AFFECTED
    }
}
