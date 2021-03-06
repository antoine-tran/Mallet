/* Copyright (C) 2003 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */
package cc.mallet.grmm.types;


import java.util.Collection;
import java.util.Iterator;

import cc.mallet.util.Randoms;

/**
 * An Abstract class from which new Factor classes can be subclassed.
 *
 * Created: Sep 12, 2005
 *
 * @author <A HREF="mailto:casutton@cs.umass.edu>casutton@cs.umass.edu</A>
 * @version $Id: AbstractFactor.java,v 1.1 2007/10/22 21:37:44 mccallum Exp $
 */
public abstract class AbstractFactor implements Factor {

  protected VarSet vars;

  protected AbstractFactor ()
  {
    vars = new HashVarSet ();
  }

  protected AbstractFactor (VarSet vars)
  {
    this.vars = vars;
  }

  protected abstract Factor extractMaxInternal (VarSet varSet);

  protected abstract double lookupValueInternal (int i);

  protected abstract Factor marginalizeInternal (VarSet varsToKeep);

  @Override
public double value (Assignment assn)
  {
    return lookupValueInternal (assn.singleIndex ());
  }

  @Override
public double value (AssignmentIterator it)
  {
    return lookupValueInternal (it.indexOfCurrentAssn ());
  }

  public double phi (DenseAssignmentIterator it)
  {
    return lookupValueInternal (it.indexOfCurrentAssn ());
  }

  @Override
public Factor marginalize (Variable vars[])
  {
    return marginalizeInternal (new HashVarSet (vars));
  }

  @Override
public Factor marginalize (Collection vars)
  {
    return marginalizeInternal (new HashVarSet (vars));
  }

  @Override
public Factor marginalize (Variable var)
  {
    return marginalizeInternal (new HashVarSet (new Variable[] { var }));
  }

  @Override
public Factor marginalizeOut (Variable var)
  {
    HashVarSet vars = new HashVarSet (this.vars);
    vars.remove (var);
    return marginalizeInternal (vars);
  }

  @Override
public Factor marginalizeOut (VarSet varset)
  {
    HashVarSet vars = new HashVarSet (this.vars);
    vars.remove (varset);
    return marginalizeInternal (vars);
  }

  @Override
public Factor extractMax (Variable vars[])
  {
    return extractMaxInternal (new HashVarSet (vars));
  }

  @Override
public Factor extractMax (Collection vars)
  {
    return extractMaxInternal (new HashVarSet (vars));
  }

  @Override
public Factor extractMax (Variable var)
  {
    return extractMaxInternal (new HashVarSet (new Variable[] { var }));
  }


  // xxx should return an Assignment
  @Override
public int argmax ()
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public Assignment sample (Randoms r)
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public double sum ()
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public double entropy ()
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public Factor multiply (Factor dist)
  {
    Factor dup = duplicate ();
    dup.multiplyBy (dist);
    return dup;
  }

  @Override
public void multiplyBy (Factor pot)
  {
    throw new UnsupportedOperationException ("Cannot multiply "+this+" by "+pot);
  }

  @Override
public void exponentiate (double power)
  {
    throw new UnsupportedOperationException ("Cannot exponentiate "+this+" by "+power);
  }

  @Override
public void divideBy (Factor pot)
  {
    throw new UnsupportedOperationException ("Cannot divide "+this+ " by "+pot);
  }

  public boolean isInLogSpace ()
  {
    return false;
  }

  public void logify ()
  {
    throw new UnsupportedOperationException (toString());
  }

  public void delogify ()
  {
    throw new UnsupportedOperationException (toString());
  }

  public Factor log ()
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public boolean containsVar (Variable var)
  {
    return vars.contains (var);
  }

  @Override
public VarSet varSet ()
  {
    return vars;
  }

  @Override
public AssignmentIterator assignmentIterator ()
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public boolean almostEquals (Factor p)
  {
    return almostEquals (p, 1e-5);
  }


  @Override
public double logValue (Assignment assn)
  {
    return Math.log (value (assn));
  }

  @Override
public double logValue (AssignmentIterator it)
  {
    return Math.log (value (it));
  }

  @Override
public double logValue (int loc)
  {
    throw new UnsupportedOperationException (toString());
  }

  @Override
public Variable getVariable (int i)
  {
    return vars.get (i);
  }

  @Override
public AbstractTableFactor asTable ()
  {
    throw new UnsupportedOperationException (toString());
  }

  protected void setVarSet (VarSet vars) { this.vars = vars; }

    @Override
	public String prettyOutputString () {
	StringBuffer buf = new StringBuffer();
	for (Iterator it = vars.iterator(); it.hasNext();) {
	    Variable var = (Variable) it.next();
	    buf.append (var.getLabel());
	    buf.append (" ");
	}
	buf.append ("~ Factor\n");
	return buf.toString();
    }

}