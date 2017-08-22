
public class DAMRunner {
    public static void main(String[] args) {

        // create pipes
        final Pipe<Message> genToXOR = new PipeImpl<Message>();
        final Pipe<Message> xorToReverse = new PipeImpl<Message>();
        final Pipe<Message> reverseToSwap = new PipeImpl<Message>();
        final Pipe<Message> swapToComplement = new PipeImpl<Message>();
        final Pipe<Message> complementToOut = new PipeImpl<Message>();

        // create components that use the pipes
        final Generator<Message> generator = new DAMGenerator(genToXOR);
        final Filter<Message, Message> reverseFilter = new ReverseFilter(genToXOR, complementToOut);
        /*final Filter<Message, Message> xorFilter = new XORFilter(genToXOR, xorToReverse);
        final Filter<Message, Message> reverseFilter = new ReverseFilter(xorToReverse, reverseToSwap);
        final Filter<Message, Message> swapFilter = new SwapFilter(reverseToSwap, swapToComplement);
        final Filter<Message, Message> complementFilter = new ComplementFilter(swapToComplement, complementToOut);*/
        final Sink<Message> sink = new DAMSink(complementToOut);

        // start all components
        generator.start();
        reverseFilter.start();
        /*xorFilter.start();
        reverseFilter.start();
        swapFilter.start();
        complementFilter.start();*/
        sink.start();

        //System.out.println("runner finished");
    }
}
