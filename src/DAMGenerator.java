import PipesAndFilter.*;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DAMGenerator extends Generator<Message> {
	private String message;
	private String typed_key;
    
    public DAMGenerator(Pipe<Message> output, String message, String key) {
    	super(output);
    	this.message = message;
    	this.typed_key = key;
    }

    public int get_key( String s ) {
        int key = 0;
        for( int i = 0; i < s.length(); ++i ) {
            key += s.charAt(i);
        }
        return key % SimpleFilter.SIZE_ALPHABET;
    }

    @Override
    public void generateInto(Pipe<Message> pipe) {
        int key = get_key( typed_key );

        for ( char text : message.toCharArray() ) {
            pipe.put( new Message( key, SimpleFilter.getIndex(text) ) );
            //System.out.println("generated " + Integer.toString(i));
            delayForDebug(200);
        }
        pipe.closeForWriting();
        //System.out.println("generator finished");
    }
}
