package gpl;

import java.lang.Integer;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;



// of vertex
   
   // *************************************************************************
    
public class Edge extends Neighbor implements EdgeIfc {
    public  Vertex start;
       
    public Edge( Vertex the_start,  Vertex the_end, int the_weight )
      {
        neighbor = the_end;
        setWeight( the_weight );
        start = the_start;
    } // Edge constructor

    public Vertex getStart()
    {
        return start;
    }
}
