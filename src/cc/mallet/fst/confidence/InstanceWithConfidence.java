/* Copyright (C) 2002 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */

/** 
		@author Aron Culotta <a href="mailto:culotta@cs.umass.edu">culotta@cs.umass.edu</a>
*/

package cc.mallet.fst.confidence;

import cc.mallet.types.*;

/**
	 Helper class to store confidence of an Instance.
 */
public class InstanceWithConfidence implements Comparable{
	double confidence;
	Instance instance;
	boolean correct;
	
	public InstanceWithConfidence (Instance inst, double c, Sequence predicted) {
		this.instance = inst;
		this.confidence = c;
		this.correct = true;
		Sequence truth = (Sequence) inst.getTarget ();
		for (int i=0; i < truth.size(); i++) {
			if (!truth.get(i).equals (predicted.get(i))) {
				this.correct = false;
				break;
			}				
		}
	}

	public InstanceWithConfidence (Instance inst, double c, boolean correct) {
		this.instance = inst;
		this.confidence = c;
		this.correct = correct;
	}

	@Override
	public int compareTo (Object o) {
		InstanceWithConfidence inst = (InstanceWithConfidence) o;
		if (this.confidence > inst.confidence)
			return 1;
		else if (this.confidence < inst.confidence)
			return -1;
		else return 0;
	}

	public double getConfidence () { return confidence; }
	public Instance getInstance () { return instance; }
	public boolean correct () { return correct; }
}
