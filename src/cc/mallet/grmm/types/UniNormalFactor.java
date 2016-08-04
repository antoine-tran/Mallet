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
 * Univariate Gaussian factor.
 * $Id: UniNormalFactor.java,v 1.1 2007/10/22 21:37:44 mccallum Exp $
 */
public class UniNormalFactor extends AbstractFactor {

  private Variable var;
  private double mean;
  private double variance;

  public UniNormalFactor (Variable var, double mean, double variance)
  {
    super (new HashVarSet (new Variable[] { var }));
    if (!var.isContinuous ()) throw new IllegalArgumentException ();
    if (variance <= 0) throw new IllegalArgumentException ();
    this.var = var;
    this.mean = mean;
    this.variance = variance;
  }


  //
  @Override
protected Factor extractMaxInternal (VarSet varSet)
  {
    throw new UnsupportedOperationException ();
  }

  @Override
public double value (Assignment assn)
  {
    double x = assn.getDouble (var);
    return 1/Math.sqrt(2*Math.PI*variance) * Math.exp (-1/(2.0 * variance) * (x - mean)*(x-mean));
  }

  @Override
protected double lookupValueInternal (int i)
  {
    throw new UnsupportedOperationException ();
  }

  @Override
protected Factor marginalizeInternal (VarSet varsToKeep)
  {
    if (varsToKeep.contains (var)) {
      return duplicate ();
    } else {
      return new ConstantFactor (1.0);
    }
  }

  @Override
public Factor normalize ()
  {
    return this;
  }

  @Override
public Assignment sample (Randoms r)
  {
    double val = r.nextGaussian (mean, variance);
    return new Assignment (var, val);
  }

  @Override
public boolean almostEquals (Factor p, double epsilon)
  {
    return equals (p);
  }

  @Override
public Factor duplicate ()
  {
    return new UniNormalFactor (var, mean, variance);
  }

  @Override
public boolean isNaN ()
  {
    return Double.isNaN (mean) || Double.isNaN (variance);
  }

  @Override
public String dumpToString ()
  {
    return toString ();
  }

  @Override
public String toString ()
  {
    return "[NormalFactor "+var+" "+mean+" ... " +variance+" ]";
  }

  @Override
public Factor slice (Assignment assn)
  {
    if (assn.containsVar (var)) {
      return new ConstantFactor (value (assn));
    } else return duplicate ();
  }

  @Override
public void multiplyBy (Factor f)
  {
    if (f instanceof ConstantFactor) {
      double val = f.value (new Assignment());
      // NormalFactor must be normalized right now...
      if (Maths.almostEquals (val, 1.0)) {
        return;  // ok, it's an identity factor
      }
    }

    throw new UnsupportedOperationException ("Can't multiply NormalFactor by "+f);
  }

  @Override
public void divideBy (Factor f)
  {
    if (f instanceof ConstantFactor) {
      double val = f.value (new Assignment());
      // NormalFactor must be normalized right now...
      if (Maths.almostEquals (val, 1.0)) {
        return;  // ok, it's an identity factor
      }
    }

    throw new UnsupportedOperationException ("Can't divide NormalFactor by "+f);
  }


}
