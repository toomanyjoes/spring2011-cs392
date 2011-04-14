package gpl;

//dja - trying to fix logic problems
import java.util.Iterator;
import java.util.LinkedList;
//dja: added to fix compile problems when doing the performance improvements
import java.util.Comparator;
import java.util.Collections;

//dja: added for performance improvement
import java.util.HashMap;
import java.util.Map;

import java.io.*;



// ************************************************************

abstract class Graph$$Directed {
    public LinkedList vertices;
    public static final boolean isDirected = true;

    public Graph$$Directed() {
        vertices = new LinkedList();
    }

    public VertexIter getVertices() 
    {
        // dja - trying to fix logic problems
        return new VertexIter() {
            private Iterator iter = vertices.iterator();
            public Vertex next() 
                {
                return ( Vertex )iter.next();
            }
            public boolean hasNext() 
                {
                return iter.hasNext();
            }
        };

    }
    // dja - fix compile code.
    //    public EdgeIter getEdges() { return null; }
    //    public EdgeIfc addEdge(Vertex start,  Vertex end) { return null; }
    //    public  Vertex findsVertex( String theName ) { return null; }

        // Fall back method that stops the execution of programs
    public void run( Vertex s ) {}

    //dja: fix for compile problems during performance improvements
    public void sortVertices( Comparator c ) {
        Collections.sort( vertices, c );
    }

    // Adds an edge without weights if Weighted layer is not present
    public void addAnEdge( Vertex start,  Vertex end, int weight )
      {
        addEdge( start,end );
    }

    public void addVertex( Vertex v ) {
        vertices.add( v );
    }

    // Adds and edge by setting end as adjacent to start vertices
    public EdgeIfc addEdge( Vertex start,  Vertex end ) {
        start.addAdjacent( end );
        return ( EdgeIfc ) start;
    }

    // Finds a vertex given its name in the vertices list
    public  Vertex findsVertex( String theName )
      {
        int i=0;
        Vertex theVertex;

        // if we are dealing with the root
        if ( theName==null )
            return null;

        for( i=0; i<vertices.size(); i++ )
            {
            theVertex = ( Vertex )vertices.get( i );
            if ( theName.equals( theVertex.name ) )
                return theVertex;
        }
        return null;
    }

    public void display() {
        int s = vertices.size();
        int i;

        System.out.println( "******************************************" );
        System.out.println( "Vertices " );
        for ( i=0; i<s; i++ )
            ( ( Vertex ) vertices.get( i ) ).display();
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
