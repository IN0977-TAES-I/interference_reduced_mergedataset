class SlimTableFactory {
    void SlimTableFactory__() {
    addTableType("ddt:", DynamicDecisionTable.class);
    addTableType("dynamic decision:", DynamicDecisionTable.class);
    addTableType("ordered query:", OrderedQueryTable.class);
    addTableType("subset query:", SubsetQueryTable.class);
    addTableType("query:", QueryTable.class);
    addTableType("table:", TableTable.class);
    addTableType("script", ScriptTable.class);
    addTableType("script:", ScriptTable.class);
    }
}
