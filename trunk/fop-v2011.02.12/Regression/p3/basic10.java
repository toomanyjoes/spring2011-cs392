/* this java file illustrates the basic organization of classes that will
   be generated by P3.
*/

class basic10 {

   public static void main( String args[] ) {
      empcont1  ec;
      t0        c0;
      t1        c1;
      t2        c2;
      t3        c3;
      emp       obj;

      emp[] rawdata = {
                         new emp( 10000,60,"Biology","Akers, Mark" ),
                         new emp( 10070,22,"CompSci","Andrews, Kay" ),
                         new emp( 10020,21,"Biology","Alexander, Joe" ),
                         new emp( 10010,40,"Physics","Akin, Monica" ),
                         new emp( 10050,42,"Biology","Akerson, Suzanne" ),
                         new emp( 10040,53,"Astrono","Akerson, Mary" ),
                         new emp( 10060,61,"CompSci","Andrews, John" ),
                         new emp( 10030,23,"Biology","Akerson, Gwyn" )
                      };
      int i;

      ec = new empcont1();
      c0 = new t0( ec );
      c1 = new t1( ec );
      c2 = new t2( ec );
      c3 = new t3( ec, 23 );

      System.out.println();
      System.out.println("original employee data \n");
      for (i=0; i<rawdata.length; i++) {
         c0.insert(rawdata[i]);
         c0.obj().print();
      }

      System.out.println();
      System.out.println("read again \n");    
      for (c0.first(); c0.more(); c0.next()) {
         c0.obj().print();
      }

      System.out.println();
      System.out.println("age() > 30 && age() < 50 \n");
      for (obj = c1.first(); c1.more(); obj = c1.next()) {
         obj.print();
      }

      System.out.println();
      System.out.println("increase those age() == 22 \n");
      for (c2.first(); c2.more(); c2.next()) {
         c2.obj().print();
         c2.obj().birthday();
      }

      System.out.println();
      System.out.println("read again \n");    
      for (c0.first(); c0.more(); c0.next()) {
         c0.obj().print();
      }

      System.out.println();
      System.out.println("delete those age() == 23 \n");
      for (c3.first(); c3.more(); c3.next()) {
         c3.obj.print();
         c3.remove();
      }

      System.out.println();
      System.out.println("delete those age() == 60 \n");
      c3.x = 60;
      for (c3.first(); c3.more(); c3.next()) {
         c3.obj.print();
         c3.remove();
      }

      System.out.println();
      System.out.println("print remaining emps \n");
      for (c0.first(); c0.more(); c0.next()) {
         c0.obj().print();
      }

      System.out.println();
      System.out.println("Done");
   }
} 