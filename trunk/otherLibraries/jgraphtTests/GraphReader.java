package jgraphtTests;

import java.io.*;
import java.util.*;
import org.jgrapht.*;

public class GraphReader {
    private Reader inFile;
    private boolean directed;
    private boolean weighted;
    private String filename;
    
    public GraphReader(boolean weighted, String filename) {
        this.weighted = weighted;
        this.filename = filename;
    }
    
    public void readGraph(Graph g) {
        openFile();
        
        int numVertices = readNumber();
        int numEdges = readNumber();
        readNumber(); 
        readNumber();
        readNumber();

        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < numVertices; i++)
        {
            Object curr = new Object();
            vList.add(curr);
            g.addVertex(curr);
        }

        for(int i=0; i < numEdges; i++)
        {
            int start_idx = readNumber();
            int end_idx = readNumber();
            Object start = vList.get(start_idx);
            Object end = vList.get(end_idx);
            g.addEdge(start, end);
        }
        
        closeFile();
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
