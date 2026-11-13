



class MultiShortestPathTree {
    void dominates_State__State_() {

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
        return ret;
    }

    @Override
    public List<State> getStates(Vertex dest) {
        return stateSets.get(dest);
    }

    @Override
    public int getVertexCount() {
        return stateSets.keySet().size();
    }

