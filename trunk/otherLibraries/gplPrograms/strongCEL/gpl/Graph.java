package gpl;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

//dja: added for performance improvement
import java.util.HashMap;
import java.util.Map;

import java.io.*;



// *************************************************************************

abstract class Graph$$Directed {
    private LinkedList vertices;
    private LinkedList edges;
    public static final boolean isDirected = true;

    public Graph$$Directed() {
        vertices = new LinkedList();
        edges = new LinkedList();
    }

    // Fall back method that stops the execution of programs
    public void run( Vertex s ) {}

    public void sortEdges( Comparator c ) {
        Collections.sort( edges, c );
    }

    public void sortVertices( Comparator c ) {
        Collections.sort( vertices, c );
    }

    // Adds an edge without weights if Weighted layer is not present
    public EdgeIfc addEdge( Vertex start,  Vertex end ) {
        Edge theEdge = new  Edge();
        theEdge.EdgeConstructor( start, end );
        edges.add( theEdge );
        start.addNeighbor( new  Neighbor( end, theEdge ) );
        //end.addNeighbor( new  Neighbor( start, theEdge ) );

        return theEdge;
    }

    protected void addVertex( Vertex v ) {
        vertices.add( v );
    }

    // Finds a vertex given its name in the vertices list
    public  Vertex findsVertex( String theName )
      {
        Vertex theVertex = null;

        // if we are dealing with the root
        if ( theName==null )
            return null;

        for( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            theVertex = vxiter.next();
            if ( theName.equals( theVertex.getName() ) )
                return theVertex;
        }

        return theVertex;
    }

    public VertexIter getVertices() {
        return new VertexIter() {
            private Iterator iter = vertices.iterator();
            public Vertex next() {
                return ( Vertex )iter.next();
            }
            public boolean hasNext() {
                return iter.hasNext();
            }
        };
    }

    public EdgeIter getEdges() {
        return new EdgeIter() {
            private Iterator iter = edges.iterator();
            public EdgeIfc next() {
                return ( EdgeIfc )iter.next();
            }
            public boolean hasNext() {
                return iter.hasNext();
            }
        };
    }

    public void display() {
        int i;

        System.out.println( "******************************************" );
        System.out.println( "Vertices " );
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext() ; )
            vxiter.next().display();

        System.out.println( "******************************************" );
        System.out.println( "Edges " );
        for ( EdgeIter edgeiter = getEdges(); edgeiter.hasNext(); )
            edgeiter.next().display();

        System.out.println( "******************************************" );
    }

}



// **********************************************************************

abstract class Graph$$DFS extends  Graph$$Directed  {
    public void GraphSearch( WorkSpace w ) 
    {
        // Step 1: initialize visited member of all nodes
        VertexIter vxiter = getVertices();
        if ( vxiter.hasNext() == false )
        {
            return; // if there are no vertices return
        }

        // Initializing the vertices
        while( vxiter.hasNext() ) 
        {
            Vertex v = vxiter.next();
            v.init_vertex( w );
        }

        // Step 2: traverse neighbors of each node
        for( vxiter = getVertices(); vxiter.hasNext(); ) 
        {
            Vertex v = vxiter.next();
            if ( !v.visited ) 
            {
                w.nextRegionAction( v );
                v.nodeSearch( w );
            }
        }
    }
      // inherited constructors



    public Graph$$DFS (  ) { super(); }
}



// *********************************************************

abstract class Graph$$Transpose extends  Graph$$DFS  {

    public  Graph ComputeTranspose( Graph the_graph )
   {
        int i;
        String theName;

        //dja: added for performance improvement
        Map newVertices = new HashMap();

        // Creating the new Graph
        Graph newGraph = new  Graph();

        // Creates and adds the vertices with the same name
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            theName = vxiter.next().getName();
            //dja: changes for performance improvement
            Vertex v = new  Vertex().assignName( theName );
            //            newGraph.addVertex( new  Vertex().assignName( theName ) );
            newGraph.addVertex( v );

            //dja: added for performance improvement
            newVertices.put( theName, v );
        }

        Vertex theVertex, newVertex;
        Vertex theNeighbor;
        Vertex newAdjacent;
        EdgeIfc newEdge;

        // adds the transposed edges
        // dja: added line below for performance improvements
        VertexIter newvxiter = newGraph.getVertices();
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            // theVertex is the original source vertex
            // the newAdjacent is the reference in the newGraph to theVertex
            theVertex = vxiter.next();

            // dja: performance improvement fix
            // newAdjacent = newGraph.findsVertex( theVertex.getName() );
            newAdjacent = newvxiter.next();

            for( VertexIter neighbors = theVertex.getNeighbors(); neighbors.hasNext(); )
            {
                // Gets the neighbor object
                theNeighbor = neighbors.next();

                // the new Vertex is the vertex that was adjacent to theVertex
                // but now in the new graph
                // dja: performance improvement fix
                // newVertex = newGraph.findsVertex( theNeighbor.getName() );
                newVertex = ( Vertex ) newVertices.get( theNeighbor.getName() );

                // Creates a new Edge object and adjusts the adornments
                newEdge = newGraph.addEdge( newVertex, newAdjacent );
            //newEdge.adjustAdorns( theNeighbor.edge );

            // Adds the new Neighbor object with the newly formed edge
            // newNeighbor = new $TEqn.Neighbor(newAdjacent, newEdge);
            // (newVertex.neighbors).add(newNeighbor);

            } // all adjacentNeighbors
        } // all the vertices

        return newGraph;

    }
      // inherited constructors



    public Graph$$Transpose (  ) { super(); } // of ComputeTranspose

}



// Cormen's Textbook 23.5
// ***********************************************************************

abstract class Graph$$StronglyConnected extends  Graph$$Transpose  {

    // Executes Strongly Connected Components
    public void run( Vertex s )
     {
        Graph gaux = StrongComponents();
        Graph.stopProfile();
        gaux.display();
        Graph.resumeProfile();
        super.run( s );
    }

    public  Graph StrongComponents() {

        FinishTimeWorkSpace FTWS = new FinishTimeWorkSpace();

        // 1. Computes the finishing times for each vertex
        GraphSearch( FTWS );

        // 2. Order in decreasing  & call DFS Transposal
        sortVertices( new Comparator() {
            public int compare( Object o1, Object o2 )
                {
                Vertex v1 = ( Vertex )o1;
                Vertex v2 = ( Vertex )o2;

                if ( v1.finishTime > v2.finishTime )
                    return -1;

                if ( v1.finishTime == v2.finishTime )
                    return 0;
                return 1;
            }
        } );

        // 3. Compute the transpose of G
        // Done at layer transpose
        Graph gaux = ComputeTranspose( ( Graph )((Graph) this) );

        // 4. Traverse the transpose G
        WorkSpaceTranspose WST = new WorkSpaceTranspose();
        gaux.GraphSearch( WST );

        return gaux;

    }
      // inherited constructors



    public Graph$$StronglyConnected (  ) { super(); } // of Strong Components

}



public class Graph extends  Graph$$StronglyConnected  {
    public Reader inFile; // File handler for reading
    public static int ch; // Character to read/write

    // timmings
    static long last = 0, current = 0, accum = 0;

    public void runBenchmark( String FileName ) throws IOException
    {
        try 
        {
            inFile = new FileReader( FileName );
        }
        catch ( IOException e )
        {
            System.out.println( "Your file " + FileName + " cannot be read" );
        }
    }

    public void stopBenchmark() throws IOException
    {
        inFile.close();
    }

    public int readNumber() throws IOException
    {
        int index = 0;
        char[ ] word = new char[ 80 ];
        int ch = 0;

        ch = inFile.read();
        while( ch==32 )
        {
            ch = inFile.read(); // skips extra whitespaces
        }

        while( ch != -1 && ch != 32 && ch != 10 ) // while it is not EOF, WS, NL
        {
            word[ index++ ] = ( char )ch;
            ch = inFile.read();
        }
        word[ index ] = 0;

        String theString = new String( word );

        theString = new String( theString.substring( 0,index ) ).trim();
        return Integer.parseInt( theString,10 );
    }

    public static void startProfile()
    {
        accum = 0;
        current = System.currentTimeMillis();
        last = current;
    }

    public static void stopProfile()
    {
        current = System.currentTimeMillis();
        accum = accum + ( current - last );
    }

    public static void resumeProfile()
    {
        current = System.currentTimeMillis();
        last = current;
    }

    public static void endProfile()
     {
        current = System.currentTimeMillis();
        accum = accum + ( current-last );
        System.out.println( "Time elapsed: " + accum + " milliseconds" );
    }
      // inherited constructors



    public Graph (  ) { super(); }
}
