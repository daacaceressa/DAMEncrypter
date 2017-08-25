package PipesAndFilter;
public abstract class SimpleFilter<I, O> extends Filter<I, O> {
    public SimpleFilter(Pipe<I> input, Pipe<O> output) {
        super(input, output);
    }
    public SimpleFilter() {
        super();
    }

    public static final int ASCII = 0x7f;

    @Override
    protected void transformBetween(Pipe<I> input, Pipe<O> output) {
        try {
            I in;
            while ((in = input.nextOrNullIfEmptied()) != null) {
                O out = transformOne(in);
                output.put(out);
            }
        } catch (InterruptedException e) {
            // TODO handle properly, using advice in http://www.ibm.com/developerworks/java/library/j-jtp05236/
            System.err.println("interrupted");
            e.printStackTrace();
            return;
        }
        output.closeForWriting();
    }

    protected abstract O transformOne(I in);
}

