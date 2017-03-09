package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;
import fpga.PipeStream.In;
import fpga.PipeStream.Out;
import java.util.List;

public class Multiply2Node extends PipeNode <Double, Double>
{
  double xPlusFive;
  double twoXPlusFive;
  
  // READ STREAMS:
  // 0: Add5Node
  
  // WRITE STREAMS:
  // 0: DivideXNode
  
  public Multiply2Node(List<In<Double>> readers, List<Out<Double>> writers)
  {
    super(readers, writers);
  }

  @ Override
  public void readInputs()
  {
    try
    {
      xPlusFive = readers.get(0).read();
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
      writers.get(0).write(twoXPlusFive);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
