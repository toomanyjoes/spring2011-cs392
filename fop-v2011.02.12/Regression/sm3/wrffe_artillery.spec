State_machine wrffe_artillery extends class wrffe {

   Delivery_parameters(M m);
   Unrecognizable_state { error(-1,m); }

   Otherwise_default { ignore_message(current_state, m); }

   States one, two;

   Prepare start { /* determine unit capability */; unit_capable = true; }

   Transition t1 : start -> one
   conditions m.msg == M.initialize && unit_capable
   do { /* set loadtime; downstream(accept) */;  }

   Transition t2 : start -> stop
   conditions m.msg == M.initialize && !unit_capable
   do { /* downstream(deny) */; }

   Transition t3 : one -> two
   conditions m.msg == M.loadtime
   do { /* set shot, set flighttime, set completetime */ }

   Prepare two { high_angle = true; }

   Transition t4 : two -> two
   conditions m.msg == M.flighttime && high_angle
   do { /* downstream(splash) */; }

   Transition t5 : two -> two
   conditions m.msg == M.flighttime && !high_angle
   do { ; }

   Transition t6 : two -> two
   conditions m.msg == M.completetime
   do { /* downstream(roundsCompleted) */; }

   Transition t7 : two -> stop
   conditions m.msg == M.eom
   do { /* downstream(mfr) */ }

   Enter stop { Goto_state start(m); }  // debugging only

   boolean high_angle, unit_capable;

   wrffe_artillery() { }

   static int array[] = { M.initialize, M.loadtime, M.flighttime, 
                          M.completetime, M.eom,
                          M.initialize, M.loadtime, M.completetime, M.eom };

   public static void main(String args[]) {
      wrffe_artillery sd = new wrffe_artillery();
      int i;
      M m = new M();

      for (i=0; i<array.length; i++) {
         m.msg = array[i];
         sd.delivery(m);
         System.out.println(sd.getState());
      }
   }
}
