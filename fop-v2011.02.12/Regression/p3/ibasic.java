/* this java file illustrates the basic organization of classes that will
   be generated by P3.
*/

class ibasic {

   public static void main( String args[] ) {
      invcont1  ic;
      invcursor all;
      invcursor edison;
      invcursor france;
      invcursor germany;
      inventor  obj;
      int i;

      ic = new invcont1();
      all = new all( ic );
      edison = new edison_cursor( ic );
      france = new france_cursor( ic );
      germany = new germany_cursor( ic );

      System.out.println("inserting inventor data\n");

      for (i=0; i<inventor.rawdata.length; i++) {
         all.insert(inventor.rawdata[i]);
      }

      System.out.println("--------------");
      System.out.println("Inventions by edison");
      for (edison.first(); edison.more(); edison.next())
         edison.obj().print();

      System.out.println("--------------");
      System.out.println("Inventions from France");
      for (france.first(); france.more(); france.next())
         france.obj().print();

      System.out.println("--------------");
      System.out.println("Inventions from Germany");
      for (germany.first(); germany.more(); germany.next())
         germany.obj().print();

      System.out.println("--------------");
      System.out.println("All inventions");
      for (all.first(); all.more(); all.next())
         all.obj().print();

      System.out.println("--------------");
      System.out.println("Delete all inventions");
      for (all.first(); all.more(); all.next())
         all.remove();

      System.out.println("--------------");
      System.out.println("All inventions");
      for (all.first(); all.more(); all.next())
         all.obj().print();
   }
} 
