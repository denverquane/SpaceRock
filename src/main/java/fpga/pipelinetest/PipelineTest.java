package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;

public class PipelineTest
{
  private static class DoubleStream extends PipeStream<Double> {
  }

  public static void main(String args[]) throws InterruptedException
  {
    // Compute ((2 * (x + 5)) / x) using a pipeline for several inputs.
	
	// x -----------> + 5 -----------> * 2 -----------> / x
	// |                                                  ^
	// |                                                  |
	// ----------------------------------------------------
    
    // First, set up the pipeline, establishing all connections in the pipeline
    // and ensuring that they point where they should.  Note that a single pipe
    // stream object is used for both ends of the connection, and one reference
    // of the object should be given to the reader, and the other to the
    // writer in each case.
    
    DoubleStream mainToN1 = new DoubleStream(); // Connection between main and
    // Add5Node.  Main will write objects to this stream, and Add5Node will
    // read them.
    DoubleStream n1ToN2 = new DoubleStream(); // Connection between Add5Node and
    // Multiply2Node.  Add5Node will write objects to this stream, and
    // Multiply2Node will read them.
      DoubleStream n2ToN3 = new DoubleStream(); // Connection between Multiply2Node
    // and DivideXNode.  DivideXNode will be listening to input from both
    // Add5Node and Multiply2Node.
    DoubleStream n3ToMain = new DoubleStream(); // Connection between DivideXNode
    // and Main.  This is where main will listen to read the pipeline's output.
    


    // And now, create the actual node objects...

    double input1 = Math.random() * 1000;
    double input2 = Math.random() * 1000;

    PipeNode n1 = new Add5Node(mainToN1.getReadEnd(), n1ToN2.getWriteEnd());
    PipeNode n2 = new Multiply2Node(n1ToN2.getReadEnd(), n2ToN3.getWriteEnd());
    PipeNode n3 = new DivideXNode(n2ToN3.getReadEnd(), n3ToMain.getWriteEnd(), input1);
    
    // ...put them in threads...
    
    Thread n1t = new Thread(n1);
    Thread n2t = new Thread(n2);
    Thread n3t = new Thread(n3);
    
    // ...and start them up.
    
    n1t.start();
    n2t.start();
    n3t.start();
    
    // Now let's create a few random inputs and see what we get out of the
    // pipeline.
    
    System.out.println("Main: Wrote " + input1 + " to Add5Node");
    mainToN1.getWriteEnd().write(input1);
    System.out.println("Main: Wrote " + input2 + " to Add5Node");
    mainToN1.getWriteEnd().write(input2);

    
    double output1 = n3ToMain.getReadEnd().read();
    System.out.println("Main: Read " + output1 + " from DivideXNode");
    System.out.println("Expected output: " + (2 * (input1 + 5)) / input1);

    // Hack: Kill the threads.  Should set up a signal for this
    n1t.interrupt();
    n2t.interrupt();
    n3t.interrupt();


    // Note that, for the actual pipeline, reading and writing should be done
    // in separate threads, as the pipeline won't be cleared out until
    // everything is read on the other end.  So we wouldn't be able to add a
    // fourth input as we have here without deadlocking ourselves.
  }
}
