
SoUrCe RooT foo "xxx";

State_machine refinedTransitionParent1 extends class wrffe {

   Delivery_parameters(M m);
   Unrecognizable_state {}
   Otherwise_default {}

   States one, two;

   Transition e1: start -> one
   condition false do { System.out.println("1"); }

   Transition e2 : one -> two
   condition false do { System.out.println("2"); }

   Transition e22: two -> two
   condition m.msg == M.two do { System.out.println("22"); }

   Transition e3 : two -> stop 
   condition false do { System.out.println("3"); }
}


SoUrCe foo1 "yxxx";

State_machine er2 extends refinedTransitionParent1 {

   Transition_condition e1 Proceed(m) || m.msg == M.one ;
   Transition_action e1 { System.out.print("following "); Proceed(m); }

   Transition_condition e2 Proceed(m) || m.msg == M.two ;
   Transition_action e2 { System.out.print("following "); Proceed(m); }

   Transition_condition e22 Proceed(m) || m.msg == M.four ;
   Transition_action e22 { System.out.print("following "); Proceed(m); }

   Transition_condition e3 Proceed(m) || m.msg == M.eom ;
   Transition_action e3 { System.out.print("following "); Proceed(m); }

   static int array[] = { M.one, M.two, M.two, M.four, M.eom };

   public static void main(String args[]) {
      er2 sd = new er2();
      int i;
      M m = new M();

      for (i=0; i<array.length; i++) {
         m.msg = array[i];
         sd.delivery(m);
         System.out.println(sd.getState());
      }
   }
}

