// class name : org.opentripplanner.routing.spt.MultiShortestPathTree
// method name: dominates(State, State)
// side       : left
// region     : lines 123-152 (affected lines: [123, 141, 144, 146, 147, 148, 149, 152])
class MultiShortestPathTree {
    void dominates_State__State_() {
  123: //    	return this.weight < other.weight; <== AFFECTED
  124: 	}
  125: 
  126: 	@Override
  127:     public State getState(Vertex dest) {
  128:         Collection<State> states = stateSets.get(dest);
  129:         if (states == null)
  130:             return null;
  131:         State ret = null;
  132:         for (State s : states) {
  133:             if ((ret == null || s.betterThan(ret)) && s.isFinal() && s.allPathParsersAccept()) {
  134:                 ret = s;
  135:             }
  136:         }
  137:         return ret;
  138:     }
  139: 
  140:     @Override
  141:     public List<State> getStates(Vertex dest) { <== AFFECTED
  142:         return stateSets.get(dest);
  143:     }
  144:  <== AFFECTED
  145:     @Override
  146:     public int getVertexCount() { <== AFFECTED
  147:         return stateSets.keySet().size(); <== AFFECTED
  148:     } <== AFFECTED
  149:  <== AFFECTED
  150:     /**
  151:      * Check that a state coming out of the queue is still in the Pareto-optimal set for this vertex, 
  152:      * which indicates that it has not been ruled out as a state on an optimal path. Many shortest  <== AFFECTED
    }
}
