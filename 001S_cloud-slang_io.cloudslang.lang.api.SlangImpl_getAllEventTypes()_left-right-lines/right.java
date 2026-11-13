// class name : io.cloudslang.lang.api.SlangImpl
// method name: getAllEventTypes()
// side       : right
// region     : lines 116-125 (affected lines: [116, 123, 124, 125])
class SlangImpl {
    void getAllEventTypes__() {
  116:         eventTypes.add(ScoreLangConstants.EVENT_INPUT_START); <== AFFECTED
  117:         eventTypes.add(ScoreLangConstants.EVENT_INPUT_END);
  118:         eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_START);
  119:         eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_END);
  120:         eventTypes.add(ScoreLangConstants.EVENT_BRANCH_START);
  121:         eventTypes.add(ScoreLangConstants.EVENT_BRANCH_END);
  122:         eventTypes.add(ScoreLangConstants.EVENT_SPLIT_BRANCHES);
  123:         eventTypes.add(ScoreLangConstants.EVENT_JOIN_BRANCHES_START); <== AFFECTED
  124:         eventTypes.add(ScoreLangConstants.EVENT_JOIN_BRANCHES_END); <== AFFECTED
  125:         eventTypes.add(ScoreLangConstants.EVENT_EXECUTION_FINISHED); <== AFFECTED
    }
}
