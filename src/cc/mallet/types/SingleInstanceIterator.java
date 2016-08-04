package cc.mallet.types;

import java.util.Iterator;


public class SingleInstanceIterator implements Iterator<Instance> {
	
	Instance nextInstance;
	boolean doesHaveNext;
	
	public SingleInstanceIterator (Instance inst) {
		nextInstance = inst;
		doesHaveNext = true;
	}

	@Override
	public boolean hasNext() {
		return doesHaveNext;
	}

	@Override
	public Instance next() {
		doesHaveNext = false;
		return nextInstance;
	}
	
	@Override
	public void remove () { throw new IllegalStateException ("This iterator does not support remove().");	}

}
