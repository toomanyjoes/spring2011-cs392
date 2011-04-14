package gpl;

import java.util.Iterator;
import java.util.LinkedList;

import java.lang.Integer;



// *********************************************************************

abstract class Vertex$$Undirected {
    public LinkedList neighbors;
    public String name;

    public Vertex$$Undirected() 
    {
        VertexConstructor();
    }

    public void VertexConstructor() 
    {
        name      = null;
        neighbors = new LinkedList();
    }

    public  Vertex assignName( String name ) 
    {
        ((Vertex) this).name = name;
        return ( Vertex ) ((Vertex) this);
    }

    public String getName()
    {
        return ((Vertex) this).name;
    }

    public LinkedList getNeighborsObj()
    {
        return neighbors;
    }

    public VertexIter getNeighbors()
    {
        return new VertexIter() {
            private Iterator iter = neighbors.iterator();
            public Vertex next() 
            {
                return ( ( Neighbor )iter.next() ).end;
            }
            public boolean hasNext() 
            {
                return iter.hasNext();
            }
        };
    }

    public void display() 
    {
        System.out.print( " Node " + name + " connected to: " );

        for ( VertexIter vxiter = getNeighbors(); vxiter.hasNext(); )
        {
            System.out.print( vxiter.next().getName() + ", " );
        }

        System.out.println();
    }
    //--------------------
    // differences
    //--------------------

    public void addNeighbor( Neighbor n ) 
    {
        neighbors.add( n );
    }

    public EdgeIter getEdges()
    {
        return new EdgeIter() {
            private Iterator iter = neighbors.iterator();
            public EdgeIfc next() 
            {
                return ( ( EdgeIfc ) ( ( Neighbor )iter.next() ).edge );
            }
            public boolean hasNext() 
            {
                return iter.hasNext();
            }
        };
    }

}



// *************************************************************************

abstract class Vertex$$DFS extends  Vertex$$Undirected  {
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



// *************************************************************************
   
public class Vertex extends  Vertex$$DFS  {
    public int VertexCycle;
    public int VertexColor; // white ->0, gray ->1, black->2
      
    public void display() {
        System.out.print( " VertexCycle# " + VertexCycle + " " );
        super.display();
    }
      // inherited constructors



    public Vertex (  ) { super(); }
}
