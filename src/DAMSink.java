import PipesAndFilter.*;
import java.io.*;

public class DAMSink extends Sink<Message> {
    public DAMSink(Pipe<Message> input) {
        super(input);
    }

    @Override
    public void takeFrom(Pipe<Message> pipe) {
        try {
            Message in;
            while ((in = pipe.nextOrNullIfEmptied()) != null) {
                String currentText = DAMApplet.resultField.getText() + SimpleFilter.getChar( in.text );
                DAMApplet.resultField.setText(currentText);
//                System.out.print( (char)in.text );
                delayForDebug(300);
            }
            //System.out.println("sink finished");
        } catch (InterruptedException e) {
            System.err.println("interrupted");
            e.printStackTrace();
        } finally {
            System.out.close();
        }
    }
}
