package fpga;

/**
 * Until we decide a unit testing framework, this one can do basic asserts.
 *
 */
public class Testing {

  public static class TestException extends Exception{
    public TestException(String message){
      super(message);
    }

  }
  public static void assertEquals(Object expected, Object actual) throws Exception{

    if(!expected.equals(actual)){
      throw new TestException(String.format("Expected %s, Found %s", expected.toString(), actual.toString()));
    }
  }

  public static void assertTrue(Boolean result) throws Exception {
    if(result != true){
      throw new TestException(String.format("Expected true, Found %s", result.toString()));
    }
  }
}
