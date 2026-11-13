// class name : org.opentripplanner.routing.spt.MultiShortestPathTree
// method name: dominates(State, State)
// side       : right
// region     : lines 123-152 (affected lines: [123, 141, 144, 146, 147, 148, 149, 152])
class MultiShortestPathTree {
    void dominates_State__State_() {
  123:  <== AFFECTED
  124:         if (thisState.routeSequenceSubset(other)) {
  125:             // TODO subset is not really the right idea
  126:             return thisState.weight <= other.weight &&
  127:             		thisState.getElapsedTimeSeconds() <= other.getElapsedTimeSeconds();
  128:             // && this.getNumBoardings() <= other.getNumBoardings();
  129:         }
  130: 
  131:         // If returning more than one result from GenericAStar, the search can be very slow
  132:         // unless you replace the following code with:
  133:         // return false;
  134:         boolean walkDistanceBetter = thisState.walkDistance <= other.getWalkDistance() * 1.05;
  135:         double weightRatio = thisState.weight / other.weight;
  136:         boolean weightBetter = (weightRatio < 1.02 && thisState.weight - other.weight < 30);
  137:         boolean timeBetter = thisState.getElapsedTimeSeconds() - other.getElapsedTimeSeconds() <= 30;
  138:         
  139:         return walkDistanceBetter && weightBetter && timeBetter;
  140: //    	return this.weight < other.weight;
  141: 	} <== AFFECTED
  142: 
  143: 	@Override
  144:     public State getState(Vertex dest) { <== AFFECTED
  145:         Collection<State> states = stateSets.get(dest);
  146:         if (states == null) <== AFFECTED
  147:             return null; <== AFFECTED
  148:         State ret = null; <== AFFECTED
  149:         for (State s : states) { <== AFFECTED
  150:             if ((ret == null || s.betterThan(ret)) && s.isFinal() && s.allPathParsersAccept()) {
  151:                 ret = s;
  152:             } <== AFFECTED
    }
}
