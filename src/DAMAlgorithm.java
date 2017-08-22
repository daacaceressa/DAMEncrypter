import java.util.ArrayList;

public class DAMAlgorithm {
    ArrayList<Filter<Message,Message>> filters;

    public DAMAlgorithm( ) {
        filters = new ArrayList<>();
        filters.add( new XORFilter() );
        filters.add( new ReverseFilter() );
        filters.add( new SwapFilter() );
        filters.add( new ComplementFilter() );
    }
}
