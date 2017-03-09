package fpga.pipelinetest;

import fpga.PipeNode;
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
  
  public DivideXNode(In<Double> reader, Out<Double> writer, double x)
  {
    super(reader, writer);
    this.x = x;
  }

  @ Override
  public void readInputs()
  {
    try
    {
      twoXPlusFive = reader.read();
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
      writer.write(result);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
