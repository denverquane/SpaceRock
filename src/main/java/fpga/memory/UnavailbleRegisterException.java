package fpga.memory;

import fpga.memory.MemoryMapException;

public class UnavailbleRegisterException extends MemoryMapException {
  UnavailbleRegisterException(String message) {
    super(message);
  }
}