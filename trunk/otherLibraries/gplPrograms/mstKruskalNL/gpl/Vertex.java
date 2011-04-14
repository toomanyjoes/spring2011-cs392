package gpl;

import java.util.LinkedList;
import java.util.Iterator;

import java.lang.Integer;
import java.util.Collections;
import java.util.Comparator;



// **********************************************************************   
   
abstract class Vertex$$Undirected {
    public LinkedList adjacentNeighbors;
    public String name;

    public Vertex$$Undirected() 
    {
        VertexConstructor();
    }
      
    public void VertexConstructor() 
    {
        name      = null;
        adjacentNeighbors = new LinkedList();
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
        return adjacentNeighbors;
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

    public void display() 
    {
        System.out.print( "Node " + name + " connected to: " );

        for ( VertexIter vxiter = getNeighbors(); vxiter.hasNext(); )
        {
            System.out.print( vxiter.next().getName() + ", " );
        }

        System.out.println();
    }
    //--------------------
    // differences
    //--------------------

    public void addEdge( Neighbor n ) 
    {
        adjacentNeighbors.add( n );
    }

    public void adjustAdorns( Neighbor sourceNeighbor )
    {}

    public EdgeIter getEdges()
    {
        return new EdgeIter() {
            private Iterator iter = adjacentNeighbors.iterator();
            public EdgeIfc next() 
            {
                return ( Neighbor ) iter.next();

            //              return ( ( EdgeIfc ) ( ( Neighbor )iter.next( ) ).edge );
            }
            public boolean hasNext() 
            {
                return iter.hasNext();
            }
        };
    }

}



// of Graph
 
// *************************************************************************
 
abstract class Vertex$$Weighted extends  Vertex$$Undirected  {
    public void addWeight( Vertex end, int theWeight ) 
    {
        Neighbor the_neighbor = 
                ( Neighbor ) ( end.adjacentNeighbors ).removeLast();
        the_neighbor.weight = theWeight;
        ( end.adjacentNeighbors ).add( the_neighbor );
    }
    
    public void adjustAdorns( Neighbor sourceNeighbor )
     {
        Neighbor targetNeighbor = 
                ( Neighbor )adjacentNeighbors.getLast();
        targetNeighbor.weight = sourceNeighbor.weight;
        super.adjustAdorns( sourceNeighbor );
    }
    
    public void display()
    {
        super.display();
    }
      // inherited constructors



    public Vertex$$Weighted (  ) { super(); }

}



// of Graph

  // *************************************************************************

public class Vertex extends  Vertex$$Weighted  {
    public  Vertex representative;
    public LinkedList members;

    public void display() {
        if ( representative == null )
            System.out.print( "Rep null " );
        else
            System.out.print( " Rep " + representative.getName() + " " );
        super.display();
    }
      // inherited constructors



    public Vertex (  ) { super(); }
}
