package graphlink;

public class Vertex<E> {
    private E data;
    protected ListLinked<Edge<E>> listAdj;
    protected boolean visited; // Add visited field
    
    public Vertex(E data) {
        this.data = data;
        listAdj = new ListLinked<Edge<E>>();
    }
    
    public E getData() {
        return data;
    }
    
    public boolean equals(Object o) {
        if (o instanceof Vertex<?>) {
            Vertex<?> v = (Vertex<?>) o;
            return this.data.equals(v.data);
        }
        return false;
    }
    
    public String toString() {
        return data.toString();
    }
}
