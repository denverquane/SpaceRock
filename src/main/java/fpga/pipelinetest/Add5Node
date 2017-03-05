package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;

public class Add5Node extends PipeNode
{
  double x;
  double xPlusFive;
  
  // READ STREAMS:
  // 0: Main
  
  // WRITE STREAMS:
  // 0: Multiply2Node
  // 1: DivideXNode
  
  public Add5Node(PipeStream [] readers, PipeStream [] writers)
  {
    super(readers, writers);
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
      
      x = (double) readers[0].read();
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
      
      writers[0].write(xPlusFive);
      writers[1].write(x);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
