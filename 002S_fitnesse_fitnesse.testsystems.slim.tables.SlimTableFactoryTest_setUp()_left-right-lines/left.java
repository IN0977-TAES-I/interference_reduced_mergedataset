// class name : fitnesse.testsystems.slim.tables.SlimTableFactoryTest
// method name: setUp()
// side       : left
// region     : lines 31-38 (affected lines: [31, 32, 38])
class SlimTableFactoryTest {
    void setUp__() {
   31:     addTableType("scenario", ScenarioTable.class); <== AFFECTED
   32:     addTableType("import", ImportTable.class); <== AFFECTED
   33:     addTableType("library", LibraryTable.class);
   34:   }
   35: 
   36:   protected SlimTableFactory(HashMap<String, Class<? extends SlimTable>> tableTypes, HashMap<String, String> tableTypeArrays) {
   37:     this.tableTypes = tableTypes;
   38:     this.tableTypeArrays = tableTypeArrays; <== AFFECTED
    }
}
