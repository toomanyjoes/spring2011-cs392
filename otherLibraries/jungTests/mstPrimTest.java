package jungTests;

import java.io.*;

import java.util.*;

import org.apache.commons.collections15.*;
import org.apache.commons.collections15.functors.*;

import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.algorithms.shortestpath.*; 

public class mstPrimTest {
    private static GraphReader reader;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        Graph<Object, MyLink> graph = new SparseGraph<Object, MyLink>();
        GraphReader reader = new GraphReader(true, args[0]);
        reader.readGraph(graph);
        System.out.println("read graph");
        Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink, Double>() {
            public Double transform(MyLink link) {
                return new Double((double)link.weight);
            }
        };
        PrimMinimumSpanningTree mst = new PrimMinimumSpanningTree(new ConstantFactory<Graph>(graph), wtTransformer);
        System.out.println("starting clock");
        long beginTime = System.currentTimeMillis();
        mst.transform(graph);
        long endTime = System.currentTimeMillis();
        //System.out.println(" StronglyConnected? " + connected);
        System.out.println("Time elapsed: " + (endTime-beginTime));
    }
}

