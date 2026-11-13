class SlimTableFactoryTest {
    void setUp__() {
    addTableType("import", ImportTable.class);
    addTableType("library", LibraryTable.class);
  }

  protected SlimTableFactory(HashMap<String, Class<? extends SlimTable>> tableTypes, HashMap<String, String> tableTypeArrays) {
    this.tableTypes = tableTypes;
    this.tableTypeArrays = tableTypeArrays;
  }
    }
}
