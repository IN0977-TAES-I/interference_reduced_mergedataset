// class name : fitnesse.testsystems.slim.tables.SlimTableFactory
// method name: SlimTableFactory()
// side       : base
// region     : lines 24-31 (affected lines: [24, 25, 31])
class SlimTableFactory {
    void SlimTableFactory__() {
   24:     addTableType("ordered query:", OrderedQueryTable.class); <== AFFECTED
   25:     addTableType("subset query:", SubsetQueryTable.class); <== AFFECTED
   26:     addTableType("query:", QueryTable.class);
   27:     addTableType("table:", TableTable.class);
   28:     addTableType("script", ScriptTable.class);
   29:     addTableType("scenario", ScenarioTable.class);
   30:     addTableType("import", ImportTable.class);
   31:     addTableType("library", LibraryTable.class); <== AFFECTED
    }
}
