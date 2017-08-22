import java.io.InputStreamReader;
import java.util.Scanner;

public class DAMGenerator extends Generator<Message> {
    public DAMGenerator(Pipe<Message> output) {
        super(output);
    }

    public int get_key( String s ) {
        int key = 0;
        for( int i = 0; i < s.length(); ++i ) {
            key += s.charAt(i);
        }
        return key % 128;
    }

    @Override
    public void generateInto(Pipe<Message> pipe) {
        Scanner sc = new Scanner( new InputStreamReader(System.in) );
        System.out.print( "Introduzca el mensaje a cifrar: " ); String message = sc.nextLine();
        System.out.print( "Introduzca la llave: " );            String typed_key = sc.nextLine();
        int key = get_key( typed_key );

        for ( char text : message.toCharArray() ) {
            pipe.put( new Message( key, text ) );
            //System.out.println("generated " + Integer.toString(i));
            delayForDebug(200);
        }
        pipe.closeForWriting();
        //System.out.println("generator finished");
    }
}
