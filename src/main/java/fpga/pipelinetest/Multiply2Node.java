package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;

public class Multiply2Node extends PipeNode
{
  double xPlusFive;
  double twoXPlusFive;
  
  // READ STREAMS:
  // 0: Add5Node
  
  // WRITE STREAMS:
  // 0: DivideXNode
  
  public Multiply2Node(PipeStream [] readers, PipeStream [] writers)
  {
    super(readers, writers);
  }

  @ Override
  public void readInputs()
  {
    try
    {
      xPlusFive = (double) readers[0].read();
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
    twoXPlusFive = 2 * xPlusFive;
  }
  
  @ Override
  public void writeOutputs()
  {
    try
    {
      writers[0].write(twoXPlusFive);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
