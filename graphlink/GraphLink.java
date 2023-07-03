package graphlink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class GraphLink<E> {
    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<Vertex<E>>();
    }

    public void insertVertex(E data) {
        Vertex<E> newVertex = new Vertex<E>(data);
        listVertex.insertLast(newVertex);
    }

    public void insertEdge(E verSrc, E verDest) {
        Vertex<E> source = findVertex(verSrc);
        Vertex<E> destination = findVertex(verDest);
        
        if (source != null && destination != null) {
            Edge<E> newEdge = new Edge<E>(destination);
            source.listAdj.insertLast(newEdge);
        }
    }

    private Vertex<E> findVertex(E data) {
        Iterator<Vertex<E>> iterator = listVertex.iterator();
        while (iterator.hasNext()) {
            Vertex<E> vertex = iterator.next();
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }
    public boolean searchVertex(E data) {
        Vertex<E> vertex = findVertex(data);
        return vertex != null;
    }
    
    public boolean searchEdge(E v, E z) {
        Vertex<E> vertexV = findVertex(v);
        Vertex<E> vertexZ = findVertex(z);
        
        if (vertexV != null && vertexZ != null) {
            Edge<E> edge = findEdge(vertexV, vertexZ);
            return edge != null;
        }
        
        return false;
    }

    private Edge<E> findEdge(Vertex<E> vertexV, Vertex<E> vertexZ) {
        ListLinked<Edge<E>> listAdj = vertexV.listAdj;
        Iterator<Edge<E>> iterator = listAdj.iterator();
        
        while (iterator.hasNext()) {
            Edge<E> edge = iterator.next();
            if (edge.getRefDest().equals(vertexZ)) {
                return edge;
            }
        }
        
        return null;
    }
    public void removeVertex(E data) {
        Vertex<E> vertexToRemove = findVertex(data);
        
        if (vertexToRemove != null) {
            // Eliminar las aristas de entrada del vértice
            removeEdgesToVertex(vertexToRemove);
            
            // Eliminar las aristas de salida del vértice
            removeEdgesFromVertex(vertexToRemove);
            
            // Eliminar el vértice de la lista de vértices
            listVertex.remove(vertexToRemove);
        }
    }

    private void removeEdgesToVertex(Vertex<E> vertex) {
        Iterator<Vertex<E>> iterator = listVertex.iterator();
        
        while (iterator.hasNext()) {
            Vertex<E> currentVertex = iterator.next();
            ListLinked<Edge<E>> listAdj = currentVertex.listAdj;
            Iterator<Edge<E>> edgeIterator = listAdj.iterator();
            
            while (edgeIterator.hasNext()) {
                Edge<E> edge = edgeIterator.next();
                
                if (edge.refDest.equals(vertex)) {
                    edgeIterator.remove();
                }
            }
        }
    }

    private void removeEdgesFromVertex(Vertex<E> vertex) {
        vertex.listAdj.clear();
    }
    public void removeEdge(E v, E z) {
        Vertex<E> vertexV = findVertex(v);
        Vertex<E> vertexZ = findVertex(z);
        
        if (vertexV != null && vertexZ != null) {
            removeEdgeFromVertex(vertexV, vertexZ);
            removeEdgeFromVertex(vertexZ, vertexV);
        }
    }

    private void removeEdgeFromVertex(Vertex<E> sourceVertex, Vertex<E> targetVertex) {
        ListLinked<Edge<E>> listAdj = sourceVertex.listAdj;
        Iterator<Edge<E>> iterator = listAdj.iterator();
        
        while (iterator.hasNext()) {
            Edge<E> edge = iterator.next();
            if (edge.refDest.equals(targetVertex)) {
                iterator.remove();
                break;
            }
        }
    }
    public void dfs(E v) {
        Vertex<E> startVertex = findVertex(v);
        
        if (startVertex != null) {
            clearVisitedFlags();
            dfsTraversal(startVertex);
        }
    }

    private void dfsTraversal(Vertex<E> vertex) {
        System.out.print(vertex.getData() + " ");
        vertex.visited = true;
        
        ListLinked<Edge<E>> listAdj = vertex.listAdj;
        Iterator<Edge<E>> iterator = listAdj.iterator();
        
        while (iterator.hasNext()) {
            Vertex<E> adjacentVertex = iterator.next().refDest;
            if (!adjacentVertex.visited) {
                dfsTraversal(adjacentVertex);
            }
        }
    }

    private void clearVisitedFlags() {
        Iterator<Vertex<E>> iterator = listVertex.iterator();
        
        while (iterator.hasNext()) {
            Vertex<E> vertex = iterator.next();
            vertex.visited = false;
        }
    }
    public void bfs(E v) {
        Vertex<E> startVertex = findVertex(v);
        
        if (startVertex != null) {
            clearVisitedFlags();
            bfsTraversal(startVertex);
        }
    }

    private void bfsTraversal(Vertex<E> startVertex) {
        Queue<Vertex<E>> queue = new LinkedList<>();
        queue.add(startVertex);
        startVertex.visited = true;
        
        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.poll();
            System.out.print(vertex.getData() + " ");
            
            ListLinked<Edge<E>> listAdj = vertex.listAdj;
            Iterator<Edge<E>> iterator = listAdj.iterator();
            
            while (iterator.hasNext()) {
                Vertex<E> adjacentVertex = iterator.next().refDest;
                if (!adjacentVertex.visited) {
                    queue.add(adjacentVertex);
                    adjacentVertex.visited = true;
                }
            }
        }
    }
    public ArrayList<E> bfsPath(E v, E z) {
        Vertex<E> startVertex = findVertex(v);
        Vertex<E> targetVertex = findVertex(z);
        
        if (startVertex != null && targetVertex != null) {
            clearVisitedFlags();
            return bfsPathTraversal(startVertex, targetVertex);
        }
        
        return new ArrayList<E>();
    }

    private ArrayList<E> bfsPathTraversal(Vertex<E> startVertex, Vertex<E> targetVertex) {
        Queue<Vertex<E>> queue = new LinkedList<>();
        queue.add(startVertex);
        startVertex.visited = true;
        
        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.poll();
            if (vertex.equals(targetVertex)) {
                return constructPath(startVertex, targetVertex);
            }
            
            ListLinked<Edge<E>> listAdj = vertex.listAdj;
            Iterator<Edge<E>> iterator = listAdj.iterator();
            
            while (iterator.hasNext()) {
                Vertex<E> adjacentVertex = iterator.next().refDest;
                if (!adjacentVertex.visited) {
                    queue.add(adjacentVertex);
                    adjacentVertex.visited = true;
                }
            }
        }
        
        return new ArrayList<E>();
    }

    private ArrayList<E> constructPath(Vertex<E> startVertex, Vertex<E> targetVertex) {
        ArrayList<E> path = new ArrayList<>();
        path.add(targetVertex.getData());
        
        Vertex<E> currentVertex = targetVertex;
        
        while (!currentVertex.equals(startVertex)) {
            ListLinked<Edge<E>> listAdj = currentVertex.listAdj;
            Iterator<Edge<E>> iterator = listAdj.iterator();
            
            while (iterator.hasNext()) {
                Vertex<E> adjacentVertex = iterator.next().refDest;
                if (adjacentVertex.visited) {
                    path.add(adjacentVertex.getData());
                    currentVertex = adjacentVertex;
                    break;
                }
            }
        }
        
        Collections.reverse(path);
        return path;
    }
    
    public void insertEdgeWeight(E v, E z, int weight) {
        Vertex<E> vertexV = findVertex(v);
        Vertex<E> vertexZ = findVertex(z);
        
        if (vertexV != null && vertexZ != null) {
            Edge<E> edge = new Edge<E>(vertexZ, weight);
            vertexV.listAdj.insertLast(edge);
            
            boolean isDirectedGraph = false;
			// Si el grafo no es dirigido, también insertamos una arista desde z hacia v con el mismo peso
            if (!isDirectedGraph) {
                Edge<E> reverseEdge = new Edge<E>(vertexV, weight);
                vertexZ.listAdj.insertLast(reverseEdge);
            }
        }
    }

    public ArrayList<E> shortPath(E v, E z) {
        Vertex<E> startVertex = findVertex(v);
        Vertex<E> targetVertex = findVertex(z);
        
        if (startVertex != null && targetVertex != null) {
            clearVisitedFlags();
            return bfsShortestPath(startVertex, targetVertex);
        }
        
        return new ArrayList<E>();
    }

    private ArrayList<E> bfsShortestPath(Vertex<E> startVertex, Vertex<E> targetVertex) {
        Queue<Vertex<E>> queue = new LinkedList<>();
        Map<Vertex<E>, Vertex<E>> previousVertices = new HashMap<>();
        
        queue.add(startVertex);
        startVertex.visited = true;
        
        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.poll();
            
            if (vertex.equals(targetVertex)) {
                return constructShortestPath(startVertex, targetVertex, previousVertices);
            }
            
            ListLinked<Edge<E>> listAdj = vertex.listAdj;
            Iterator<Edge<E>> iterator = listAdj.iterator();
            
            while (iterator.hasNext()) {
                Vertex<E> adjacentVertex = iterator.next().refDest;
                
                if (!adjacentVertex.visited) {
                    queue.add(adjacentVertex);
                    adjacentVertex.visited = true;
                    previousVertices.put(adjacentVertex, vertex);
                }
            }
        }
        
        return new ArrayList<E>();
    }

    private ArrayList<E> constructShortestPath(Vertex<E> startVertex, Vertex<E> targetVertex, Map<Vertex<E>, Vertex<E>> previousVertices) {
        ArrayList<E> shortestPath = new ArrayList<>();
        Vertex<E> currentVertex = targetVertex;
        
        while (!currentVertex.equals(startVertex)) {
            shortestPath.add(currentVertex.getData());
            currentVertex = previousVertices.get(currentVertex);
        }
        
        shortestPath.add(startVertex.getData());
        Collections.reverse(shortestPath);
        
        return shortestPath;
    }


    public String toString() {
        return listVertex.toString();
    }
}
