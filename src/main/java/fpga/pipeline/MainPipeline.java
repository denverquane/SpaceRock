package fpga.pipeline;

import java.awt.image.BufferedImage;
import java.util.List;

import fpga.pipeline.PipeStream.In;
import fpga.pipeline.PipeStream.Out;

/**
 * The main code to create and run the main part of the pipeline consisting
 * of the three intermediate nodes Gaussian Filter, Threshold, and Extract.
 */

public class MainPipeline
{
  private Out<BufferedImage> writeEnd;
  private In<List<Extract.Debris>> readEnd;
  
  private Thread gaussThread;
  private Thread threshThread;
  private Thread extractThread;

  /**
   * Set up and connect all nodes and streams of the pipeline, including the
   * threads to run them.
   */
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
  
  /**
   * Start the pipeline up, with each node running in its own thread.
   */
  public void start()
  {
    gaussThread.start();
    threshThread.start();
    extractThread.start();
  }
  
  /**
   * Put an image into the Gaussian Blur node.  May block if the
   * Gaussian Blur node isn't ready to take more input.
   * @param image The image to be written into the pipeline.
   */
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
  
  /**
   * Read an image from the Extract node.  May block if there is no
   * output to be read until output is eventually received.
   * @return The most recent list of debris outputted from the extract
   * node.
   */
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
