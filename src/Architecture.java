import PipesAndFilter.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// This class manages the structure of the pipe and filter architecture.
public class Architecture {
    private ArrayList<Filter<Message,Message>> filters;
    private ArrayList<Pipe<Message>> pipes;

    public Architecture(List<String> fromFilters) {
        filters = new ArrayList<>();
        pipes = new ArrayList<>();
        // Add all pipes for the given filters, including the ending pipe.
        for (int i = 0; i <= fromFilters.size(); i++) {
            pipes.add(new PipeImpl<Message>());
        }
        for (int i = 0; i < fromFilters.size(); i++) {
            filters.add(createFilter(fromFilters.get(i), pipes.get(i), pipes.get(i+1)));
        }
    }

    public Pipe<Message> getStartingPipe() {
        return pipes.get(0);
    }

    public Pipe<Message> getEndingPipe() {
        return pipes.get(pipes.size()-1);
    }

    public Filter<Message, Message> createFilter(String filterType, Pipe<Message> inPipe, Pipe<Message> outPipe) {
        switch (filterType) {
            case ComplementFilter.NAME:
                return new ComplementFilter(inPipe, outPipe);
            case XORFilter.NAME:
                return new XORFilter(inPipe, outPipe);
            case SwapFilter.NAME:
                return new SwapFilter(inPipe, outPipe);
            case ReverseFilter.NAME:
                return  new ReverseFilter(inPipe, outPipe);
            default:
                throw new NoSuchElementException();
        }
    }

    public void start() {
        for (Filter filter : filters) {
            filter.start();
        }
    }
}
