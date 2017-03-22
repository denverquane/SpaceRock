package fpga.pipelinetest;

import static fpga.pipelinetest.Testing.assertEquals;
import static fpga.pipelinetest.Testing.assertTrue;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import fpga.pipeline.GaussianFilter;
import fpga.pipeline.Threshold;
import fpga.pipeline.PipeStream;

import javax.imageio.ImageIO;

/**
 * Tests the Threshold class with and without Gaussian Blur.
 * Created by Jessica Dudek on 3/20/2017.
 */
public class ThresholdTest extends Threshold {


    public ThresholdTest(PipeStream.In<BufferedImage> reader,
                         PipeStream.Out<boolean[][]> writer) {
        super(reader, writer);
    }

    public void cornerWhiteDebrisTest() throws Exception {
        int[] white = {255,255,255,255};

        int dimension = 32;
        int border = Threshold.BORDER_SIZE;

        BufferedImage img = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setColor ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
        WritableRaster raster = img.getRaster();

        boolean[][] desiredResults = new boolean[32][32];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (    i < border
                        || j < border
                        || i >= dimension - border
                        || j >= dimension - border ) {
                    desiredResults[i][j] = false;
                }
                else if (i < 16 && j < 16){

                    raster.setPixel(i, j, white);
                    desiredResults[i][j] = true;
                }
                else{
                    desiredResults[i][j] = false;
                }
            }
        }
        deepEquals(desiredResults, Threshold.threshold(img));
    }

    public void blurCornerWhiteDebrisTest() throws Exception {
        int[] white = {255,255,255,255};
        int dimension = 32;
        int border = Threshold.BORDER_SIZE;

        BufferedImage img = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setColor ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
        WritableRaster raster = img.getRaster();

        boolean[][] desiredResults = new boolean[32][32];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (    i < border
                        || j < border
                        || i >= dimension - border
                        || j >= dimension - border ) {
                    desiredResults[i][j] = false;
                }
                else if (i < 16 && j < 16){

                    raster.setPixel(i, j, white);
                    desiredResults[i][j] = true;
                }
                else{
                    desiredResults[i][j] = false;
                }
            }
        }
        img = GaussianFilter.blur(img);
        deepEquals(desiredResults, Threshold.threshold(img));
    }


    public void manyWhiteDebrisTest() throws Exception {
        int[] white = {255,255,255,255};

        int dimension = 32;
        int border = Threshold.BORDER_SIZE;

        BufferedImage img = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setColor ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
        WritableRaster raster = img.getRaster();

        boolean[][] desiredResults = new boolean[32][32];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (    i < border
                        || j < border
                        || i >= dimension - border
                        || j >= dimension - border ) {
                    desiredResults[i][j] = false;
                }
                else if (i % 7 == j % 6){

                    raster.setPixel(i, j, white);
                    desiredResults[i][j] = true;
                }
                else{
                    desiredResults[i][j] = false;
                }
            }
        }
        deepEquals(desiredResults, Threshold.threshold(img));
    }


    public void blurManyWhiteDebrisTest() throws Exception {
        int[] white = {255,255,255,255};

        int dimension = 32;
        int border = Threshold.BORDER_SIZE;

        BufferedImage img = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setColor ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
        WritableRaster raster = img.getRaster();

        boolean[][] desiredResults = new boolean[32][32];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (    i < border
                        || j < border
                        || i >= dimension - border
                        || j >= dimension - border ) {
                    desiredResults[i][j] = false;
                }
                else if (i % 7 == j % 6){

                    raster.setPixel(i, j, white);
                    desiredResults[i][j] = true;
                }
                else{
                    desiredResults[i][j] = false;
                }
            }
        }
        img = GaussianFilter.blur(img);
        deepEquals(desiredResults, Threshold.threshold(img));
    }

    private void deepEquals(boolean[][] a, boolean[][] b) throws Exception {
        assertEquals( a.length, b.length);
        assertEquals( a[0].length, b[0].length);
        int x = a.length;
        int y = a[0].length;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                assertEquals(a[i][j], b[i][j]);
            }
        }
    }




    public static void main(String args[]) throws IOException {

        PipeStream<BufferedImage> reader = new PipeStream<>();
        PipeStream<boolean[][]> writer = new PipeStream <>();
        ThresholdTest test = new ThresholdTest(reader.getReadEnd(), writer.getWriteEnd());
        try {
            test.cornerWhiteDebrisTest();
            System.err.println("Test passed: Corner");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        try {
            test.blurCornerWhiteDebrisTest();
            System.err.println("Test passed: Corner with Gaussian blur");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        try {
            test.manyWhiteDebrisTest();
            System.err.println("Test passed: Many debris");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        try {
            test.blurManyWhiteDebrisTest();
            System.err.println("Test passed: Many debris with Gaussian blur");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }


    }
}
