package gpl;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

//dja: add for performance reasons
import java.util.HashMap;
import java.util.Map;

import java.lang.Integer;
import java.util.Set;
import java.util.HashSet;

import java.io.*;



// Edge-Neighbor implementation

// *************************************************************************

abstract class Graph$$Undirected {
    private LinkedList vertices;
    private LinkedList edges;
    public static final boolean isDirected = false;

    //dja: add for performance reasons
    private Map verticesMap;

    public Graph$$Undirected() {
        vertices = new LinkedList();
        edges = new LinkedList();

        //dja: add for performance reasons
        verticesMap = new HashMap();

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
        end.addNeighbor( new  Neighbor( start, theEdge ) );

        return theEdge;
    }

    protected void addVertex( Vertex v ) {
        vertices.add( v );

        //dja: add for performance reasons
        verticesMap.put( v.name, v );

    }

    // Finds a vertex given its name in the vertices list
    public  Vertex findsVertex( String theName ) {
        Vertex theVertex;

        // if we are dealing with the root
        if ( theName==null )
            return null;

                  //dja: removed for performance reasons
        //        for( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        //        {
        //            theVertex = vxiter.next();
        //            if ( theName.equals( theVertex.getName() ) )
        //                return theVertex;
        //        }
        //        return null;

                  //dja: add for performance reasons
        return ( Vertex ) verticesMap.get( theName );

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

    // Finds an Edge given both of its vertices
    public  EdgeIfc findsEdge( Vertex theSource,
                    Vertex theTarget )
       {
        EdgeIfc theEdge;

            // dja: performance improvement
        //  for( EdgeIter edgeiter = getEdges(); edgeiter.hasNext(); )
        for( EdgeIter edgeiter = theSource.getEdges(); edgeiter.hasNext(); )
         {
            theEdge = edgeiter.next();
            if ( ( theEdge.getStart().getName().equals( theSource.getName() ) &&
                  theEdge.getEnd().getName().equals( theTarget.getName() ) ) ||
                 ( theEdge.getStart().getName().equals( theTarget.getName() ) &&
                  theEdge.getEnd().getName().equals( theSource.getName() ) ) )
                return theEdge;
        }
        return null;
    }

    public void display() {
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



// *************************************************************************

abstract class Graph$$MSTPrim extends  Graph$$Undirected  {

    // Executes MSTPrim
    public void run( Vertex s )
     {
        Graph gaux = Prim( s );
        Graph.stopProfile();
        gaux.display();
        Graph.resumeProfile();
        super.run( s );
    }

    public  Graph Prim( Vertex r ) {
        Vertex root;

        root = r;
        Vertex x;

        // 2. and 3. Initializes the vertices
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            x = vxiter.next();
            x.pred = null;
            x.key = Integer.MAX_VALUE;
        }

        // 4. and 5.
        root.key = 0;
        root.pred = null;

        // 2. S <- empty set

        // 1. Queue <- V[G], copy the vertex in the graph in the priority queue
        LinkedList Queue = new LinkedList();
        //dja: added for performance reasons
        Set indx = new HashSet();

        // Inserts the root at the head of the queue
        //dja: Moved from below the for loop for performance reasons
        Queue.add( root );
     
        //dja: added for performance reasons
        indx.add( root.getName() );
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            x = vxiter.next();
            if ( x.key != 0 ) // this means, if this is not the root
            {
                Queue.add( x );
                //dja: added for performance reasons
                indx.add( x.getName() );
            }
        }

        // Inserts the root at the head of the queue
        //dja: moved above for loop for perf. reasons
        // Queue.addFirst( root );

        // 6. while Q!=0
        Vertex ucurrent;
        int j,k,l;
        int pos;
        LinkedList Uneighbors;
        Vertex u,v;
        EdgeIfc en;
        NeighborIfc vn;

        int wuv;
        boolean isNeighborInQueue = false;

        // Queue is a list ordered by key values.
        // At the beginning all key values are INFINITUM except
        // for the root whose value is 0.
        while ( Queue.size()!=0 )
        {
            // 7. u <- Extract-Min(Q);
            // Since this is an ordered queue the first element is the min
            u = ( Vertex )Queue.removeFirst();

            //dja: added code below for per. reasons
            indx.remove( u.getName() );
            
            // 8. for each vertex v adjacent to u
            Uneighbors = u.getNeighborsObj();

            // dja - fix compile erros
            // for( EdgeIter edgeiter = u.getEdges(); edgeiter.hasNext(); )
            k = 0;
            for( EdgeIter edgeiter = u.getEdges(); edgeiter.hasNext(); k++ )
            {
                vn = ( NeighborIfc )Uneighbors.get( k );
                                // dja - fix compile bug
                                // en = edgeiter.next();
                //                en = ( Edge ) edgeiter.next();
                en = edgeiter.next();

                v = en.getOtherVertex( u );

                // Check to see if the neighbor is in the queue
                isNeighborInQueue = false;

                // if the Neighor is in the queue
                                    //dja: removed 2 lines for performance reasons and added 3rd line.  left 4th line alone
                //int indexNeighbor = Queue.indexOf( v );
                //if ( indexNeighbor>=0 )
                if ( indx.contains( v.getName() ) )
                    isNeighborInQueue = true;
                wuv = en.getWeight();

                // 9. Relax (u,v w)
                if ( isNeighborInQueue && ( wuv < v.key ) )
                    {
                    v.key = wuv;
                    v.pred = u.getName();
                    Uneighbors.set( k,vn ); // adjust values in the neighbors

                    // update the values of v in the queue
                    // Remove v from the Queue so that we can reinsert it
                    // in a new place according to its new value to keep
                    // the Linked List ordered
                                                //dja: added for perf. reasons
                    Object residue = ( Object ) v;
                    Queue.remove( residue );
                    // Object residue = Queue.remove( indexNeighbor );

                                                //dja: added for performance reasons
                    indx.remove( v.getName() );

                    // Get the new position for v
                    int position = Collections.binarySearch( Queue,v,
                                              new Comparator() {
                        public int compare( Object o1, Object o2 )
                                                 {
                            Vertex v1 = ( Vertex )o1;
                            Vertex v2 = ( Vertex )o2;

                            if ( v1.key < v2.key )
                                return -1;
                            if ( v1.key == v2.key )
                                return 0;
                            return 1;
                        }
                    } );

                    // Adds v in its new position in Queue
                    if ( position < 0 )  // means it is not there
                                         {
                        Queue.add( - ( position+1 ),v );
                    }
                    else      // means it is there
                                         {
                        Queue.add( position,v );
                    }
                
                    //dja: added for perf. reasons
                    indx.add( v.getName() );

                } // if 8-9.
            } // for all neighbors
        } // of while

        // Creates the new Graph that contains the SSSP
        String theName;
        Graph newGraph = new  Graph();

        // Creates and adds the vertices with the same name
        // dja - fixed compile bug
        // for ( i=0; i<numvertices; i++ )
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            // dja - fixing compile bugs
            // theName = ( ( Vertex )vertices.get( i ) ).name;
            Vertex vtx = vxiter.next();
            theName = vtx.name;

            newGraph.addVertex( new  Vertex().assignName( theName ) );
        }

        // Creates the edges from the NewGraph
        Vertex theVertex, thePred;
        Vertex theNewVertex, theNewPred;
        EdgeIfc   e;

        // Creates and adds the vertices with the same name
        // dja - fixed compile bug
        // for ( i=0; i<numvertices; i++ )
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            // theVertex and its Predecessor
            // dja - fixing compile bugs
            // theVertex = ( Vertex )vertices.get( i );
            theVertex = vxiter.next();

            thePred = findsVertex( theVertex.pred );

            // if theVertex is the source then continue we dont need
            // to create a new edge at all
            if ( thePred==null )
                continue;

            // Find the references in the new Graph
            theNewVertex = newGraph.findsVertex( theVertex.name );
            theNewPred = newGraph.findsVertex( thePred.name );

                        // Creates the new edge from predecessor -> vertex in the newGraph
                        // and ajusts the adorns based on the old edge
            //            EdgeIfc theNewEdge = new  Edge();

                        // dja - fix compile errors
                        // theNewEdge.EdgeConstructor( theNewPred, theNewVertex );
            EdgeIfc theNewEdge = newGraph.addEdge( theNewPred, theNewVertex );

            // dja - fix compile errors
            // e = findsEdge( thePred,theVertex );
            e = findsEdge( thePred,theVertex );

            // dja - fix comple errors
            // theNewEdge.adjustAdorns( e );
            theNewEdge.adjustAdorns( e );

        // Adds the new edge to the newGraph
        // dja - fix compile errors
        // newGraph.addEdge( theNewEdge );
        }
        return newGraph;

    }
      // inherited constructors



    public Graph$$MSTPrim (  ) { super(); } // MST

}



public class Graph extends  Graph$$MSTPrim  {
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
