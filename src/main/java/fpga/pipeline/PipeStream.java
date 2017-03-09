package fpga.pipeline;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * A pipe stream object that represents the connection between different nodes
 * of a pipeline.  Both communicating objects must have a copy of the same
 * pipe stream.  Once established, objects may be written and read by the
 * communicating nodes.  Best to have one pipe stream object per unidirectional
 * communication - with one end being the "reading" end and the other end being
 * the "writing" end, similar to a normal pipe.
 */

public class PipeStream <T>
{


  private final In<T> in;
  private final Out<T> out;

  public PipeStream() {
    this(100);
  }

  /**
   * Constructs a pipe stream object with a given capacity, or number of inputs
   * that can be queued without the method call blocking the caller.
   * @param capacity Number of requests that can be queued.
   */
  public PipeStream(int capacity)
  {
    LinkedBlockingQueue<T> q = new LinkedBlockingQueue<>(capacity);
    in  = new In<>(q);
    out = new Out<>(q);
  }
  
//  /**
//   * Read the most recent input written to this particular stream.  Blocks
//   * until input is received.
//   * @return The head of the queue of inputs.
//   * @throws InterruptedException
//   */
//  public T read() throws InterruptedException
//  {
//    return queue.take();
//  }
//
//  /**
//   * Write an output to this particular stream.  Will only block if the queue
//   * is full - otherwise the input will be queued.
//   * @param obj The object to be written.
//   * @throws InterruptedException
//   */
//  public void write(T obj) throws InterruptedException
//  {
//    queue.put(obj);
//  }

  public In<T> getReadEnd(){
    return in;
  }

  public Out<T> getWriteEnd(){
    return out;
  }


  public static class In<T> {
    private final LinkedBlockingQueue<T> queue;

    private In(LinkedBlockingQueue<T> q) {
      queue = q;
    }

    public T read() throws InterruptedException{
      return queue.take();
    }
  }

  public static class Out <T> {

    private final LinkedBlockingQueue<T> queue;

    private Out(LinkedBlockingQueue<T> q) {
      queue = q;
    }

    public void write(T t) throws InterruptedException{
      queue.put(t);
    }
  }
}
