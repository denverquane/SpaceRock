package fpga;

/**
 * Created by Ken Kressin on 13/3/17. Description:
 * Used to determine which FPGA flag we want to start, and how the flag thread will behave.
 * This matches the flags detailed in the SpaceRock Software Architecture Design document,
 * version 3.0, dated 7 March, 2017.
 */
public enum FPGAFlags {
  SET_GET_FRAME, ON_OFF, RESET, TAKE_IMAGE, ZOOM, IMAGE_CAPTURED
}
