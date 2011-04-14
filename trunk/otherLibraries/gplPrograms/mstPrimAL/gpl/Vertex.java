package gpl;

import java.util.Iterator;
import java.util.LinkedList;

import java.lang.Integer;
import java.util.Collections;
import java.util.Comparator;



// ************************************************************

abstract class Vertex$$Undirected implements EdgeIfc, NeighborIfc {
    public LinkedList adjacentVertices;
    public String name;

    public Vertex$$Undirected()
    {
        VertexConstructor();
    }

    public void VertexConstructor()
    {
        name      = null;
        adjacentVertices = new LinkedList();
    }

    public  Vertex assignName( String name )
    {
        ((Vertex) this).name = name;
        return ( Vertex ) ((Vertex) this);
    }

    public VertexIter getNeighbors()
    {
        return new VertexIter() {
            private Iterator iter = adjacentVertices.iterator();
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
        int s = adjacentVertices.size();
        int i;

        System.out.print( "Vertex " + name + " connected to: " );
        for ( i=0; i<s; i++ )
            System.out.print( ( ( Vertex ) adjacentVertices.get( i ) ).name
                                                + ", " );
        System.out.println();
    }
    //--------------------
    // differences
    //--------------------

    public void addAdjacent( Vertex n ) {
        adjacentVertices.add( n );
    }

    public void adjustAdorns( Vertex the_vertex, int index )
      {}
    public LinkedList getNeighborsObj()
    {
        return adjacentVertices;
    }

    public EdgeIter getEdges()
    {
        return new EdgeIter() {
            private Iterator iter = adjacentVertices.iterator();
            public EdgeIfc next()
            {
                return ( EdgeIfc ) iter.next();

            //              return ( ( EdgeIfc ) ( ( Neighbor )iter.next( ) ).edge );
            }
            public boolean hasNext()
            {
                return iter.hasNext();
            }
        };
    }

    public String getName()
    {
        return ((Vertex) this).name;
    }

    //--------------------
    // from EdgeIfc
    //--------------------

    public Vertex getStart() {
        return ((Vertex) this);
    }
    public Vertex getEnd() {
        return null;
    }

    public void setWeight( int weight ) {}
    public int getWeight() {
        return 0;
    }

    public Vertex getOtherVertex( Vertex vertex )
    {
        return ((Vertex) this);
    }

    public void adjustAdorns( EdgeIfc the_edge )
    {}

}



// of Graph
 
// The weighted layer needs to extend Vertex to provide a new 
// LinkedList to hold the  weigths  of the edges
// ************************************************************
 
abstract class Vertex$$Weighted extends  Vertex$$Undirected  {
    public LinkedList weightsList;
 
    public void VertexConstructor() {
        super.VertexConstructor();
        weightsList = new LinkedList();
    }
         
    public void addWeight( int weight )
    {
        weightsList.add( new Integer( weight ) );
    }
    
    public void adjustAdorns( Vertex the_vertex, int index )
    {
        int the_weight = ( ( Integer )the_vertex.weightsList.get( index ) ).intValue();
        weightsList.add( new Integer( the_weight ) );
        super.adjustAdorns( the_vertex, index );
    }
    public void setWeight( int weight )
    {
        addWeight( weight );
        Vertex v = ( Vertex ) adjacentVertices.getLast();
        v .addWeight( weight );
    }
                          
    public void display()
    {
        int s = weightsList.size();
        int i;

        System.out.print( " Weights : " );

        for ( i=0; i<s; i++ ) {
            System.out.print( ( ( Integer )weightsList.get( i ) ).intValue() + ", " );
        }

        super.display();
    }
      // inherited constructors



    public Vertex$$Weighted (  ) { super(); }

}



// ***********************************************************************
   
abstract class Vertex$$MSTPrimPrepGR extends  Vertex$$Weighted  {
    private LinkedList edges;
    public EdgeIter getEdges()
    {
        edges = new LinkedList();
        Iterator viter = adjacentVertices.iterator();
        Iterator witer = weightsList.iterator();
        while ( viter.hasNext() )
        {
            Vertex v = ( Vertex ) viter.next();
            Integer i;
            if ( witer.hasNext() )
                i = ( Integer ) witer.next();
            //            Integer i = ( Integer ) witer.next( );
            else
{
                System.out.println( "no weight for: " + ((Vertex) this).name + "-" + v.name );
                i = new Integer( 0 );
            }

            Edge e = new Edge( ((Vertex) this), v, i.intValue() ) ;
            edges.add( e );
        }

        return new EdgeIter() {
            private Iterator iter = edges.iterator();
            public EdgeIfc next()
            {
                return ( EdgeIfc ) iter.next();

            }
            public boolean hasNext()
            {
                return iter.hasNext();
            }
        };
    }
      // inherited constructors



    public Vertex$$MSTPrimPrepGR (  ) { super(); }
}



// ***********************************************************************
   
public class Vertex extends  Vertex$$MSTPrimPrepGR  {
    public String pred; // the predecessor vertex if any
    public int key; // weight so far from s to it
            
    public void display() 
    {
        System.out.print( " Pred " + pred + " Key " + key + " " );
        super.display();
    }
      // inherited constructors



    public Vertex (  ) { super(); }
}
