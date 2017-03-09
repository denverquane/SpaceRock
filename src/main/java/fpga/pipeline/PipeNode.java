package fpga.pipeline;

/**
 * A pipe node object that represents the node of a pipeline.  Each node has a
 * set of streams from which it reads input and another  set of streams into
 * which output is written, with a single stream between each communicating
 * node in the pipeline.
 */

public abstract class PipeNode <I,O> implements Runnable
{
  protected PipeStream.In<I> reader;
  protected PipeStream.Out<O> writer;
  
  /**
   * Set up a new node.  All streams must be set up between the communicating
   * nodes and organized into an array beforehand.
   * @param reader The streams from which this node will read input.
   * @param writer The streams into which this node will write output.
   */
  public PipeNode(PipeStream.In<I> reader, PipeStream.Out<O> writer)
  {
    this.reader = reader;
    this.writer = writer;
  }
  
  /**
   * The method invoked during each iteration of the pipeline.  This method
   * should: read inputs from the streams, perform the necessary work on the
   * inputs to generate outputs, and send the output to the outgoing streams.
   */
  public abstract void processInputs();

  @Override
  public void run()
  {

    while (true)
    {
      processInputs();
    }
  }
}
