// tests composition of extensions that reference methods

layer ext111;

import firstPackage.*;

refines State_machine root {

   void fromext11() { }
}
   

