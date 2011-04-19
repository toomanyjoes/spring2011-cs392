package yfilesTests;

import java.io.*;
import java.util.*;
//import demo.base.RandomGraphGenerator;
import y.algo.*;
import y.base.*;
import y.util.*;
import y.util.Maps.*;

public class GraphReader {
    private Reader inFile;
    private boolean directed;
    private boolean weighted;
    private String filename;
    
    public GraphReader(boolean weighted, String filename) {
        this.weighted = weighted;
        this.filename = filename;
    }
    
    // returns a DataProvider for edge weights
    public DataProvider readGraph(Graph g) {
        openFile();
        
        int numVertices = readNumber();
        int numEdges = readNumber();
        readNumber(); 
        readNumber();
        readNumber();

        List<Node> vList = new ArrayList<Node>();
        for (int i = 0; i < numVertices; i++)
        {
            Node curr = g.createNode();
            vList.add(curr);
            //g.addVertex(curr);
        }

        List<Edge> eList = new ArrayList<Edge>();
        for(int i=0; i < numEdges; i++)
        {
            int start_idx = readNumber();
            int end_idx = readNumber();
            Node start = vList.get(start_idx);
            Node end = vList.get(end_idx);
            //Node[] edge = {start, end};
            Edge edge = g.createEdge(start, end);
            eList.add(edge);
        }

        double[] weights = new double[numEdges];
        for(int i = 0; i < numEdges; i++) weights[i] = 1;
        if(weighted)
        {
            for(int i=0; i < numEdges; i++)
            {
                weights[i] = (double)readNumber();
                //Object[] edge = eList.get(i);
                //g.addEdge(new MyLink(weight), edge[0], edge[1]);
            }
        }
        
        closeFile();
        return DataProviders.createEdgeDataProvider(weights);
    }

    private boolean openFile()
    {
        try 
        {
            inFile = new FileReader( filename );
        }
        catch ( IOException e )
        {
            System.out.println( "Your file " + filename + " cannot be read" );
            return false;
        }
        return true;
    }
    
    private void closeFile() {
        try
        {
            inFile.close();
        }
        catch(Exception e)
        { }
    }
    
    private int readNumber()
    {
        String theString = null;
        try {
        int index = 0;
        char[ ] word = new char[ 80 ];
        int ch = 0;

        ch = inFile.read();
        while( ch==32 )
        {
            ch = inFile.read(); // skips extra whitespaces
        }

        while( ch != -1 && ch != 32 && ch != 10 ) // while it is not EOF, WS, NL
        {
            word[ index++ ] = ( char )ch;
            ch = inFile.read();
        }
        word[ index ] = 0;

        theString = new String( word );

        theString = new String( theString.substring( 0,index ) ).trim();
        } catch(Exception e) { }
        return Integer.parseInt( theString,10 );
    }
}
