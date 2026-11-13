class SlimTableFactory {
    void SlimTableFactory__() {
    addTableType("ordered query:", OrderedQueryTable.class);
    addTableType("subset query:", SubsetQueryTable.class);
    addTableType("query:", QueryTable.class);
    addTableType("table:", TableTable.class);
    addTableType("script", ScriptTable.class);
    addTableType("scenario", ScenarioTable.class);
    addTableType("import", ImportTable.class);
    addTableType("library", LibraryTable.class);
    }
}
