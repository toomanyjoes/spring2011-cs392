package composer.unit ;

import composer.algebra.AlgebraException ;
import composer.algebra.Unit ;

import java.io.File ;
import java.io.IOException ;

import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.List ;

import mixin.Main ;

class MixinFileUnitComposeFunction extends FileUnitComposeFunction {

    public MixinFileUnitComposeFunction (Factory fact, List src, Class klass) {
	super (fact, src, klass) ;
    }

    public void invoke (Unit targetUnit) throws AlgebraException {

	File target = ((FileUnit) targetUnit) . getFile () ;
	writeEquation (target) ;

	List args = new ArrayList (6 + getSources().size ()) ;

	String aspect = aspectName (target) ;
	if (aspect != null) {
	    args.add ("-a") ;
	    args.add (aspect) ;
	} else {
	    String msg = fileMessage (target, "doesn't provide aspect name") ;
	    getLogger().warning (msg) ;
	}

	args.add ("-f") ;
	args.add (target.getPath ()) ;

	String model = ((Factory) getFactory ()) . getModelPath () ;
	if (model != null) {
	    args.add ("-model") ;
	    args.add (model) ;
	} else {
	    String msg = fileMessage (target, "invoked without model path") ;
	    getLogger().warning (msg) ;
	}

	for (Iterator p = getSources().iterator () ; p.hasNext() ; )
	    args.add (((FileUnit) p.next ()) . getFile() . getPath ()) ;

	StringBuffer buffer = new StringBuffer ("mixin") ;
	for (Iterator p = args.iterator () ; p.hasNext () ; )
	    buffer.append(' ').append (p.next ()) ;
	getLogger().info (buffer.toString ()) ;

	try {
	    backupFile (target) ;
	    String[] argArray = new String [args.size()] ;
	    mixin.Main.main ( (String[]) args.toArray (argArray) ) ;

	} catch (Exception except) {
	    throw new AlgebraException ("failed mixin " + args,	except) ;
	}
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    MixinFileUnitComposeFunction (Factory factory, List sources) {
	this (factory, sources, MixinFileUnit.class) ;
    }

}
