package webGuiDsl;
import guidsl.*;
import java.util.*;

public class WebGuidsl{
   public static void main( String args[] ) {
      String modelFile = "model.m";
      List<String> argList = new ArrayList(args.length);
      argList.addAll(Arrays.asList(args));
      
      int firstFeatureIdx = 0;
      try {
        if(argList.contains("-m")) {
            if(argList.indexOf("-m") != 0 || argList.size() == 1) {
                printUsage();
                System.exit(1);
            }
            else {
                modelFile = argList.get(1);
                firstFeatureIdx = 2;
            }
        }
      } catch(Exception e) {
        printUsage();
        System.exit(1);
      }
   
      WebTool tool = new WebTool( modelFile );

      // print to stdout and let the CGI script deal with it
      System.out.print(tool.modelExplanation(argList.subList(firstFeatureIdx,argList.size())));
   }
   
   private static void printUsage() {
        System.out.println("Usage: java WebGuidsl -m <modelfile.m> selectedFeature1 ... selectedFeatureN");
   }
}
