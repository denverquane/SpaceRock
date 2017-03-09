package fpga.pipelinetest;

import fpga.pipeline.PipeNode;
import fpga.pipeline.PipeStream.In;
import fpga.pipeline.PipeStream.Out;

public class Multiply2Node extends PipeNode <Double, Double>
{
  double xPlusFive;
  double twoXPlusFive;
  
  // READ STREAMS:
  // 0: Add5Node
  
  // WRITE STREAMS:
  // 0: DivideXNode
  
  public Multiply2Node(In<Double> reader, Out<Double> writer)
  {
    super(reader, writer);
  }

  @ Override
  public void readInputs()
  {
    try
    {
      xPlusFive = reader.read();
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
      writer.write(twoXPlusFive);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
