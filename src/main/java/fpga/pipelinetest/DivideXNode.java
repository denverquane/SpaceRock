package fpga.pipelinetest;

import fpga.pipeline.PipeNode;
import fpga.pipeline.PipeStream.In;
import fpga.pipeline.PipeStream.Out;

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
    readInputs();
    result = twoXPlusFive / x;
    writeOutputs();
  }
  
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
