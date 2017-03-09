package fpga.pipelinetest;

import fpga.pipeline.PipeNode;
import fpga.pipeline.PipeStream.In;
import fpga.pipeline.PipeStream.Out;

public class Add5Node extends PipeNode <Double, Double>
{
  double x;
  double xPlusFive;
  
  // READ STREAMS:
  // 0: Main
  
  // WRITE STREAMS:
  // 0: Multiply2Node
  // 1: DivideXNode


  public Add5Node(In<Double> reader, Out<Double> writer) {
    super(reader, writer);
  }

  @ Override
  public void readInputs()
  {
    try
    {
      // Read the x value we were sent by main, blocking until received if
      // necessary.  Typically, readInputs should just be a simple method
      // where you store whatever information you were sent in class variables.
      // You'll do the actual work using this information in processInputs.
      
      x = reader.read();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
  
  @ Override
  public void processInputs()
  {
    // Here, we do the actual work we need to in order to create whatever
    // output this node will be sending out.  In this case, just adding five to
    // our input.
    
    xPlusFive = x + 5;
  }
  
  @ Override
  public void writeOutputs()
  {
    try
    {
      // Now we have our output and we're ready to send it.  Put xPlusFive on
      // the stream to be read by Multiply2Node, and put x on the stream to be
      // read by DivideXNode.
      
      writer.write(xPlusFive);

    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
