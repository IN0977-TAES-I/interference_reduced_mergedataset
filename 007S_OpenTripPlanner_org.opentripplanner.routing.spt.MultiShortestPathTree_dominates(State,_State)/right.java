class MultiShortestPathTree {
    void dominates_State__State_() {

        if (thisState.routeSequenceSubset(other)) {
            return thisState.weight <= other.weight &&
            		thisState.getElapsedTimeSeconds() <= other.getElapsedTimeSeconds();
        }

        boolean walkDistanceBetter = thisState.walkDistance <= other.getWalkDistance() * 1.05;
        double weightRatio = thisState.weight / other.weight;
        boolean weightBetter = (weightRatio < 1.02 && thisState.weight - other.weight < 30);
        boolean timeBetter = thisState.getElapsedTimeSeconds() - other.getElapsedTimeSeconds() <= 30;

        return walkDistanceBetter && weightBetter && timeBetter;
	}

	@Override
    public State getState(Vertex dest) {
        Collection<State> states = stateSets.get(dest);
        if (states == null)
            return null;
        State ret = null;
        for (State s : states) {
            if ((ret == null || s.betterThan(ret)) && s.isFinal() && s.allPathParsersAccept()) {
                ret = s;
            }
    }
}
