package jgraphtTests;

import java.io.*;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.alg.*;

public class jgraphtMstKruskalTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        DirectedWeightedMultigraph<Object, DefaultWeightedEdge> graph = new DirectedWeightedMultigraph<Object, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        GraphReader reader = new GraphReader(true, args[0]);
        reader.readGraph(graph);
        
        long beginTime = System.currentTimeMillis();
        KruskalMinimumSpanningTree<Object, DefaultWeightedEdge> mstAlgo = new KruskalMinimumSpanningTree<Object, DefaultWeightedEdge>(graph);
        mstAlgo.getEdgeSet();
        //double cost = mstAlgo.getSpanningTreeCost();
        long endTime = System.currentTimeMillis();
        //System.out.println("cost " + cost);
        System.out.println("Time elapsed: " + (endTime-beginTime));
    }
}

