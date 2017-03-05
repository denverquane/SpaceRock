package fpga;

/**
 * A pipe node object that represents the node of a pipeline.  Each node has a
 * set of streams from which it reads input and another  set of streams into
 * which output is written, with a single stream between each communicating
 * node in the pipeline.
 */

public abstract class PipeNode implements Runnable
{
  protected PipeStream [] readers;
  protected PipeStream [] writers;
  
  /**
   * Set up a new node.  All streams must be set up between the communicating
   * nodes and organized into an array beforehand.
   * @param readers The streams from which this node will read input.
   * @param writers The streams into which this node will write output.
   */
  public PipeNode(PipeStream [] readers, PipeStream [] writers)
  {
    this.readers = readers;
    this.writers = writers;
  }
  
  /**
   * Read and record the necessary information from the input streams so
   * that processing can be done.  No writing information to output streams
   * should be done here so as to avoid deadlock.  When processing, read and
   * write a single object at a time from each stream.  If multiple objects
   * need to be sent between nodes, group them together in an array or
   * collection and send that instead.
   */
  public abstract void readInputs();
  
  /**
   * Here's where the node performs whatever work it needs to with its newest
   * input to generate output.  Should not read from or write to the output
   * streams here.
   */
  public abstract void processInputs();
  
  /**
   * Write the appropriate output to the appropriate output streams.
   * No reading should be done here.
   */
  public abstract void writeOutputs();

  @Override
  public void run()
  {
    while (true)
    {
      readInputs();
      processInputs();
      writeOutputs();
    }
  }
}
