package PipesAndFilter;

public class ExampleGenerator extends Generator<Integer> {
    public ExampleGenerator(Pipe<Integer> output) {
        super(output);
    }

    @Override
    public void generateInto(Pipe<Integer> pipe) {
        for (int i = 1; i <= 15; i++) {
            pipe.put(new Integer(i));
            System.out.println("generated " + Integer.toString(i));
            delayForDebug(200);
        }
        pipe.closeForWriting();
        System.out.println("generator finished");
    }
}
