package graphlink;

public class GraphLink<E>{
	protected ListLinked<Vertex<E>> listVertex;
	
	public GraphLink() {
		listVertex = new ListLinked<Vertex<E>>();
	}
	public void insertVertex ( E data) {
		//implementar este metodo
	}
	public void insertEdge (E ver0ri, E verDes) {
		//implementar este metodo
	}
	
	public String toString() {
		return this.listVertex.toString();
	}
}
