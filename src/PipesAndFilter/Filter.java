package PipesAndFilter;
public abstract class Filter<I, O> extends ThreadedRunner {
	protected Pipe<I> input;
	protected Pipe<O> output;

	public Filter(Pipe<I> input, Pipe<O> output) {
		this.input = input;
		this.output = output;
	}

	public Filter() {

	}

	@Override
	public void run() {
		transformBetween(input, output);
	}

	protected abstract void transformBetween(Pipe<I> input, Pipe<O> output);
}
