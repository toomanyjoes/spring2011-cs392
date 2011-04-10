package jgraphtTests;

import java.io.*;

public class GraphReader {
    private Reader inFile;

    public boolean openFile(String FileName)
    {
        try 
        {
            inFile = new FileReader( FileName );
        }
        catch ( IOException e )
        {
            System.out.println( "Your file " + FileName + " cannot be read" );
            return false;
        }
        return true;
    }
    
    public void closeFile() {
        try
        {
            inFile.close();
        }
        catch(Exception e)
        { }
    }
    
    public int readNumber()
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
