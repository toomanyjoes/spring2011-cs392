package gpl;

import java.util.Iterator;
import java.util.LinkedList;

//dja: add for performance reasons
import java.util.HashMap;
import java.util.Map;

import java.lang.Integer;
import java.util.Collections;
import java.util.Comparator;

import java.io.*;



// *************************************************************************
   
abstract class Graph$$Undirected {
    public LinkedList vertices;
    public static boolean isDirected = false;
      
    //dja: add for performance reasons
    private Map verticesMap;

    public Graph$$Undirected() 
    {
        vertices = new LinkedList();

        //dja: add for performance reasons
        verticesMap = new HashMap();

    }
 
    // Fall back method that stops the execution of programs
    public void run( Vertex s )
    {}

    // Adds an edge without weights if Weighted layer is not present
    public void addEdge( Vertex start,   Neighbor theNeighbor ) 
    {
        start.addEdge( theNeighbor );
        Vertex end = theNeighbor.neighbor;
        end.addEdge( new  Neighbor( start ) );
    }

    public void addVertex( Vertex v ) 
    {
        vertices.add( v );

        //dja: add for performance reasons
        verticesMap.put( v.name, v );
    }
   
    // Finds a vertex given its name in the vertices list
    public  Vertex findsVertex( String theName )
    {
        Vertex theVertex;
        
        // if we are dealing with the root
        if ( theName == null )
            return null;

                  //dja: removed for performance reasons
        //        for( VertexIter vxiter = getVertices( ); vxiter.hasNext( ); )
        //        {
        //            theVertex = vxiter.next( );
        //            if ( theName.equals( theVertex.getName( ) ) )
        //            {
        //               return theVertex;
        //            }
        //        }
        //        return null;

                  //dja: add for performance reasons
        return ( Vertex ) verticesMap.get( theName );

    }

    public VertexIter getVertices() 
    {
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

    // Finds an Edge given both of its vertices
    public  EdgeIfc findsEdge( Vertex theSource,
                    Vertex theTarget )
       {
          //dja: performance improvement
        //for( VertexIter vertexiter = getVertices(); vertexiter.hasNext(); )
        // {
          //        Vertex v1 = vertexiter.next( );
          //        for( EdgeIter edgeiter = v1.getEdges(); edgeiter.hasNext(); )
        //    {
          //          EdgeIfc theEdge = edgeiter.next();
          //                Vertex v2 = theEdge.getOtherVertex( v1 );
        //              if ( ( v1.getName().equals( theSource.getName() ) &&
        //                   v2.getName().equals( theTarget.getName() ) ) ||
        //                      ( v1.getName().equals( theTarget.getName() ) &&
        //                   v2.getName().equals( theSource.getName() ) ) )
        //                return theEdge;
        //    }
        //}
        Vertex v1 = theSource;
        for( EdgeIter edgeiter = v1.getEdges(); edgeiter.hasNext(); )
            {
            EdgeIfc theEdge = edgeiter.next();
            Vertex v2 = theEdge.getOtherVertex( v1 );
            if ( ( v1.getName().equals( theSource.getName() ) &&
                               v2.getName().equals( theTarget.getName() ) ) ||
                                  ( v1.getName().equals( theTarget.getName() ) &&
                               v2.getName().equals( theSource.getName() ) ) )
                return theEdge;
        }
        return null;
    }

    public void display() 
    {
        System.out.println( "******************************************" );
        System.out.println( "Vertices " );
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext() ; )
        {
            vxiter.next().display();
        }

        System.out.println( "******************************************" );
    }

    // Adds an edge without weights if Weighted layer is not present
    public EdgeIfc addEdge( Vertex start,  Vertex end )
      {
        Neighbor e = new Neighbor( end );
        addEdge( start, e );
        return e;
    }
    
}



// ***********************************************************************
 
abstract class Graph$$Weighted extends  Graph$$Undirected  {
    // Adds an edge with weights
    public void addAnEdge( Vertex start,  Vertex end, int weight )
    {
        addEdge( start, new  Neighbor( end, weight ) );
    }
      
    public void addEdge( Vertex start,  Neighbor theNeighbor )
    {
        super.addEdge( start,theNeighbor );
          
        // At this point the edges are added.
        // If there is an adorn like weight it has to be added to
        // the neighbor already present there
        if ( isDirected==false ) 
      {
            // It has to add ONLY the weight object to the neighbor
            Vertex end = theNeighbor.neighbor;
            end.addWeight( end,theNeighbor.weight );
        
        } // of else
    }
    
    public void display() 
    {
        super.display();
    }
      // inherited constructors



    public Graph$$Weighted (  ) { super(); }

}



// *************************************************************************
   
abstract class Graph$$MSTKruskalPrepGnR extends  Graph$$Weighted  {
        
    LinkedList edges = null;
    boolean edgesNotBuilt = true;

    public EdgeIter getEdges()
    {
        if ( edgesNotBuilt )
        {
            edgesNotBuilt = false;
            edges = new LinkedList();

            int k1=0,k2=0, sizeAV=0;
            Neighbor tempNeighbor;
        
            for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
                    {
                Vertex v = vxiter.next();
                sizeAV = v.adjacentNeighbors.size();
                for ( k2=0; k2<sizeAV; k2++ )
                            {
                    tempNeighbor = ( Neighbor ) v.adjacentNeighbors.get( k2 );
                    edges.add( new  Edge( v, tempNeighbor.neighbor,
                                            tempNeighbor.weight ) );
                } // of k2
            } // of k1
        }
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
    public void sortEdges( Comparator c ) {
        Collections.sort( edges, c );
    }
      // inherited constructors



    public Graph$$MSTKruskalPrepGnR (  ) { super(); }

}



// *************************************************************************

abstract class Graph$$MSTKruskal extends  Graph$$MSTKruskalPrepGnR  {

    // Executes MSTKruskal
    public void run( Vertex s )
     {
        Graph gaux = Kruskal();
        Graph.stopProfile();
        gaux.display();
        Graph.resumeProfile();
        super.run( s );
    }

    public  Graph Kruskal() {

        // 1. A <- Empty set
        LinkedList A = new LinkedList();

        // 2. for each vertex v E V[G]
        // 3.    do Make-Set(v)

        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
        {
            Vertex v = vxiter.next();
            v.representative = v; // I am in my set
            v.members = new LinkedList(); // I have no members in my set
        }

        // 4. sort the edges of E by nondecreasing weight w
        // Creates the edges objects
        //int j;
        LinkedList Vneighbors = new LinkedList();
        //Vertex u;
        
          //dja - this was added to support GnR and GR because there are no
        //      edge objects b4 this point.
        EdgeIter dummyIter = getEdges();

        // Sort the Edges in non decreasing order
        sortEdges( new Comparator() {
            public int compare( Object o1, Object o2 )
                 {
                Edge e1 = ( Edge )o1;
                Edge e2 = ( Edge )o2;
                if ( e1.getWeight() < e2.getWeight() )
                    return -1;
                if ( e1.getWeight() == e2.getWeight() )
                    return 0;
                return 1;
            }

        } );

        // 5. for each edge in the nondecresing order
        Vertex vaux, urep, vrep;

        for( EdgeIter edgeiter = getEdges(); edgeiter.hasNext(); )
        {
            // 6. if Find-Set(u)!=Find-Set(v)
            EdgeIfc e1 = edgeiter.next();
            Vertex u = e1.getStart();
            Vertex v = e1.getEnd();

            if ( ! ( v.representative.getName() ).equals( u.representative.getName() ) )
              {
                // 7. A <- A U {(u,v)}
                A.add( e1 );

                // 8. Union(u,v)
                urep = u.representative;
                vrep = v.representative;

                if ( ( urep.members ).size() > ( vrep.members ).size() )
                    { // we add elements of v to u
                    for( int j=0; j< ( vrep.members ).size(); j++ )
                          {
                        vaux = ( Vertex ) ( vrep.members ).get( j );
                        vaux.representative = urep;
                        ( urep.members ).add( vaux );
                    }
                    v.representative = urep;
                    vrep.representative = urep;
                    ( urep.members ).add( v );
                    if ( !v.equals( vrep ) )
                        ( urep.members ).add( vrep );
                    ( vrep.members ).clear();
                }
                else
                     { // we add elements of u to v
                    for( int j=0; j< ( urep.members ).size(); j++ )
                           {
                        vaux = ( Vertex ) ( urep.members ).get( j );
                        vaux.representative = vrep;
                        ( vrep.members ).add( vaux );
                    }
                    u.representative = vrep;
                    urep.representative = vrep;
                    ( vrep.members ).add( u );
                    if ( !u.equals( urep ) )
                        ( vrep.members ).add( urep );
                    ( urep.members ).clear();

                } // else

            } // of if

        } // of for numedges

        // 9. return A
        // Creates the new Graph that contains the SSSP
        String theName;
        Graph newGraph = new  Graph();

        // Creates and adds the vertices with the same name
        for ( VertexIter vxiter = getVertices(); vxiter.hasNext(); )
      {
            theName = vxiter.next().getName();
            newGraph.addVertex( new  Vertex().assignName( theName ) );
        }

        // Creates the edges from the NewGraph
        Vertex theStart, theEnd;
        Vertex theNewStart, theNewEnd;
        EdgeIfc   theEdge;

        // For each edge in A we find its two vertices
        // make an edge for the new graph from with the correspoding
        // new two vertices
        for( int i=0; i<A.size(); i++ )
       {
            // theEdge with its two vertices
            //dja - changed to generalized for GNR and GR
            theEdge = ( EdgeIfc )A.get( i );
            theStart = theEdge.getStart();
            theEnd = theEdge.getEnd();

            // Find the references in the new Graph
            theNewStart = newGraph.findsVertex( theStart.getName() );
            theNewEnd = newGraph.findsVertex( theEnd.getName() );

            // Creates the new edge with new start and end vertices
            // in the newGraph
            // and ajusts the adorns based on the old edge
            // Adds the new edge to the newGraph
            EdgeIfc theNewEdge = newGraph.addEdge( theNewStart, theNewEnd );
            theNewEdge.adjustAdorns( theEdge );
        //            Edge theNewEdge = ( Edge ) newGraph.addEdge( theNewStart, theNewEnd );
        //            theNewEdge.adjustAdorns( ( Edge )  theEdge );
        }
        return newGraph;

    }
      // inherited constructors



    public Graph$$MSTKruskal (  ) { super(); } // kruskal

}



public class Graph extends  Graph$$MSTKruskal  {
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
