// class name : fitnesse.testsystems.slim.tables.SlimTableFactoryTest
// method name: setUp()
// side       : merge
// region     : lines 31-38 (affected lines: [31, 32, 38])
class SlimTableFactoryTest {
    void setUp__() {
   31:     addTableType("script:", ScriptTable.class); <== AFFECTED
   32:     addTableType("scenario", ScenarioTable.class); <== AFFECTED
   33:     addTableType("import", ImportTable.class);
   34:     addTableType("library", LibraryTable.class);
   35:   }
   36: 
   37:   protected SlimTableFactory(HashMap<String, Class<? extends SlimTable>> tableTypes, HashMap<String, String> tableTypeArrays) {
   38:     this.tableTypes = tableTypes; <== AFFECTED
    }
}
