package GPL;

SoUrCe RooT Number "../GEN/Number/NumberWorkSpace.java";

// *************************************************************************
public class NumberWorkSpace extends WorkSpace {
    int vertexCounter;

    public void NumberWorkSpaceConstructor() {
        vertexCounter = 0;
    }

    public void preVisitAction( Vertex v )
      {
        // This assigns the values on the way in
        if ( v.visited!=true )
            v.VertexNumber = vertexCounter++;
    }

}
