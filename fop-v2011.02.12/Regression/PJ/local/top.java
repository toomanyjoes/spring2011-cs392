
package Ctop; 

import Jakarta.util.*;

import java.util.*;

class top implements java.io.Serializable {
   static int i$$Ctop,j$$Ctop;

   static int i$$Cmid,j$$Cmid;

   static { i$$Ctop = 4; }

   static { i$$Cmid = 4; }
   int ii$$Ctop,jj$$Ctop;
   int ii$$Cmid,jj$$Cmid;

   top() {  ii$$Ctop = 5; }
   top(int rj) { jj$$Ctop = rj; } final

   float bar$$Ctop( float x ) { return (float) 5.0; }

   float bar( float x ) { 
       bar$$Ctop(x);
       return (float) 4.0; 
   }

   void foo$$Ctop(float x, float y) { /* do something */ }

   void foo$$Cmid(float x, float y) { /* do something */ }
}