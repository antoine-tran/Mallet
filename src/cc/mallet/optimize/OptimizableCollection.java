package cc.mallet.optimize;

import java.util.ArrayList;

import cc.mallet.types.MatrixOps;

public class OptimizableCollection {
	
	public class ByGradientValue implements Optimizable.ByGradientValue 
	{
		ArrayList<Optimizable.ByGradientValue> optimizables;
		
		public ByGradientValue (Optimizable.ByGradientValue... ops) {
			optimizables = new ArrayList<Optimizable.ByGradientValue>(ops.length);
			for (Optimizable.ByGradientValue o : ops)
				optimizables.add(o);
		}
		
		@Override
		public void getValueGradient (double[] buffer) {
			double[] b2 = new double[buffer.length];
			for (Optimizable.ByGradientValue o : optimizables) {
				MatrixOps.setAll(b2, 0);
				o.getValueGradient(b2);
				MatrixOps.plusEquals(buffer, b2);
			}
		}
		
		@Override
		public double getValue () {
			double ret = 0;
			for (Optimizable.ByGradientValue o : optimizables)
				ret += o.getValue();
			return ret;
		}

		// Here we rely on all optimizables pointing to the same set of parameters! 
		
		@Override
		public int getNumParameters() {
			return optimizables.get(0).getNumParameters();
		}

		@Override
		public double getParameter(int index) {
			return optimizables.get(0).getParameter(index);
		}

		@Override
		public void getParameters(double[] buffer) {
			optimizables.get(0).getParameters(buffer);
		}

		@Override
		public void setParameter(int index, double value) {
			optimizables.get(0).setParameter(index, value);
		}

		@Override
		public void setParameters(double[] params) {
			optimizables.get(0).setParameters(params);
		}

		
	}

}
