/* Copyright (C) 2006 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://mallet.cs.umass.edu/
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */
package cc.mallet.grmm.types;


import java.util.Collection;

import cc.mallet.util.Randoms;

/**
 * $Id: CPT.java,v 1.1 2007/10/22 21:37:44 mccallum Exp $
 */
public class CPT implements DiscreteFactor {

  private DiscreteFactor subFactor;
  private VarSet parents;
  private Variable child;

  public CPT (DiscreteFactor subFactor, Variable child)
  {
    this.subFactor = subFactor;
    this.child = child;
    if (!subFactor.containsVar (child)) {
      throw new IllegalArgumentException ("Invalid child var for CPT\n  Child: " + child + "\n  Factor: " + subFactor);
    }
    parents = new HashVarSet (subFactor.varSet ());
    parents.remove (child);
  }

  public VarSet getParents ()
  {
    return parents;
  }

  public Variable getChild ()
  {
    return child;
  }

  public void setSubFactor (DiscreteFactor subFactor)
  {
    this.subFactor = subFactor;
  }

  @Override
public String toString ()
  {
    return "CPT: Child ["+child+"]\n  Factor: "+subFactor.toString ();
  }

  @Override
public String prettyOutputString() { return toString(); }

  @Override
public double value (Assignment assn) {return subFactor.value (assn);}

  @Override
public double value (AssignmentIterator it) {return subFactor.value (it);}

  @Override
public Factor normalize () { return subFactor.normalize (); }

  @Override
public Factor marginalize (Variable[] vars) {return subFactor.marginalize (vars);}

  @Override
public Factor marginalize (Collection vars) {return subFactor.marginalize (vars);}

  @Override
public Factor marginalize (Variable var) {return subFactor.marginalize (var);}

  @Override
public Factor marginalizeOut (Variable var) {return subFactor.marginalizeOut (var);}

  @Override
public Factor extractMax (Collection vars) {return subFactor.extractMax (vars);}

  @Override
public Factor extractMax (Variable var) {return subFactor.extractMax (var);}

  @Override
public Factor extractMax (Variable[] vars) {return subFactor.extractMax (vars);}

  @Override
public int argmax () {return subFactor.argmax ();}

  @Override
public Assignment sample (Randoms r) {return subFactor.sample (r);}

  @Override
public double sum () {return subFactor.sum ();}

  @Override
public double entropy () {return subFactor.entropy ();}

  @Override
public Factor multiply (Factor dist) {return subFactor.multiply (dist);}

  @Override
public void multiplyBy (Factor pot) {subFactor.multiplyBy (pot);}

  @Override
public void exponentiate (double power) {subFactor.exponentiate (power);}

  @Override
public void divideBy (Factor pot) {subFactor.divideBy (pot);}

  @Override
public boolean containsVar (Variable var) {return subFactor.containsVar (var);}

  @Override
public VarSet varSet () {return subFactor.varSet ();}

  @Override
public AssignmentIterator assignmentIterator () {return subFactor.assignmentIterator ();}

  @Override
public boolean almostEquals (Factor p) {return subFactor.almostEquals (p);}

  @Override
public boolean almostEquals (Factor p, double epsilon) {return subFactor.almostEquals (p, epsilon);}

  @Override
public Factor duplicate () {return subFactor.duplicate ();}

  @Override
public boolean isNaN () {return subFactor.isNaN ();}

  @Override
public double logValue (AssignmentIterator it) {return subFactor.logValue (it);}

  @Override
public double logValue (Assignment assn) {return subFactor.logValue (assn);}

  @Override
public double logValue (int loc) {return subFactor.logValue (loc);}

  @Override
public Variable getVariable (int i) {return subFactor.getVariable (i);}

  @Override
public int sampleLocation (Randoms r) {return subFactor.sampleLocation (r);}

  @Override
public double value (int index) {return subFactor.value (index);}

  @Override
public int numLocations () {return subFactor.numLocations ();}

  @Override
public double valueAtLocation (int loc) {return subFactor.valueAtLocation (loc);}

  @Override
public int indexAtLocation (int loc) {return subFactor.indexAtLocation (loc);}

  @Override
public double[] toValueArray () {return subFactor.toValueArray ();}

  @Override
public int singleIndex (int[] smallDims) {return subFactor.singleIndex (smallDims);}

  @Override
public String dumpToString () { return subFactor.dumpToString (); }

  @Override
public Factor slice (Assignment assn) { return subFactor.slice (assn); }

  @Override
public AbstractTableFactor asTable ()
  {
    return subFactor.asTable ();
  }

  @Override
public Factor marginalizeOut (VarSet varset)
  {
    return subFactor.marginalizeOut (varset);
  }

}
