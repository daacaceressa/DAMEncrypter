
public class XORFilter extends SimpleFilter<Message, Message> {
    public static final String NAME = "XORFilter";
    public XORFilter(Pipe<Message> input, Pipe<Message> output) {
        super(input, output);
    }
    public XORFilter() { super(); }

    @Override
    protected Message transformOne(Message in) {
        int text = (in.text ^ in.key) & ASCII;
        Message out = new Message( in.key, text );
        return out;
    }
}
