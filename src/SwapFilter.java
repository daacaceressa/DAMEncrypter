import PipesAndFilter.*;
public class SwapFilter extends SimpleFilter<Message, Message> {
    public static final String NAME = "SwapFilter";
    public SwapFilter(Pipe<Message> input, Pipe<Message> output) {
        super(input, output);
    }
    public SwapFilter() {
        super();
    }

    @Override
    protected Message transformOne(Message in) {
        int left, right, middle, text = 0;
        left = (in.text>>4) & 7;
        right = in.text & 7;
        middle = in.text & (1<<3);
        text = (right<<4) | middle | left;
        Message out = new Message( in.key, text & ASCII );
        return out;
    }
}
