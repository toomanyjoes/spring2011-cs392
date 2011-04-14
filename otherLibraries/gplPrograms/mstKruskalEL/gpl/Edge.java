package gpl;

import java.util.LinkedList;



// *************************************************************************

abstract class Edge$$Undirected extends Neighbor implements EdgeIfc {
    public Vertex start;

    public void EdgeConstructor( Vertex the_start, Vertex the_end )
    {
        start = the_start;
        end = the_end;
    }

    public void adjustAdorns( EdgeIfc the_edge ) 
    {}

    public void setWeight( int weight ) 
    {}

    public int getWeight() 
    {
        return 0;
    }

    public Vertex getOtherVertex( Vertex vertex )
    {
        if( vertex == start )
        {
            return end;
        }
        else
            if( vertex == end )
        {
                return start;
            }
            else
        {
                return null;
            }
    }

    public Vertex getStart()
    {
        return start;
    }

    public Vertex getEnd()
    {
        return end;
    }

    public void display() 
    {
        System.out.println( " start=" + start.getName() + " end=" + end.getName() );
    }
}



// *************************************************************************

public class Edge extends  Edge$$Undirected  {
    private int weight;

    public void EdgeConstructor( Vertex the_start,  Vertex the_end,
                int the_weight ) {
        super.EdgeConstructor( the_start,the_end );
        weight = the_weight;
    }

    // Constructor Loophole removed
    // public void EdgeConstructor($TEqn.Vertex the_start,
    //                    $TEqn.Vertex the_end) {
    // Super($TEqn.Vertex, $TEqn.Vertex).EdgeConstructor(the_start,the_end);
    // }

    public void adjustAdorns( Edge the_edge ) {
        setWeight( the_edge.getWeight() );
        super.adjustAdorns( the_edge );
    }

    public void setWeight( int weight )
    {
        ((Edge) this).weight = weight;
    }

    public int getWeight()
    {
        return ((Edge) this).weight;
    }

    public void display() {
        System.out.print( " Weight=" + weight );
        super.display();
    }

}
