
/* this should test the alias contruct in multiple ways */

import java.io.PrintWriter;
import jak2java.* ;

class alias_test {
    static AstProperties props;
    static PrintWriter pw;

    public static void printcode( AST_Class code ) {
	code.reduce2java(alias_test.props);
	pw.flush();
	pw.println("\n");
    }

    public static void main(String argv[]) {

	foo f = new foo(4);
	AstProperties props;

	// Step 1:  initialize output stream

	props = new AstProperties();
	pw = new PrintWriter(System.out);
	props.setProperty("output", pw);

	// Step 2: try some simple stuff and then test augment

	f.gen_expression1().reduce2java(props); pw.print("\n");
	f.gen_expression2().reduce2java(props); pw.print("\n");

	// Step 3: now try a simple test of alias

	f.atest1().reduce2java(props); pw.print("\n");
	f.atest2().reduce2java(props); pw.print("\n");

	// Step 4: now try a more complex test

	f.atest3().reduce2java(props); pw.print("\n");
	f.atest4().reduce2java(props); pw.print("\n");
	f.atest5().reduce2java(props); pw.print("\n");
	pw.flush();
    }
}

class foo {
    environment i, j, loop;
    int i;

    foo(int k) {
	String varname = "k";
	i = k;

	// adds identifier "k" to current environment
	environment augment varname;
    }

    AST_Exp gen_expression1() {
	// should return i_# + j_#
	return exp{ i + j }exp;
    }

    AST_Exp gen_expression2() {
	// should return i_# + j_# + k_#
	return exp{ i + j + k }exp;
    }

    AST_Exp atest1() {
	// should return i_# + j_# + k_#
	AST_Exp x = gen_expression2();
      
	alias( i, x );

	// should return i_# + j_# + k_# + j_# + k_#
	return exp{ i + j + k }exp;
    }

    AST_Exp atest2() {
	AST_Exp x = exp{ 4*5 + 7 }exp;
      
	alias( j, x );

	// should return i_# + j_# + k_# + 4*5 + 7 + k_#
	return exp{ i + j + k }exp;
    }

    AST_Stmt atest3() {
	AST_Stmt z = stm{ for (q=0; q<30; q++) { funcall(q); } }stm;

	alias( loop, z );
	return stm{ v = 0; loop; v = 1; }stm;  // returns v = 0; <loop>; v = 1
    }

    // Note: we still don't have the Alias construct quite right
    // we can only alias identifiers that were predeclared
    // not augmented... (but this will have to wait until later)

    AST_Stmt atest4() {
	bar b = new bar(this,5);
	return b.atest4();
    }

    AST_FieldDecl atest5() {
	bar b = new bar(this,5);
	return b.atest5();
    }
}

class bar {
    environment;
    int n;

    bar( foo f, int y ) { 
	environment parent f;
	n = y;
    }

    AST_Stmt atest4() {
	return stm{ v = 0; loop; v = 1; }stm;  // returns v = 0; <loop>; v = 1
    }

    AST_FieldDecl atest5() {
	// should generate a run-time error; i is aliased to be an expression
	return mth{ int q; AST_Exp x() { int r = i; } }mth;
    }

    // note; replace return mth{ ... }mth with return mth{ int q; AST_Exp x()
    // { int r = i; } }mth
    // should return "int q; AST_Exp x() { int r = i_# + j_# + k_#; }"
}
