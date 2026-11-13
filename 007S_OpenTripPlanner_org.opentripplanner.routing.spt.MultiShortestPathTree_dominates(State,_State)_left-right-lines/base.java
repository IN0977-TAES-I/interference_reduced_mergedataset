// class name : org.opentripplanner.routing.spt.MultiShortestPathTree
// method name: dominates(State, State)
// side       : base
// region     : lines 123-152 (affected lines: [123, 141, 144, 146, 147, 148, 149, 152])
class MultiShortestPathTree {
    void dominates_State__State_() {
  123:         } <== AFFECTED
  124:         return ret;
  125:     }
  126: 
  127:     @Override
  128:     public List<State> getStates(Vertex dest) {
  129:         return stateSets.get(dest);
  130:     }
  131: 
  132:     @Override
  133:     public int getVertexCount() {
  134:         return stateSets.keySet().size();
  135:     }
  136: 
  137:     /**
  138:      * Check that a state coming out of the queue is still in the Pareto-optimal set for this vertex, 
  139:      * which indicates that it has not been ruled out as a state on an optimal path. Many shortest 
  140:      * path algorithms will decrease the key of an entry in the priority queue when it is updated, or
  141:      * remove it when it is dominated. <== AFFECTED
  142:      * 
  143:      * When the Fibonacci heap was replaced with a binary heap, the decrease-key operation was 
  144:      * removed for the same reason: both improve theoretical run time complexity, at the cost of  <== AFFECTED
  145:      * high constant factors and more complex code.
  146:      *  <== AFFECTED
  147:      * So there can be dominated (useless) states in the queue. When they come out we want to  <== AFFECTED
  148:      * ignore them rather than spend time branching out from them. <== AFFECTED
  149:      */ <== AFFECTED
  150:     @Override
  151:     public boolean visit(State state) {
  152:         boolean ret = false; <== AFFECTED
    }
}
