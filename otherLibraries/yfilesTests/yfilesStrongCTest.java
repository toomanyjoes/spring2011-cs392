package yfilesTests;


//import demo.base.RandomGraphGenerator;
import y.algo.*;
import y.base.*;
import y.util.*;

public class yfilesStrongCTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        Graph graph = new Graph();
        GraphReader reader = new GraphReader(false, args[0]);
        reader.readGraph(graph);
        
        for(int i=0; i<500; i++) {
        long beginTime = System.currentTimeMillis();
        boolean connected = GraphConnectivity.isStronglyConnected(graph);
        long endTime = System.currentTimeMillis();
        System.out.println(" Strongly Connected? " + connected);
        System.out.println("Time elapsed: " + (endTime-beginTime));
        }
    }
    
}
