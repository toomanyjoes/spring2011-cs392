//created on: Sun Jul 13 23:04:15 CDT 2003

package gpl;



abstract interface EdgeIfc$$base
{
    public Vertex getStart();
    public Vertex getEnd();
    public void display();

    public void setWeight( int weight );
    //   public int getWeight();

    public Vertex getOtherVertex( Vertex vertex );

    public void adjustAdorns( EdgeIfc the_edge );
}



public interface EdgeIfc extends EdgeIfc$$base
{
    //public void setWeight( int weight );
    public int getWeight();
}
