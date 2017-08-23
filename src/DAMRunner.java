import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DAMRunner {
    public static void main(String[] args) {

        Scanner sc = new Scanner( new InputStreamReader(System.in) );
        System.out.print( "encrypt?: " );
        String message = sc.nextLine();
        ArrayList<String> architectureOrder;
        if(message.equals("Y")) {
            architectureOrder = getFiltersForDAMEncryption();
        } else {
            architectureOrder = getFiltersForDAMDecryption();
        }
        // create components that use the pipes
        final Architecture DAMArchitecture = new Architecture(architectureOrder);
        final Generator<Message> generator = new DAMGenerator(DAMArchitecture.getStartingPipe());
        final Sink<Message> sink = new DAMSink(DAMArchitecture.getEndingPipe());

        // start all components
        generator.start();
        DAMArchitecture.start();
        sink.start();

        //System.out.println("runner finished");
    }

    public static ArrayList<String> getFiltersForDAMEncryption(){
        ArrayList<String> filtersOrder = new ArrayList<>();
        filtersOrder.add(XORFilter.NAME);
        filtersOrder.add(ReverseFilter.NAME);
        filtersOrder.add(SwapFilter.NAME);
        filtersOrder.add(ComplementFilter.NAME);
        return filtersOrder;
    }

    public static ArrayList<String> getFiltersForDAMDecryption(){
        ArrayList<String> filtersOrder = getFiltersForDAMEncryption();
        Collections.reverse(filtersOrder);
        return filtersOrder;
    }
}
