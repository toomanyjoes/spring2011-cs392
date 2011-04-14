package gpl;

import java.util.LinkedList;



// Vertex class

 // *************************************************************************

abstract class Neighbor$$Undirected implements EdgeIfc, NeighborIfc {
    public  Vertex neighbor;

    // This constructor has to be present here so that the default one
    // Called on Weighted can call it, i.e. it is not longer implicit
    public Neighbor$$Undirected()  {
        neighbor = null;
    }

    public Neighbor$$Undirected( Vertex theNeighbor )
   {
        NeighborConstructor( theNeighbor );
    }

    public void setWeight( int weight ) {}
    public int getWeight() {
        return 0;
    }

    public void NeighborConstructor( Vertex theNeighbor ) {
        neighbor = theNeighbor;
    }

    public void display()
    {
        System.out.print( neighbor.name + " ," );
    }

    public Vertex getStart() {
        return null;
    }
    public Vertex getEnd() {
        return neighbor;
    }

    public Vertex getOtherVertex( Vertex vertex )
    {
        return neighbor;
    }

    public void adjustAdorns( EdgeIfc the_edge )
    {}
}



// end of Vertex class
 
  // *************************************************************************
  
public class Neighbor extends  Neighbor$$Undirected  {
    public int weight;

    public Neighbor( Vertex theNeighbor, int theWeight ) {
        NeighborConstructor( theNeighbor, theWeight );
    }

    public void NeighborConstructor( Vertex theNeighbor, int theWeight )
    {
        super.NeighborConstructor( theNeighbor );
        weight = theWeight;
    }

    public void display()
    {
        System.out.print( " Weight = " + weight + " " );
        super.display();
    }

    public void setWeight( int weight )
    {
        ((Neighbor) this).weight = weight;
    }

    public int getWeight()
    {
        return ((Neighbor) this).weight;
    }
      // inherited constructors



    public Neighbor (  Vertex theNeighbor ) { super(theNeighbor); }

    // This constructor has to be present here so that the default one
    // Called on Weighted can call it, i.e. it is not longer implicit
    public Neighbor (  ) { super(); }

}
