package fpga;

import fpga.objectdetection.Debris;

/**
 * Created by David M on 3/4/17.
 *
 * Report Debris Detection
 * [FROM DR. ROMAN]
 * “RDD gets the extraction result from Extract and writes the description of the detected debris,
 * a success flag, and a completion flag that indicates is done.
 * When no debris is found, the success flag is set to false and the completion flag is set to true.
 * The PC Board needs to reset the flags and the debris data.”
 *
 */
public class ReportDebrisDetection
{
  // Called by the "Extract" Pipeline node
  public static void reportDetectedDebris(Debris[] detectedDebris) {
    // TODO: Pass the detected debris to the memory map

    // TODO: Set the flag for "no data" if there is none or "processed data" if there is debris

    // TODO: Set the "data is processing" flag to false
  }
}
