package gpl;

import java.io.*;



public class Main {
    public static void main( String[] args ) 
{
        long beginning = System.currentTimeMillis();
        // Step 1: create graph object

        Graph g = new  Graph();
        Graph gaux = new  Graph();

        // Step 2: sets up the benchmark file to read
        try {
            g.runBenchmark( args[0] );
        }
        catch( IOException e ) {}

        // Step 3: reads number of vertices, number of edges
        // and weights
        int num_vertices = 0;
        int num_edges = 0;
        int dummy = 0;
        try {
            num_vertices = g.readNumber();
            num_edges = g.readNumber();
            dummy = g.readNumber();
            dummy = g.readNumber();
            dummy = g.readNumber();
        }
        catch( IOException e ) {}

        // Step 4: reserves space for vertices, edges and weights
        Vertex V[] = new  Vertex[num_vertices];
        // $TEqn.Edge E[] = new $TEqn.Edge[num_edges];
        int weights[] = new int[num_edges];
        int startVertices[] = new int[num_edges];
        int endVertices[] = new int[num_edges];

        // Step 5: creates the vertices objects
        int i=0;
        for ( i=0; i<num_vertices; i++ )
    {
            V[i] = new Vertex().assignName( "v"+i );
            g.addVertex( V[i] );
        }

        // Step 6:        reads the edges
        try {
            for( i=0; i<num_edges; i++ )
             {
                startVertices[i] = g.readNumber();
                endVertices[i] = g.readNumber();
            }
        }
        catch( IOException e ) {}

        // Step 7: reads the weights
        try {
            for( i=0; i<num_edges; i++ )
              {
                weights[i] = g.readNumber();
            }
        }
        catch ( IOException e ) {}

        // Stops the benchmark reading
        try {
            g.stopBenchmark();
        }
        catch( IOException e ) {}

        // Step 8: Adds the edges
        for ( i=0; i<num_edges; i++ )
        {
            Vertex v1 = ( Vertex ) V[ startVertices[ i ] ];
            Vertex v2 = ( Vertex ) V[ endVertices[ i ] ];
            EdgeIfc edge = g.addEdge( v1, v2 );
                        // dja: fixing compile problems
            //            ( ( Edge ) edge ).setWeight( weights[ i ] );
            edge.setWeight( weights[ i ] );
        }

        for(int j = 0; j < 500; j++) {
        // Executes the selected features
        g.startProfile();
        g.run( g.findsVertex( args[1] ) );

        g.stopProfile();
        long totalTime = System.currentTimeMillis() - beginning;
        //g.display();
        g.resumeProfile();

        // End profiling

        g.endProfile();
        System.out.println( "Total Time: " + totalTime );
        }
    } // main

}
