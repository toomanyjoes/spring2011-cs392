package yfilesTests;


//import demo.base.RandomGraphGenerator;
import y.algo.*;
import y.base.*;
import y.util.*;
import y.util.Maps.*;

public class yfilesMstKruskalTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        Graph graph = new Graph();
        GraphReader reader = new GraphReader(true, args[0]);
        DataProvider dp = reader.readGraph(graph);
        
        long beginTime = System.currentTimeMillis();
        EdgeList el = SpanningTrees.kruskal(graph, dp);
        long endTime = System.currentTimeMillis();
        //System.out.println(" Cycle? " + cycle);
        System.out.println("Time elapsed: " + (endTime-beginTime));
    }
    
}
