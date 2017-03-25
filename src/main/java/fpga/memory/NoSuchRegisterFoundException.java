package fpga.memory;

/**
 * Created by jdt on 3/25/17.
 */
public class NoSuchRegisterFoundException extends MemoryMapException {
  NoSuchRegisterFoundException(String message) {
    super(message);
  }
}
