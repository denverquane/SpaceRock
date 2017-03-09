package fpga.pipeline;

import java.awt.image.BufferedImage;
import java.util.List;

import fpga.pipeline.PipeStream.In;
import fpga.pipeline.PipeStream.Out;

public class MainPipeline
{
  private Out<BufferedImage> writeEnd;
  private In<List<Extract.Debris>> readEnd;
  
  private Thread gaussThread;
  private Thread threshThread;
  private Thread extractThread;

  public MainPipeline()
  {
    PipeStream<BufferedImage> retGauss = new PipeStream<BufferedImage>();
    PipeStream<BufferedImage> gaussThresh = new PipeStream<BufferedImage>();
    PipeStream<boolean [][]> threshExtract = new PipeStream<boolean [] []>();
    PipeStream<List<Extract.Debris>> extractReport =
      new PipeStream<List<Extract.Debris>>();
    
    writeEnd = retGauss.getWriteEnd();
    readEnd = extractReport.getReadEnd();
    
    PipeNode<BufferedImage, BufferedImage> gauss =
      new GaussianFilter(retGauss.getReadEnd(), gaussThresh.getWriteEnd());
    PipeNode<BufferedImage, boolean [][]> thresh = new
      Threshold(gaussThresh.getReadEnd(), threshExtract.getWriteEnd());
    PipeNode<boolean [][], List<Extract.Debris>> extract = new
      Extract(threshExtract.getReadEnd(), extractReport.getWriteEnd());
    
    gaussThread = new Thread(gauss);
    threshThread = new Thread(thresh);
    extractThread = new Thread(extract);
  }
  
  public void start()
  {
    gaussThread.start();
    threshThread.start();
    extractThread.start();
  }
  
  // TODO: Might want to implement some sort of stop method.
  
  public void write(BufferedImage image)
  {
    try
    {
      writeEnd.write(image);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  public List<Extract.Debris> read()
  {
    try
    {
      return readEnd.read();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}