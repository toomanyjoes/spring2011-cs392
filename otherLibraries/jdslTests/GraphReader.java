package jdslTests;

import java.io.*;
import java.util.*;
import jdsl.graph.api.*;

public class GraphReader {
    private Reader inFile;
    private boolean directed;
    private boolean weighted;
    private String filename;
    List<Vertex[]> eList = new ArrayList<Vertex[]>();
    
    public GraphReader(boolean directed, boolean weighted, String filename) {
        this.directed = directed;
        this.weighted = weighted;
        this.filename = filename;
    }
    
    public List<Integer> readGraph(Graph g) {
        openFile();
        
        int numVertices = readNumber();
        int numEdges = readNumber();
        readNumber(); 
        readNumber();
        readNumber();

        List<Vertex> vList = new ArrayList<Vertex>();
        for (int i = 0; i < numVertices; i++)
        {
            Object curr = new Object();
            Vertex v = g.insertVertex(curr);
            vList.add(v);
        }

        //List<Edge> eList = new ArrayList<Edge>();
        for(int i=0; i < numEdges; i++)
        {
            int start_idx = readNumber();
            int end_idx = readNumber();
            Vertex start = vList.get(start_idx);
            Vertex end = vList.get(end_idx);
            Edge edge = g.insertEdge(start, end, new Integer(i));
            Vertex[] a = {start, end};
            eList.add(a);
        }
        
        List<Integer> edgeWeights = new ArrayList<Integer>();
        if(weighted)
        {
            for(int i=0; i < numEdges; i++)
            {
                edgeWeights.add(new Integer(readNumber()));
            }
        }
        closeFile();
        return edgeWeights;
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
