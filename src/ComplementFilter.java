import PipesAndFilter.*;
public class ComplementFilter extends SimpleFilter<Message, Message> {
    public static final String NAME = "ComplementFilter";
    public ComplementFilter(Pipe<Message> input, Pipe<Message> output) {
        super(input, output);
    }
    public ComplementFilter() {
        super();
    }

    @Override
    protected Message transformOne(Message in) {
        int text = (~in.text) & MASK_ALPHABET;
        Message out = new Message( in.key, text );
        return out;
    }
}
