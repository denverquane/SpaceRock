package fpga;

/**
 * Created by Ken Kressin on 4/3/17. Description:
 */
public class GetFrameFlag implements Runnable {
Thread framethread;
  boolean running = true;
  public GetFrameFlag(String name){
framethread = new Thread(this, name);
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
