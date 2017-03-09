package fpga.pipelinetest;

import fpga.PipeNode;
import fpga.PipeStream;
import fpga.PipeStream.In;
import fpga.PipeStream.Out;
import java.util.List;

public class DivideXNode extends PipeNode <Double, Double>
{
  double twoXPlusFive;
  double x;
  double result;
  
  // READ STREAMS:
  // 0: Multiply2Node
  // 1: Add5Node
  
  // WRITE STREAMS:
  // 0: Main
  
  public DivideXNode(List<In<Double>> readers, List<Out<Double>> writers)
  {
    super(readers, writers);
  }

  @ Override
  public void readInputs()
  {
    try
    {
      twoXPlusFive = readers.get(0).read();
      x = readers.get(1).read();
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
      writers.get(0).write(result);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
