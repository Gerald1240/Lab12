package graphlink;

public class Edge<E> {
    Vertex<E> refDest;
    private int weight;
    
    public Edge(Vertex<E> refDest) {
        this(refDest, -1);
    }
    
    public Edge(Vertex<E> refDest, int weight) {
        this.setRefDest(refDest);
        this.weight = weight;
    }
    
    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<E> e = (Edge<E>) o;
            return this.getRefDest().equals(e.getRefDest());
        }
        return false;
    }
    
    public String toString() {
        if (this.weight > -1) {
            return getRefDest().getData() + " [" + this.weight + "]";
        } else {
            return getRefDest().getData().toString();
        }
    }

	public Vertex<E> getRefDest() {
		return refDest;
	}

	public void setRefDest(Vertex<E> refDest) {
		this.refDest = refDest;
	}
}

