package gpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;



// of Graph
   
  // *************************************************************************   
   
abstract class Vertex$$Directed {
    public LinkedList adjacentNeighbors;
    public String name;
   
    public Vertex$$Directed() {
        VertexConstructor();
    }
    public String getName() 
    {
        return name;
    }

    public void VertexConstructor() {
        name      = null;
        adjacentNeighbors = new LinkedList();
    }

    public  Vertex assignName( String name ) {
        ((Vertex) this).name = name;
        return ( Vertex ) ((Vertex) this);
    }
   
    public void addEdge( Neighbor n ) {
        adjacentNeighbors.add( n );
    }

    public VertexIter getNeighbors() 
    {
        return new VertexIter() {
            private Iterator iter = adjacentNeighbors.iterator();
            public Vertex next() 
            {
                return ( ( Neighbor )iter.next() ).neighbor;
            }

            public boolean hasNext() 
            {
                return iter.hasNext();
            }
        };
    }

    public void adjustAdorns( Neighbor sourceNeighbor )
      {}
      
    public void display() 
    {
        System.out.print( "Node " + getName() + " connected to: " );

        for( VertexIter vxiter = getNeighbors(); vxiter.hasNext(); )
         {
            Vertex v = vxiter.next();
            System.out.print( v.getName() + ", " );
        }
        System.out.println();
    }
}



// *************************************************************************

abstract class Vertex$$DFS extends  Vertex$$Directed  {
    public boolean visited;

    public void VertexConstructor() 
    {
        super.VertexConstructor();
        visited = false;
    }

    public void init_vertex( WorkSpace w ) 
    {
        visited = false;
        w.init_vertex( ( Vertex ) ((Vertex) this) );
    }

    public void nodeSearch( WorkSpace w ) 
    {
        Vertex v;

        // Step 1: Do preVisitAction.
        //            If we've already visited this node return
        w.preVisitAction( ( Vertex ) ((Vertex) this) );

        if ( visited )
            return;

        // Step 2: else remember that we've visited and
        //         visit all neighbors
        visited = true;

        for ( VertexIter  vxiter = getNeighbors(); vxiter.hasNext(); ) 
        {
            v = vxiter.next();
            w.checkNeighborAction( ( Vertex ) ((Vertex) this), v );
            v.nodeSearch( w );
        }

        // Step 3: do postVisitAction now
        w.postVisitAction( ( Vertex ) ((Vertex) this) );
    } // of dftNodeSearch

    public void display() {
        if ( visited )
            System.out.print( "  visited" );
        else
            System.out.println( " !visited" );
        super.display();
    }
      // inherited constructors


   
    public Vertex$$DFS (  ) { super(); }
}



// of Graph

  // ***********************************************************************
   
public class Vertex extends  Vertex$$DFS  {
    public int finishTime;
    public int strongComponentNumber;
      
    public void display() {
        System.out.print( " FinishTime -> " + finishTime + " SCCNo -> " 
                        + strongComponentNumber );
        super.display();
    }
      // inherited constructors


   
    public Vertex (  ) { super(); }
}
