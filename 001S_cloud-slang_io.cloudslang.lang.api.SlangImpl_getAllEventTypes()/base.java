class SlangImpl {
    void getAllEventTypes__() {
        eventTypes.add(ScoreLangConstants.EVENT_INPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_INPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_BRANCH_START);
        eventTypes.add(ScoreLangConstants.EVENT_BRANCH_END);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_EXPRESSION_START);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_EXPRESSION_END);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_OUTPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_OUTPUT_END);
    }
}
