
public class ReverseFilter extends SimpleFilter<Message, Message> {
    public static final String NAME = "ReverseFilter";
    public ReverseFilter(Pipe<Message> input, Pipe<Message> output) {
        super(input, output);
    }
    public ReverseFilter() {
        super();
    }

    @Override
    protected Message transformOne(Message in) {
        int text = 0;
        for( int i = 0; i < 7; ++i ) {
            text |= ((in.text>>i) & 1)<<(6-i);
        }
        Message out = new Message( in.key, text & ASCII );
        return out;
    }
}
