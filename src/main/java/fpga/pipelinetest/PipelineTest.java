package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;
import fpga.PipeStream.In;
import fpga.PipeStream.Out;
import java.util.ArrayList;
import java.util.List;

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
    DoubleStream n1ToN3 = new DoubleStream(); // Connection between Add5Node and
    // DivideXNode.  So, Add5Node will have a total of two streams it writes to
    // when writing output.  Note also that this stream has two capacity, or
    // one extra object that it can store in memory without the writer waiting
    // until DivideXNode has read it.  This is due to the fact that we have a
    // node latency between Add5Node and DivideXNode (see above).  DivideXNode
    // won't start reading until Multiply2Node is done, and so if we gave this
    // stream a single object capacity as we did all the others, Add5Node would
    // be forced to wait at the write call to DivideXNode until Multiply2Node
    // is finished - effectively clogging our pipeline because inputs now can't
    // get through to Add5Node, and so EVERYTHING will have to wait for
    // DivideXNode.  By adding this extra capacity, Add5Node doesn't have to
    // stall, and if Add5Node ever has to block in this case, DivideXNode must
    // be in the process of reading its input streams, so our pipeline will
    // function optimally.
    DoubleStream n2ToN3 = new DoubleStream(); // Connection between Multiply2Node
    // and DivideXNode.  DivideXNode will be listening to input from both
    // Add5Node and Multiply2Node.
    DoubleStream n3ToMain = new DoubleStream(); // Connection between DivideXNode
    // and Main.  This is where main will listen to read the pipeline's output.
    
    // Form the reader and writer streams for each of the nodes.
    
    List<In<Double>> n1Readers  = new ArrayList<>();
    n1Readers.add(mainToN1.getReadEnd());

    List<Out<Double>> n1Writers = new ArrayList<>();
    n1Writers.add(n1ToN2.getWriteEnd());
    n1Writers.add(n1ToN3.getWriteEnd()); // Ordering is significant here.
    // The nodes identify the different streams based on their indices in the
    // stream array.  In this case, Add5Node specifically uses writers[0] to
    // write x + 5 to Multiply2Node, and it uses writers[1] to write x to
    // DivideXNode.
    List<In<Double>> n2Readers = new ArrayList<>();
    n2Readers.add(n1ToN2.getReadEnd());

    List<Out<Double>> n2Writers = new ArrayList<>();
    n2Writers.add(n2ToN3.getWriteEnd());

    List<In<Double>> n3Readers = new ArrayList<>();
    n3Readers.add(n2ToN3.getReadEnd());
    n3Readers.add(n1ToN3.getReadEnd()); // Same deal here as above.

    List<Out<Double>> n3Writers = new ArrayList<>();
    n3Writers.add(n3ToMain.getWriteEnd());
    
    // And now, create the actual node objects...
    
    PipeNode n1 = new Add5Node(n1Readers, n1Writers);
    PipeNode n2 = new Multiply2Node(n2Readers, n2Writers);
    PipeNode n3 = new DivideXNode(n3Readers, n3Writers);
    
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
    
    double input1 = Math.random() * 1000;
    System.out.println("Main: Wrote " + input1 + " to Add5Node");
    mainToN1.getWriteEnd().write(input1);
    double input2 = Math.random() * 1000;
    System.out.println("Main: Wrote " + input2 + " to Add5Node");
    mainToN1.getWriteEnd().write(input2);
    double input3 = Math.random() * 1000;
    System.out.println("Main: Wrote " + input3 + " to Add5Node");
    mainToN1.getWriteEnd().write(input3);
    
    double output1 = n3ToMain.getReadEnd().read();
    System.out.println("Main: Read " + output1 + " from DivideXNode");
    System.out.println("Expected output: " + (2 * (input1 + 5)) / input1);
    double output2 = n3ToMain.getReadEnd().read();
    System.out.println("Main: Read " + output2 + " from DivideXNode");
    System.out.println("Expected output: " + (2 * (input2 + 5)) / input2);
    double output3 = n3ToMain.getReadEnd().read();
    System.out.println("Main: Read " + output3 + " from DivideXNode");
    System.out.println("Expected output: " + (2 * (input3 + 5)) / input3);
    
    // Note that, for the actual pipeline, reading and writing should be done
    // in separate threads, as the pipeline won't be cleared out until
    // everything is read on the other end.  So we wouldn't be able to add a
    // fourth input as we have here without deadlocking ourselves.
  }
}
