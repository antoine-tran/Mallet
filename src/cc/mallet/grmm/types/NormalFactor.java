/* Copyright (C) 2006 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */
package cc.mallet.grmm.types;

import cc.mallet.util.Maths;
import cc.mallet.util.Randoms;
import no.uib.cipr.matrix.*;

/**
 * Multivariate Gaussian factor.  Currently, almost all of this class
 *  is a stub, except for the sample method.
 * $Id: NormalFactor.java,v 1.1 2007/10/22 21:37:44 mccallum Exp $
 */
public class NormalFactor extends AbstractFactor {

  private Vector mean;
  private Matrix variance;

  public NormalFactor (VarSet vars, Vector mean, Matrix variance)
  {
    super (vars);
    if (!isPosDef (variance)) throw new IllegalArgumentException ("Matrix "+variance+" not positive definite.");
    this.mean = mean;
    this.variance = variance;
  }

  private boolean isPosDef (Matrix variance)
  {
    try {
      EVD evd = EVD.factorize (variance);
      double[] vals = evd.getRealEigenvalues ();
      return vals[vals.length - 1] > 0;
    } catch (NotConvergedException e) {
      throw new RuntimeException (e);
    }
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
    // stub
    return 1.0;
  }

  @Override
protected double lookupValueInternal (int i)
  {
    throw new UnsupportedOperationException ();
  }

  @Override
protected Factor marginalizeInternal (VarSet varsToKeep)
  {
    throw new UnsupportedOperationException ();
  }

  @Override
public Factor normalize ()
  {
    return this;
  }

  @Override
public Assignment sample (Randoms r)
  {
    // generate from standard normal
    double[] vals = new double [mean.size ()];
    for (int k = 0; k < vals.length; k++) {
      vals[k] = r.nextGaussian ();
    }

    // and transform
    Vector Z = new DenseVector (vals, false);
    DenseVector result = new DenseVector (vals.length);
    variance.mult (Z, result);
    result = (DenseVector) result.add (mean);

    return new Assignment (vars.toVariableArray (), result.getData ());
  }

  @Override
public boolean almostEquals (Factor p, double epsilon)
  {
    return equals (p);
  }

  @Override
public Factor duplicate ()
  {
    return new NormalFactor (vars, mean, variance);
  }

  @Override
public boolean isNaN ()
  {
    return false;
  }

  @Override
public String dumpToString ()
  {
    return toString ();
  }

  @Override
public String toString ()
  {
    return "[NormalFactor "+vars+" "+mean+" ... " +variance+" ]";
  }

  // todo
  @Override
public Factor slice (Assignment assn)
  {
    if (assn.varSet ().containsAll (vars)) {
      // special case
      return new ConstantFactor (value (assn));
    } else {
      throw new UnsupportedOperationException ();
    }
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
