package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class ResetFlag implements Runnable {
Thread resetflag;
  boolean running = true;

public ResetFlag(String name){
resetflag = new Thread(this, name);
}

  /**
   * Method to shut the thread down
   */
  public void shutdown(){
    running = false;
  }

  @Override
  public void run() {
    while(running){


    }

  }
}
