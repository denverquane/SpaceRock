package fpga;

import fpga.memory.MemoryMap;
import fpga.pipeline.Extract;

import java.util.ArrayList;
import java.util.Collections;
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

public class ReportDebrisDetection
{
  private static AtomicBoolean dataIsProcessing = new AtomicBoolean(false);
  private static DebriDetectionStatus status = DebriDetectionStatus.NO_DATA;
  private static final Object statusLock = new Object();

  public static boolean isProcessing()
  {
    return dataIsProcessing.get();
  }

  // Called by the "Extract" Pipeline node
  public static void reportDetectedDebris(Extract.Debris[] detectedDebris)
  {
    dataIsProcessing.set(true);
    synchronized (statusLock)
    {
      switch (detectedDebris.length)
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

    ArrayList<Extract.Debris> list = new ArrayList<>(detectedDebris.length);
    Collections.addAll(list, detectedDebris);
    try
    {
      MemoryMap.write("debris_list", list);

    } catch (Exception e)
    {
      e.printStackTrace();
    } finally
    {
      dataIsProcessing.set(false);
    }

  }

  public static DebriDetectionStatus getDetectionStatus()
  {
    synchronized (statusLock)
    {
      return status;
    }
  }

}
