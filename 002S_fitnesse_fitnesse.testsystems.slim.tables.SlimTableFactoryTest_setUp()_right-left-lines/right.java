// class name : fitnesse.testsystems.slim.tables.SlimTableFactoryTest
// method name: setUp()
// side       : right
// region     : lines 31-38 (affected lines: [31, 32, 38])
class SlimTableFactoryTest {
    void setUp__() {
   31:     addTableType("import", ImportTable.class); <== AFFECTED
   32:     addTableType("library", LibraryTable.class); <== AFFECTED
   33:   }
   34: 
   35:   protected SlimTableFactory(HashMap<String, Class<? extends SlimTable>> tableTypes, HashMap<String, String> tableTypeArrays) {
   36:     this.tableTypes = tableTypes;
   37:     this.tableTypeArrays = tableTypeArrays;
   38:   } <== AFFECTED
    }
}
