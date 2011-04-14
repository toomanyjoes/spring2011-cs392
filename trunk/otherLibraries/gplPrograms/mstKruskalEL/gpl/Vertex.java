package gpl;

import java.util.Iterator;
import java.util.LinkedList;

import java.lang.Integer;
import java.util.Collections;
import java.util.Comparator;



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



// of Graph

  // *************************************************************************

public class Vertex extends  Vertex$$Undirected  {
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
