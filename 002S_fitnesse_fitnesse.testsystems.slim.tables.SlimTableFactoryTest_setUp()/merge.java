class SlimTableFactoryTest {
    void setUp__() {
    addTableType("script:", ScriptTable.class);
    addTableType("scenario", ScenarioTable.class);
    addTableType("import", ImportTable.class);
    addTableType("library", LibraryTable.class);
  }

  protected SlimTableFactory(HashMap<String, Class<? extends SlimTable>> tableTypes, HashMap<String, String> tableTypeArrays) {
    this.tableTypes = tableTypes;
    }
}
