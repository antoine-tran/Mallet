/* Copyright (C) 2011 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */

package cc.mallet.fst.semi_supervised.pr;

import cc.mallet.fst.CRF.State;
import cc.mallet.fst.Transducer;
import cc.mallet.types.Sequence;

/**
 * TransitionIterator that caches dot products.
 *
 * @author Gregory Druck
 */

public class CachedDotTransitionIterator extends Transducer.TransitionIterator {
  State source;
  int index, nextIndex;
  protected double[] weights;
  Object input;

  public CachedDotTransitionIterator(State source,
      Sequence inputSeq, int inputPosition, String output,
      double[] dots) {
    this(source, inputSeq.get(inputPosition), output, dots);
  }

  protected CachedDotTransitionIterator(State source, Object fv,
      String output, double[] dots) {
    this.source = source;
    this.input = fv;
    this.weights = new double[source.numDestinations()];
    for (int i = 0; i < source.numDestinations(); i++) {
      weights[i] = dots[source.getDestinationState(i).getIndex()];
    }
    // Prepare nextIndex, pointing at the next non-impossible transition
    nextIndex = 0;
    while (nextIndex < source.numDestinations()
        && weights[nextIndex] == Transducer.IMPOSSIBLE_WEIGHT)
      nextIndex++;
  }

  @Override
public boolean hasNext() {
    return nextIndex < source.numDestinations();
  }

  @Override
public Transducer.State nextState() {
    assert (nextIndex < source.numDestinations());
    index = nextIndex;
    nextIndex++;
    while (nextIndex < source.numDestinations()
        && weights[nextIndex] == Transducer.IMPOSSIBLE_WEIGHT)
      nextIndex++;
    return source.getDestinationState(index);
  }

  // These "final"s are just to try to make this more efficient. Perhaps some of
  // them will have to go away
  @Override
public final int getIndex() {
    return index;
  }

  @Override
public final Object getInput() {
    return input;
  }

  @Override
public final Object getOutput() {
    return source.getLabelName(index);
  }

  @Override
public final double getWeight() {
    return weights[index];
  }

  @Override
public final Transducer.State getSourceState() {
    return source;
  }

  @Override
public final Transducer.State getDestinationState() {
    return source.getDestinationState(index);
  }
  
  private static final long serialVersionUID = 1;
}

