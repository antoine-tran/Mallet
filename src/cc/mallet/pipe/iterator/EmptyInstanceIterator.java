package cc.mallet.pipe.iterator;

import java.util.Iterator;

import cc.mallet.types.Instance;

public class EmptyInstanceIterator implements Iterator<Instance> {

	@Override
	public boolean hasNext() { return false; }
	@Override
	public Instance next () { throw new IllegalStateException ("This iterator never has any instances.");	}
	@Override
	public void remove () { throw new IllegalStateException ("This iterator does not support remove().");	}
}
