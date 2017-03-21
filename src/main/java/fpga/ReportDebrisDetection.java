package fpga;

import fpga.memory.MemoryMap;
import fpga.pipeline.Extract;
import fpga.pipeline.MainPipeline;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by David M on 3/4/17.
 * <p>
 * Report Debris Detection
 * [FROM DR. ROMAN]
 * “RDD gets the extraction result from Extract and writes the description of the detected debris,
 * a success flag, and a completion flag that indicates is done.
 * When no debris is found, the success flag is set to false and the completion flag is set to true.
 * The PC Board needs to reset the flags and the debris data.”
 */

public class ReportDebrisDetection implements Runnable
{
  private final MainPipeline pipeline;
  private AtomicBoolean dataIsProcessing;
  private DebriDetectionStatus status;
  private final Object statusLock;

  public ReportDebrisDetection(MainPipeline pipeline)
  {
    this.pipeline = pipeline;
    dataIsProcessing = new AtomicBoolean(false);
    status = DebriDetectionStatus.NO_DATA;
    statusLock = new Object();
  }

  /**
   * This method returns a boolean which indicates whether our data
   * is currently processing
   * @return dataIsProcssing.get();
   */
  public boolean isProcessing()
  {
    return dataIsProcessing.get();
  }

  /**
   * This method sets our data processing boolean to true,
   * synchronizes on our statusLock object then determines
   * which case we have for our debris array. It then writes
   * our debris list to the memory map.
   */
  @Override
  public void run()
  {
    List<Extract.Debris> debris = pipeline.read();
    dataIsProcessing.set(true);
    synchronized (statusLock)
    {
      switch (debris.size())
      {
        case 0:
          status = DebriDetectionStatus.NO_DATA;
          break;
        case 1:
          status = DebriDetectionStatus.SINGLE;
          break;
        default:
          status = DebriDetectionStatus.MULTIPLE;
          break;
      }
    }

    try
    {
      MemoryMap.write("debris_list", debris);

    } catch (Exception e)
    {
      e.printStackTrace(System.err);
    } finally
    {
      dataIsProcessing.set(false);
    }
  }

  /**
   * This helper function is synchronized on our status lock
   * It returns the status of our debris detection process.
   * @return status
   */
  public DebriDetectionStatus getDetectionStatus()
  {
    synchronized (statusLock)
    {
      return status;
    }
  }
}
