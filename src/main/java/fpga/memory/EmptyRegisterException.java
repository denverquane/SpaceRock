package fpga.memory;

/**
 * Created by jdt on 3/25/17.
 */
public class EmptyRegisterException extends MemoryMapException {
  EmptyRegisterException(String message) {
    super(message);
  }
}
