// class name : fitnesse.testsystems.slim.tables.SlimTableFactoryTest
// method name: setUp()
// side       : base
// region     : lines 31-38 (affected lines: [31, 32, 38])
class SlimTableFactoryTest {
    void setUp__() {
   31:     addTableType("library", LibraryTable.class); <== AFFECTED
   32:   } <== AFFECTED
   33: 
   34:   protected SlimTableFactory(HashMap<String, Class<? extends SlimTable>> tableTypes, HashMap<String, String> tableTypeArrays) {
   35:     this.tableTypes = tableTypes;
   36:     this.tableTypeArrays = tableTypeArrays;
   37:   }
   38:  <== AFFECTED
    }
}
