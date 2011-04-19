package yfilesTests;


//import demo.base.RandomGraphGenerator;
import y.algo.*;
import y.base.*;
import y.util.*;

public class yfilesCycleTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        Graph graph = new Graph();
        GraphReader reader = new GraphReader(false, args[0]);
        reader.readGraph(graph);
        
        long beginTime = System.currentTimeMillis();
        boolean cycle = GraphChecker.isCyclic(graph);
        long endTime = System.currentTimeMillis();
        System.out.println(" Cycle? " + cycle);
        System.out.println("Time elapsed: " + (endTime-beginTime));
    }
    
}
