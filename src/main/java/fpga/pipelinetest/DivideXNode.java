package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;

public class DivideXNode extends PipeNode
{
  double twoXPlusFive;
  double x;
  double result;
  
  // READ STREAMS:
  // 0: Multiply2Node
  // 1: Add5Node
  
  // WRITE STREAMS:
  // 0: Main
  
  public DivideXNode(PipeStream [] readers, PipeStream [] writers)
  {
    super(readers, writers);
  }

  @ Override
  public void readInputs()
  {
    try
    {
      twoXPlusFive = (double) readers[0].read();
      x = (double) readers[1].read();
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
    result = twoXPlusFive / x;
  }
  
  @ Override
  public void writeOutputs()
  {
    try
    {
      writers[0].write(result);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
