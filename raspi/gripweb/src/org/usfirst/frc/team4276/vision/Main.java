package org.usfirst.frc.team4276.vision;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.opencv.core.*;
import org.opencv.videoio.*;

public class Main {

	public static VideoServer myVideoServer;
	public static Boolean isShuttingDown = false;
	
	private static BufferedImage image;
	private static VideoCapture camera;
	
	public static void main(String [] args) {
		System.out.println("Core.NATIVE_LIBRARY_NAME = " + Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//start video server thread
		try {
			myVideoServer = new VideoServer(5814);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myVideoServer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        camera = new VideoCapture(0);
        Mat frame = new Mat();
        camera.read(frame); 

        if(!camera.isOpened()){
            System.out.println("Camera not opened");
        }
        else {                         
			while (!isShuttingDown)
			{
	            if (camera.read(frame)) {
	            	try {
						myVideoServer.sendImage(frame);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
		    }
        }
	    camera.release();

		try {
			myVideoServer.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("VideoServer test stopped");

	}
}
