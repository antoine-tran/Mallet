/* Copyright (C) 2006 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */
package cc.mallet.grmm.types;

import cc.mallet.util.Maths;
import cc.mallet.util.Randoms;

/**
 * $Id: ConstantFactor.java,v 1.1 2007/10/22 21:37:44 mccallum Exp $
 */
public class ConstantFactor extends AbstractFactor {

  private double c;

  public ConstantFactor (double c)
  {
    super (new HashVarSet ());
    this.c = c;
  }

  @Override
protected Factor extractMaxInternal (VarSet varSet)
  {
    return this;
  }

  @Override
protected double lookupValueInternal (int i)
  {
    return c;
  }

  @Override
protected Factor marginalizeInternal (VarSet varsToKeep)
  {
    return this;
  }

  @Override
public double value (AssignmentIterator it)
  {
    return c;
  }

  // I can't imagine why anyone whould want to call this method.
  @Override
public Factor normalize ()
  {
    c = 1.0;
    return this;
  }

  @Override
public Assignment sample (Randoms r)
  {
    return new Assignment ();
  }

  @Override
public String dumpToString ()
  {
    return "[ConstantFactor : "+c+" ]";
  }

  @Override
public String toString ()
  {
    return dumpToString ();
  }

  @Override
public Factor slice (Assignment assn)
  {
    return this;
  }

  @Override
public Factor duplicate ()
  {
    return new ConstantFactor (c);
  }

  @Override
public boolean almostEquals (Factor p, double epsilon)
  {
    return (p instanceof ConstantFactor && Maths.almostEquals (c, ((ConstantFactor)p).c, epsilon));
  }

  @Override
public boolean isNaN ()
  {
    return Double.isNaN (c);
  }

  @Override
public Factor multiply (Factor other)
  {
    // special handling of identity factor
    if (Maths.almostEquals (c, 1.0)) {
      return other.duplicate ();
    } else if (other instanceof ConstantFactor) {
      return new ConstantFactor (c * ((ConstantFactor)other).c);
    } else {
      return other.multiply (this);
    }
  }

  @Override
public void multiplyBy (Factor other)
  {
    if (!(other instanceof ConstantFactor)) {
      throw new UnsupportedOperationException ("Can't multiply a constant factor by "+other);
    } else {
      ConstantFactor otherCnst = (ConstantFactor) other;
      c *= otherCnst.c;
    }
  }

  public static Factor makeIdentityFactor ()
  {
    return new ConstantFactor (1.0);
  }

  // Serialization garbage
  private static final long serialVersionUID = -2934945791792969816L;

}
