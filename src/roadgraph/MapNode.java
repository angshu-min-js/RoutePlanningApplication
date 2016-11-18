package roadgraph;
import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

class MapNode implements Comparable
{
	/** The list of edges out of this node */
	private HashSet<MapEdge> edges;
		
	/** the latitude and longitude of this node */
	private GeographicPoint location;
	
	// Scores for priority queue
	private double actualScore;
	private double score;
		
	/** 
	 * Create a new MapNode at a given Geographic location
	 * @param loc the location of this node
	 */
	MapNode(GeographicPoint loc)
	{
		location = loc;
		edges = new HashSet<MapEdge>();
		actualScore = 0;
		score = 0;
	}
		
	/**
	 * Add an edge that is outgoing from this node in the graph
	 * @param edge The edge to be added
	 */
	void addEdge(MapEdge edge)
	{
		edges.add(edge);
	}
	
	/**  
	 * Return the neighbors of this MapNode 
	 * @return a set containing all the neighbors of this node
	 */
	Set<MapNode> getNeighbors()
	{
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}
	
	/**  
	 * Getter and Setter of  Scores and Actual Scores
	 * @return scores
	 */
	// Get the score from the Node to the start Node.
	public double getActualScore() {
		return actualScore; // for A*
	}
	
	// Get the estimated score from the Node to the goal Node.
	public double getScore() {
		return score;
	}
	
	// Set the score from the Node to the start Node.
	public void setActualScore(double score) {
		actualScore = score; // for A*
	}
	
	// Set the estimated score from the Node to the goal Node.
	public void setScore(double score) {
		this.score = score;
	}
	
	//Queue will need to implement the interface Comparable
	@Override
	public int compareTo(Object o) {
		MapNode node = (MapNode)o;
		return ((Double)this.getScore()).compareTo((Double)node.getScore());
	}
	
	/**
	 * Get the geographic location that this node represents
	 * @return the geographic location of this node
	 */
	GeographicPoint getLocation()
	{
		return location;
	}
	
	/**
	 * return the edges out of this node
	 * @return a set contianing all the edges out of this node.
	 */
	Set<MapEdge> getEdges()
	{
		return edges;
	}
	
	/** Returns whether two nodes are equal.
	 * Nodes are considered equal if their locations are the same, 
	 * even if their street list is different.
	 * @param o the node to compare to
	 * @return true if these nodes are at the same location, false otherwise
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}
		MapNode node = (MapNode)o;
		return node.location.equals(this.location);
	}
	
	/** Because we compare nodes using their location, we also 
	 * may use their location for HashCode.
	 * @return The HashCode for this node, which is the HashCode for the 
	 * underlying point
	 */
	@Override
	public int hashCode()
	{
		return location.hashCode();
	}
	
	/** ToString to print out a MapNode object
	 *  @return the string representation of a MapNode
	 */
	@Override
	public String toString()
	{
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " intersects streets: ";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}
	
	// For debugging, output roadNames as a String.
	public String roadNamesAsString()
	{
		String toReturn = "(";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += ")";
		return toReturn;
	}
	
	public double getDistanceTo(MapNode goalNode) {
		return getLocation().distance(goalNode.getLocation());
	}

}