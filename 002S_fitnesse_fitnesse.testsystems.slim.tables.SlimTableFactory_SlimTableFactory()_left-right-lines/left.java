// class name : fitnesse.testsystems.slim.tables.SlimTableFactory
// method name: SlimTableFactory()
// side       : left
// region     : lines 24-31 (affected lines: [24, 25, 31])
class SlimTableFactory {
    void SlimTableFactory__() {
   24:     addTableType("ddt:", DynamicDecisionTable.class); <== AFFECTED
   25:     addTableType("dynamic decision:", DynamicDecisionTable.class); <== AFFECTED
   26:     addTableType("ordered query:", OrderedQueryTable.class);
   27:     addTableType("subset query:", SubsetQueryTable.class);
   28:     addTableType("query:", QueryTable.class);
   29:     addTableType("table:", TableTable.class);
   30:     addTableType("script", ScriptTable.class);
   31:     addTableType("scenario", ScenarioTable.class); <== AFFECTED
    }
}
