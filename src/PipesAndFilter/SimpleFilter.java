package PipesAndFilter;
public abstract class SimpleFilter<I, O> extends Filter<I, O> {
    public static final int SIZE_ALPHABET = 128;
    public static final int MASK_ALPHABET = 127;
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()_+{}\":?><`-=[];',.áéíóúøçñ€ƒ†‡Œ™œ£©®¬±µ¿ÆÑþ§¦¥¶¢¼½¾» ²³";

    public SimpleFilter(Pipe<I> input, Pipe<O> output) {
        super(input, output);
    }
    public SimpleFilter() {
        super();
    }

    public static int getIndex(char c) {
        for(int i = 0; i < SIZE_ALPHABET; i++) {
            if( ALPHABET.charAt(i) == c ) {
                return i;
            }
        }
        return -1;
    }

    public static char getChar(int x) {
        return ALPHABET.charAt(x);
    }

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

