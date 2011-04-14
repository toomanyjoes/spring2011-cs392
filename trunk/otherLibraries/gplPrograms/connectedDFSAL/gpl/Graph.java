package gpl;

import java.util.Iterator;
import java.util.LinkedList;

//dja: add for performance reasons
import java.util.HashMap;
import java.util.Map;

import java.io.*;



// ************************************************************

abstract class Graph$$Undirected {
    public LinkedList vertices;
    public static final boolean isDirected = false;
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
    public void addAnEdge( Vertex start,  Vertex end, int weight )
    {
        addEdge( start,end );
    }

    // Adds and edge by setting start as adjacent to end and
    // viceversa
    public EdgeIfc addEdge( Vertex start,  Vertex end )
    {
        start.addAdjacent( end );
        end.addAdjacent( start );
        return ( EdgeIfc ) start;
    }

        // Adds an edge without weights if Weighted layer is not present
    //   public void addEdge( Vertex start,   NeighborIfc theNeighbor )
      // {
        //   addEdge( Vertex start,  ( Vertex ) theNeighbor )
      // }

    public void addVertex( Vertex v )
    {
        vertices.add( v );

        //dja: add for performance reasons
        verticesMap.put( v.name, v );
    }

    // Finds a vertex given its name in the vertices list
    public  Vertex findsVertex( String theName )
      {
        int i=0;
        Vertex theVertex;

        // if we are dealing with the root
        if ( theName == null )
            return null;

                  //dja: removed for performance reasons
        //        for( i=0; i<vertices.size(); i++ )
        //        {
        //            theVertex = ( Vertex )vertices.get( i );
        //            if ( theName.equals( theVertex.name ) )
        //                return theVertex;
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

    public void display() {
        int s = vertices.size();
        int i;

        System.out.println( "******************************************" );
        System.out.println( "Vertices " );
        for ( i=0; i<s; i++ )
            ( ( Vertex ) vertices.get( i ) ).display();
        System.out.println( "******************************************" );

    }
    public  EdgeIfc findsEdge( Vertex theSource,
                    Vertex theTarget )
       {
                //dja: performance improvement
        //        for( VertexIter vertexiter = getVertices(); vertexiter.hasNext(); )
        //         {
        //        Vertex v1 = vertexiter.next( );
        //        for( EdgeIter edgeiter = v1.getEdges(); edgeiter.hasNext(); )
        //            {
        //                EdgeIfc theEdge = edgeiter.next();
        //            Vertex v2 = theEdge.getOtherVertex( v1 );
        //              if ( ( v1.getName().equals( theSource.getName() ) &&
        //                       v2.getName().equals( theTarget.getName() ) ) ||
        //                         ( v1.getName().equals( theTarget.getName() ) &&
        //                     v2.getName().equals( theSource.getName() ) ) )
        //                    return theEdge;
        //            }
        //        }
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

}



// **********************************************************************

abstract class Graph$$DFS extends  Graph$$Undirected  {
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



// *****************************************************************
   
abstract class Graph$$Connected extends  Graph$$DFS  {
    // Executes Connected Components
    public void run( Vertex s )
    {
        ConnectedComponents();
        super.run( s );
    }

    public void ConnectedComponents() 
    {
        GraphSearch( new RegionWorkSpace() );
    }
      // inherited constructors



    public Graph$$Connected (  ) { super(); }
}



public class Graph extends  Graph$$Connected  {
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
