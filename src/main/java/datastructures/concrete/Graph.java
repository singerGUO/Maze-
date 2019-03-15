package datastructures.concrete;

import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IEdge;
import datastructures.interfaces.IList;
import datastructures.interfaces.IDisjointSet;
import datastructures.interfaces.IPriorityQueue;
import datastructures.interfaces.ISet;
import misc.Sorter;
import misc.exceptions.NoPathExistsException;

/**
 * Represents an undirected, weighted graph, possibly containing self-loops, parallel edges,
 * and unconnected components.
 *
 * Note: This class is not meant to be a full-featured way of representing a graph.
 * We stick with supporting just a few, core set of operations needed for the
 * remainder of the project.
 */
public class Graph<V, E extends IEdge<V> & Comparable<E>> {
    // NOTE 1:
    //
    // Feel free to add as many fields, private helper methods, and private
    // inner classes as you want.
    //
    // And of course, as always, you may also use any of the data structures
    // and algorithms we've implemented so far.
    //
    // Note: If you plan on adding a new class, please be sure to make it a private
    // static inner class contained within this file. Our testing infrastructure
    // works by copying specific files from your project to ours, and if you
    // add new files, they won't be copied and your code will not compile.
    //
    //
    // NOTE 2:
    //
    // You may notice that the generic types of Graph are a little bit more
    // complicated than usual.
    //
    // This class uses two generic parameters: V and E.
    //
    // - 'V' is the type of the vertices in the graph. The vertices can be
    //   any type the client wants -- there are no restrictions.
    //
    // - 'E' is the type of the edges in the graph. We've constrained Graph
    //   so that E *must* always be an instance of IEdge<V> AND Comparable<E>.
    //
    //   What this means is that if you have an object of type E, you can use
    //   any of the methods from both the IEdge interface and from the Comparable
    //   interface
    //
    // If you have any additional questions about generics, or run into issues while
    // working with them, please ask ASAP either on Piazza or during office hours.
    //
    // Working with generics is really not the focus of this class, so if you
    // get stuck, let us know we'll try and help you get unstuck as best as we can.
    private IDictionary<V, IList<E>> map;
    private IList<E> edgeList;
    private IList<V> vertexList;
    private int vertexNum;
    private int edgeNum;

    /**
     * Constructs a new graph based on the given vertices and edges.
     *
     * @throws IllegalArgumentException if any of the edges have a negative weight
     * @throws IllegalArgumentException if one of the edges connects to a vertex not
     *                                  present in the 'vertices' list
     * @throws IllegalArgumentException if vertices or edges are null or contain null
     */
    public Graph(IList<V> vertices, IList<E> edges) {
        //undirected graph
        map = new ChainedHashDictionary<>();
        edgeList = edges;
        vertexList = vertices;
        for (V vertice : vertices) {
            map.put(vertice, new DoubleLinkedList<>());
            vertexNum++;
        }
        for (E edge : edges) {
            if ((edge.getWeight() < 0)) {
                throw new IllegalArgumentException("negative weight");
            }
            if (!vertices.contains(edge.getVertex1()) || !vertices.contains(edge.getVertex2())) {
                throw new IllegalArgumentException("connects to the vertex not in the list");
            }
            if (vertices.contains(null) || edgeList.contains(null)) {
                throw new IllegalArgumentException("throw out null exception");
            }
            map.get(edge.getVertex1()).add(edge);
            map.get(edge.getVertex2()).add(edge);
            edgeNum++;
        }
    }


    /**
     * Sometimes, we store vertices and edges as sets instead of lists, so we
     * provide this extra constructor to make converting between the two more
     * convenient.
     *
     * @throws IllegalArgumentException if any of the edges have a negative weight
     * @throws IllegalArgumentException if one of the edges connects to a vertex not
     *                                  present in the 'vertices' list
     * @throws IllegalArgumentException if vertices or edges are null or contain null
     */
    public Graph(ISet<V> vertices, ISet<E> edges) {
        // You do not need to modify this method.
        this(setToList(vertices), setToList(edges));
    }

    // You shouldn't need to call this helper method -- it only needs to be used
    // in the constructor above.
    private static <T> IList<T> setToList(ISet<T> set) {
        if (set == null) {
            throw new IllegalArgumentException();
        }
        IList<T> output = new DoubleLinkedList<>();
        for (T item : set) {
            output.add(item);
        }
        return output;
    }

    /**
     * Returns the number of vertices contained within this graph.
     */
    public int numVertices() {
        return vertexNum;
    }

    /**
     * Returns the number of edges contained within this graph.
     */
    public int numEdges() {
        return edgeNum;
    }

    /**
     * Returns the set of all edges that make up the minimum spanning tree of
     * this graph.
     * <p>
     * If there exists multiple valid MSTs, return any one of them.
     * <p>
     * Precondition: the graph does not contain any unconnected components.
     */
    public ISet<E> findMinimumSpanningTree() {
        ISet<E> mst = new ChainedHashSet<>();
        IDisjointSet<V> disjointSet = new ArrayDisjointSet<>();
        for (V vertex : this.vertexList) {
            disjointSet.makeSet(vertex);
        }
        for (E edge : Sorter.topKSort(numEdges(), edgeList)) {
            V a = edge.getVertex1();
            V b = edge.getVertex2();
            int root1 = disjointSet.findSet(a);
            int root2 = disjointSet.findSet(b);
            if (root1 != root2) {
                disjointSet.union(a, b);
                mst.add(edge);
            }

        }
        return mst;
    }



    /**
     * Returns the edges that make up the shortest path from the start
     * to the end.
     *
     * The first edge in the output list should be the edge leading out
     * of the starting node; the last edge in the output list should be
     * the edge connecting to the end node.
     *
     * Return an empty list if the start and end vertices are the same.
     *
     * @throws NoPathExistsException  if there does not exist a path from the start to the end
     * @throws IllegalArgumentException if start or end is null
     */


    public IList<E> findShortestPathBetween(V start, V end) {

        if (start.equals(end)) {
            return new DoubleLinkedList<E>();
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }

        IPriorityQueue<Node> heap = new ArrayHeap<>();

        IDictionary<V, Node> distance = new ArrayDictionary<>();


        for (V vertex : vertexList) {
            Node vertexInfo = new Node(vertex, Double.POSITIVE_INFINITY, new DoubleLinkedList<E>());
            distance.put(vertex, vertexInfo);

        }
        Node initializer = new Node(start, 0.0, new DoubleLinkedList<E>());

        distance.put(start, initializer);
        heap.insert(initializer);
        while (!heap.isEmpty() && heap.peekMin().getVertex() != end) {
            Node v = heap.removeMin();
            V vertex = v.getVertex();

            for (E edge : map.get(vertex)) {
                V otherVertex = edge.getOtherVertex(vertex);
                IList<E> stored = new DoubleLinkedList<>();
                for (E e : v.getPath()) {
                    stored.add(e);
                }
                stored.add(edge);

                double newDist = distance.get(vertex).getDistance() + edge.getWeight();
                double oldDist = distance.get(otherVertex).getDistance();
                if (newDist < oldDist) {
                    initializer = new Node(otherVertex, newDist, stored);
                    heap.insert(initializer);
                    distance.put(otherVertex, initializer);
                }
            }
        }


        if (distance.get(end).getDistance() == Double.POSITIVE_INFINITY) {

            throw new NoPathExistsException();
        }
        return distance.get(end).getPath();

    }

    private class Node implements Comparable<Node> {
        private V vertex;
        private double distance;
        private IList<E> path;

        public Node(V vertex, double distance, IList<E> path) {
            this.vertex = vertex;
            this.distance = distance;
            this.path = path;
        }

        public V getVertex() {
            return this.vertex;
        }

        public double getDistance() {
            return this.distance;
        }

        public IList<E> getPath() {
            return this.path;
        }

        public int compareTo(Node other) {
            return (int) (this.distance - other.distance);
        }

    }

}
