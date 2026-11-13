// class name : io.cloudslang.lang.api.SlangImpl
// method name: getAllEventTypes()
// side       : merge
// region     : lines 116-125 (affected lines: [116, 123, 124, 125])
class SlangImpl {
    void getAllEventTypes__() {
  116:         eventTypes.add(ScoreLangConstants.EVENT_TASK_START); <== AFFECTED
  117:         eventTypes.add(ScoreLangConstants.EVENT_INPUT_START);
  118:         eventTypes.add(ScoreLangConstants.EVENT_INPUT_END);
  119:         eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_START);
  120:         eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_END);
  121:         eventTypes.add(ScoreLangConstants.EVENT_BRANCH_START);
  122:         eventTypes.add(ScoreLangConstants.EVENT_BRANCH_END);
  123:         eventTypes.add(ScoreLangConstants.EVENT_SPLIT_BRANCHES); <== AFFECTED
  124:         eventTypes.add(ScoreLangConstants.EVENT_JOIN_BRANCHES_START); <== AFFECTED
  125:         eventTypes.add(ScoreLangConstants.EVENT_JOIN_BRANCHES_END); <== AFFECTED
    }
}
