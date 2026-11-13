class SlangImpl {
    void getAllEventTypes__() {
        eventTypes.add(ScoreLangConstants.EVENT_TASK_START);
        eventTypes.add(ScoreLangConstants.EVENT_INPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_INPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_BRANCH_START);
        eventTypes.add(ScoreLangConstants.EVENT_BRANCH_END);
        eventTypes.add(ScoreLangConstants.EVENT_SPLIT_BRANCHES);
        eventTypes.add(ScoreLangConstants.EVENT_JOIN_BRANCHES_START);
        eventTypes.add(ScoreLangConstants.EVENT_JOIN_BRANCHES_END);
    }
}
