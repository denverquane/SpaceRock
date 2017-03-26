package debrisProcessingSubsystem.cameraComponent;

/**
 * Created by jdt on 3/26/17.
 */
public class CameraStatusReport {
  private boolean isOn;
  private boolean isReady;
  private int zoomLevel;

  public CameraStatusReport(){
    isOn = false;
    isReady = false;
    zoomLevel = 1;
  }
  public CameraStatusReport(boolean isOn, boolean isReady, int zoomLevel){
    this.isOn = isOn;
    this.isReady = isReady;
    this.zoomLevel = zoomLevel;
  }

  public CameraStatusReport(CameraStatusReport other){
    this.isOn = other.isOn();
    this.isReady = other.isReady();
    this.zoomLevel = other.zoomLevel();
  }

  /**
   * Update camera status report with another status report. This method copies the
   * values of other to this CameraStatusReport.
   * @param other The CameraStatusReport to copy values from.
   */
  public void updateStatus(CameraStatusReport other){
    this.isOn = other.isOn();
    this.isReady = other.isReady();
    this.zoomLevel = other.zoomLevel();
  }

  /**
   * Manually set all fields in this CameraStatusReport.
   * @param isOn Is the camera on?
   * @param isReady Is the camera ready to take a picture?
   * @param zoomLevel What is the zoom level setting.
   */
  public void updateStatus(boolean isOn, boolean isReady, int zoomLevel){
    this.isOn = isOn;
    this.isReady = isReady;
    this.zoomLevel = zoomLevel;
  }

  public boolean isOn(){
    return isOn;
  }

  public void setIsOn(boolean value){
    isOn = value;
  }

  public boolean isReady(){
    return isReady;
  }

  public void setIsReady(boolean value){
    isReady = value;
  }

  public int zoomLevel(){
    return zoomLevel;
  }

  public void setZoomLevel(int value){
    zoomLevel = value;
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(" On: " + isOn + " Ready: " + isReady + " Zoom: " + zoomLevel);
    return sb.toString();
  }
}
