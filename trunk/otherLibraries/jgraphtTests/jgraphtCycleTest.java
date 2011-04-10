package jgraphtTests;

import java.io.*;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.alg.*;

public class jgraphtCycleTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        reader = new GraphReader();
        if(!reader.openFile(args[0]))
            System.exit(1);
        DirectedPseudograph<Object, DefaultEdge> graph = new DirectedPseudograph<Object, DefaultEdge>(DefaultEdge.class);
        
        int numVertices = reader.readNumber();
        int numEdges = reader.readNumber();
        reader.readNumber(); reader.readNumber(); reader.readNumber();

        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < numVertices; i++)
        {
            Object curr = new Object();
            vList.add(curr);
            graph.addVertex(curr);
        }

        for(int i=0; i < numEdges; i++)
        {
            int start_idx = reader.readNumber();
            int end_idx = reader.readNumber();
            Object start = vList.get(start_idx);
            Object end = vList.get(end_idx);
            graph.addEdge(start, end);
        }
        
        CycleDetector<Object, DefaultEdge> cycleDetector = new CycleDetector<Object, DefaultEdge>(graph);
        Object v1 = vList.get(0);
        long beginTime = System.currentTimeMillis();
        boolean cycle = cycleDetector.detectCycles();
        long endTime = System.currentTimeMillis();
        System.out.println(" Cycle? " + cycle);
        System.out.println("Total time: " + (endTime-beginTime));
    }
}

