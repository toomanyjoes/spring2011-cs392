layer Cycle;

import  java.lang.Integer;



  // *************************************************************************
   public
refines class Vertex {
      public int VertexCycle;      
	  public int VertexColor; // white ->0, gray ->1, black->2
	  
      public void display() {
         System.out.print(" VertexCycle# " + VertexCycle + " ");
         Super().display();
      }
   }
