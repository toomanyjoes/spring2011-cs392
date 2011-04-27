package jdslTests;

import java.io.*;

import java.util.*;

import jdsl.graph.api.*;
import jdsl.graph.algo.*;
import jdsl.graph.ref.*;
import jdsl.core.api.*;

public class jdslMstPrimTest extends IntegerPrimTemplate {
    private static GraphReader reader;
    private List<Integer> edgeWeights;
    private int edgeCount;
    private Graph mst;
    
    public jdslMstPrimTest() {
        edgeCount = 0;
        mst = new IncidenceListGraph();
    }
    
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("must provide file name");
            System.exit(1);
        }
        
        InspectableGraph graph = new IncidenceListGraph();
        reader = new GraphReader(false, true, args[0]);
        
        jdslMstPrimTest mstTest = new jdslMstPrimTest();
        mstTest.edgeWeights = reader.readGraph((Graph)graph);
        for(int i=0; i<100; i++) {
        long beginTime = System.currentTimeMillis();
        mstTest.executeAll(graph, graph.aVertex());
        long endTime = System.currentTimeMillis();
        //System.out.println(" Cycle? " + it.hasNext());
        System.out.println("Time elapsed: " + (endTime-beginTime));
        }
        System.out.flush();
    }
    
    protected int weight(Edge e) {
        int edgeNum = ((Integer)e.element()).intValue();
        //System.out.println("edgeNum: " + edgeNum);
        return edgeWeights.get(edgeNum).intValue();
    }
    
    protected void treeEdgeFound(Vertex v, Edge vparent, int treeWeight) {
        //System.out.println("treeEdgeFound edgeCount= " + edgeCount++);
        Vertex newVertex = mst.insertVertex(v.element());
        if(vparent != null) {
            Vertex parent = mst.insertVertex(new Object());
            mst.insertEdge(newVertex, parent, new Object());
        }
    }
}

