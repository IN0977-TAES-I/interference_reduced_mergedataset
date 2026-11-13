// class name : org.opentripplanner.routing.spt.MultiShortestPathTree
// method name: dominates(State, State)
// side       : merge
// region     : lines 123-152 (affected lines: [123, 141, 144, 146, 147, 148, 149, 152])
class MultiShortestPathTree {
    void dominates_State__State_() {
  123:         if (thisState.isBikeParked() != other.isBikeParked()) <== AFFECTED
  124:             return false;
  125: 
  126:         if (thisState.backEdge != other.getBackEdge() && ((thisState.backEdge instanceof StreetEdge)
  127:                 && (!((StreetEdge) thisState.backEdge).getTurnRestrictions().isEmpty())))
  128:             return false;
  129: 
  130:         if (thisState.routeSequenceSubset(other)) {
  131:             // TODO subset is not really the right idea
  132:             return thisState.weight <= other.weight &&
  133:             		thisState.getElapsedTimeSeconds() <= other.getElapsedTimeSeconds();
  134:             // && this.getNumBoardings() <= other.getNumBoardings();
  135:         }
  136: 
  137:         // If returning more than one result from GenericAStar, the search can be very slow
  138:         // unless you replace the following code with:
  139:         // return false;
  140:         
  141:         boolean walkDistanceIsHopeful = thisState.walkDistance / other.getWalkDistance() < 1+WALK_DIST_EPSILON; <== AFFECTED
  142:         
  143:         double weightRatio = thisState.weight / other.weight;
  144:         boolean weightIsHopeful = (weightRatio < 1+WEIGHT_EPSILON && thisState.weight - other.weight < WEIGHT_DIFF_MARGIN); <== AFFECTED
  145:         
  146:         double t1 = (double)thisState.getElapsedTimeSeconds(); <== AFFECTED
  147:         double t2 = (double)other.getElapsedTimeSeconds(); <== AFFECTED
  148:         double timeRatio = t1/t2; <== AFFECTED
  149:         boolean timeIsHopeful = (timeRatio < 1+TIME_EPSILON) && (t1 - t2 <= TIME_DIFF_MARGIN); <== AFFECTED
  150:         
  151:         // only dominate if everything is at least hopeful
  152:         return walkDistanceIsHopeful && weightIsHopeful && timeIsHopeful; <== AFFECTED
    }
}
