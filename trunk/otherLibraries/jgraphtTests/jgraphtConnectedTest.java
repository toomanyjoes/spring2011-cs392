package jgraphtTests;

import java.io.*;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.alg.*;

public class jgraphtConnectedTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        DirectedPseudograph<Object, DefaultEdge> graph = new DirectedPseudograph<Object, DefaultEdge>(DefaultEdge.class);
        GraphReader reader = new GraphReader(false, args[0]);
        reader.readGraph(graph);
        
        ConnectivityInspector<Object, DefaultEdge> inspector = new ConnectivityInspector<Object, DefaultEdge>(graph);
        long beginTime = System.currentTimeMillis();
        inspector.connectedSets();
        long endTime = System.currentTimeMillis();
        //System.out.println(" StronglyConnected? " + connected);
        System.out.println("Total time: " + (endTime-beginTime));
    }
}

