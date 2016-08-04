/* Copyright (C) 2002 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */




/** 
   @author Andrew McCallum <a href="mailto:mccallum@cs.umass.edu">mccallum@cs.umass.edu</a>
 */

package cc.mallet.pipe.iterator;

import java.util.Iterator;

import cc.mallet.types.Instance;

public class ConcatenatedInstanceIterator implements Iterator<Instance>
{
	Iterator<Instance>[] iterators;
	Instance next;
	int iteratorIndex;
	
	public ConcatenatedInstanceIterator (Iterator<Instance>[] iterators)
	{
		this.iterators = iterators;
		this.iteratorIndex = 0;
		setNext();
	}

	private void setNext () {
		next = null;
		for (; iteratorIndex < iterators.length &&
					 !iterators[iteratorIndex].hasNext(); iteratorIndex++);			
		if (iteratorIndex < iterators.length)
			next = iterators[iteratorIndex].next();		
	}
	
	@Override
	public boolean hasNext ()
	{
		return next != null;
	}

	@Override
	public Instance next ()
	{
		Instance ret = next;
		setNext();		
		return ret;
	}
	
	@Override
	public void remove () { throw new IllegalStateException ("This Iterator<Instance> does not support remove()."); }
}



