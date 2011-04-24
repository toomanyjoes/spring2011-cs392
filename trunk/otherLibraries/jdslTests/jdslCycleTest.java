package jdslTests;

import java.io.*;

import java.util.*;

import jdsl.graph.api.*;
import jdsl.graph.algo.*;
import jdsl.graph.ref.*;
import jdsl.core.api.*;

public class jdslCycleTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        Graph graph = new IncidenceListGraph();
        GraphReader reader = new GraphReader(false, false, args[0]);
        reader.readGraph(graph);
        
        FindCycleDFS cycleDetector = new FindCycleDFS();
        for(int i=0; i<5000; i++) {
        long beginTime = System.currentTimeMillis();
        cycleDetector.execute(graph, graph.aVertex());
        ObjectIterator it = cycleDetector.getCycle();
        long endTime = System.currentTimeMillis();
        System.out.println(" Cycle? " + it.hasNext());
        System.out.println("Time elapsed: " + (endTime-beginTime));
        }
    }
}

