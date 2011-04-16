import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RandomPseudoGraph {
	
	static Graph<Object, DefaultWeightedEdge> randomGraph;

    //Number of vertices
    static int vsize = 1000;
    
    /* max number of edges = n(n-1)/2 */
    static int esize = (int)(Math.random() * (vsize*(vsize-1)/2));
    static int reserved = 1;

    //~ Methods ----------------------------------------------------------------

    public static void main(String [] args)
    {
    	FileWriter fw;
    	PrintWriter pw = null;
		File output = null;
		
		try {
			output = new File("randompseudograph.txt");
			fw = new FileWriter(output);
			pw = new PrintWriter(fw);
		} catch (IOException e) {
			System.out.print("File: " + output.toString() + " not found");
		}
		
        //Create the graph object; it is null at this point
        randomGraph = new Pseudograph<Object, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        //Create the CompleteGraphGenerator object
        RandomGraphGenerator<Object, DefaultWeightedEdge> randomGenerator =
            new RandomGraphGenerator<Object, DefaultWeightedEdge>(vsize,esize);

        //Create the VertexFactory so the generator can create vertices
        VertexFactory<Object> vFactory =
            new ClassBasedVertexFactory<Object>(Object.class);

        //Use the CompleteGraphGenerator object to make completeGraph a
        //complete graph with [size] number of vertices
        randomGenerator.generateGraph(randomGraph, vFactory, null);

        //Now, replace all the vertices with sequential numbers so we can ID
        //them
        Set<Object> vertices = new HashSet<Object>();
        vertices.addAll(randomGraph.vertexSet());
        Integer counter = 0;
        for (Object vertex : vertices) {
            replaceVertex(vertex, (Object) counter++);
        }

        //Print out the graph to be sure it's really complete
/*        Iterator<Object> iter =
            new DepthFirstIterator<Object, DefaultWeightedEdge>(randomGraph);
        Object vertex;
        while (iter.hasNext()) {
            vertex = iter.next();
            System.out.println(
                "Vertex " + vertex.toString() + " is connected to: "
                + randomGraph.edgesOf(vertex).toString());
        }*/
        
        pw.println(vsize);
        pw.println(esize);
        pw.println(reserved);
        pw.println(reserved);
        pw.println(reserved);
        Set<DefaultWeightedEdge> printEdges = randomGraph.edgeSet();
        for(DefaultWeightedEdge dwe : printEdges){
        	pw.println(randomGraph.getEdgeSource(dwe) + " " + randomGraph.getEdgeTarget(dwe));
        }
        for(int i = 0; i < esize; i++){
        	pw.println((int)(Math.random() * 100));
        }
    
    }

    public static boolean replaceVertex(Object oldVertex, Object newVertex)
    {
        if ((oldVertex == null) || (newVertex == null)) {
            return false;
        }
        Set<DefaultWeightedEdge> relatedEdges = randomGraph.edgesOf(oldVertex);
        randomGraph.addVertex(newVertex);

        Object sourceVertex;
        Object targetVertex;
        for (DefaultWeightedEdge e : relatedEdges) {
            sourceVertex = randomGraph.getEdgeSource(e);
            targetVertex = randomGraph.getEdgeTarget(e);
            if (sourceVertex.equals(oldVertex)
                && targetVertex.equals(oldVertex))
            {
            	randomGraph.addEdge(newVertex, newVertex);
            } else {
                if (sourceVertex.equals(oldVertex)) {
                	randomGraph.addEdge(newVertex, targetVertex);
                } else {
                	randomGraph.addEdge(sourceVertex, newVertex);
                }
            }
        }
        randomGraph.removeVertex(oldVertex);
        return true;
    }
}
