package hw2;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.opencv.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class imageCreation {
	
	BufferedImage image;
	Mat img;
	
	public static Mat otsuThres(Mat in){
		Mat out = in.clone();
		Mat gray = imageCreation.toGray(out);
		
		Mat gaussian = new Mat();
		Imgproc.GaussianBlur(gray, gaussian, new Size (3,3), Imgproc.CV_GAUSSIAN);
		
		Mat thres = new Mat();
		Imgproc.threshold(gaussian, thres, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
		
		return thres;
	}
	
	public static Mat balloonDetection(Mat in){
		Mat out = in.clone();
		Mat gray = imageCreation.toGray(out);
		Mat gaussian = new Mat();
		Imgproc.GaussianBlur(gray, gaussian, new Size (3,3), 1);
		Mat edges = new Mat();
		Imgproc.Canny(gray, edges, 30, 90);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		Imgproc.drawContours(out, contours, -1, new Scalar(0,0,255), 3);
		return out;		
	}
	
	public static Mat cannyDetection(Mat in){
		Mat out = in.clone();
		Mat gray = imageCreation.toGray(out);
		Mat edges = new Mat();
		Imgproc.Canny(gray, edges, 50, 150, 3, false);
		return edges;
	}
	
	public static Mat coinDetection(Mat in, int distanse, int parameter1, int parameter2){
		Mat out = in.clone();
		Mat gray = imageCreation.toGray(out);
		
		Mat gaussian = new Mat();
		Imgproc.GaussianBlur(gray, gaussian, new Size (11,11), Imgproc.CV_GAUSSIAN);
		
		Mat thres = new Mat();
		Imgproc.threshold(gaussian, thres, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
		
		Mat morph = new Mat();
		Imgproc.morphologyEx(thres, morph, Imgproc.MORPH_CLOSE, new Mat(5,5, CvType.CV_8U));
		
		Mat edges = new Mat();
		Imgproc.Canny(thres, edges, 50, 150, 3, false);				
		
		Mat circles = new Mat();
		Imgproc.HoughCircles(gaussian, circles, Imgproc.CV_HOUGH_GRADIENT, 1, distanse, parameter1, parameter2, 0, 0 );
		if (circles.cols() > 0) {
			 for (int x = 0; x < circles.cols(); x++) 
		        {
		        double vCircle[] = circles.get(0,x);

		        if (vCircle == null)
		            break;

		        Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
		        int radius = (int)Math.round(vCircle[2]);

		        // draw the found circle
		        Core.circle(out, pt, radius, new Scalar(0,255,0), 3);
		        Core.circle(out, pt, 1, new Scalar(0,0,255), 3);
		        }
			 
        } else {
                //Log.i("Circle :", "No");
        }
		
		return out;
	}

	public static Mat lineDetection(Mat in){
		int minLineLength = 100;
		int maxLineGap = 10;
		double theta = Math.PI/180;
		Mat out = in.clone();
		Mat gray = imageCreation.toGray(out);
		Mat edges = new Mat();
		Imgproc.Canny(gray, edges, 50, 150, 3, false);
		Mat lines = new Mat();
		Imgproc.HoughLinesP(edges, lines, 1, theta, 100, minLineLength, maxLineGap);
		
		 for (int x = 0; x < lines.cols(); x++) 
		    {
		          double[] vec = lines.get(0, x);
		          double x1 = vec[0], 
		                 y1 = vec[1],
		                 x2 = vec[2],
		                 y2 = vec[3];
		          Point start = new Point(x1, y1);
		          Point end = new Point(x2, y2);

		          Core.line(out, start, end, new Scalar(255,0,0), 2);

		    }
			return out;
	}
	
	public static Mat resizeImage(Mat in, double size){
		double newx = in.height()*size;
		double newy = in.width()*size;
		Size newSize = new Size(newx, newy);;
		Mat out = in.clone();
		Imgproc.resize(in, out, newSize);
		return out;
		
	}
	
	public static Mat toGray(Mat in){
		Mat out = in.clone();
		Imgproc.cvtColor(in, out, Imgproc.COLOR_RGB2GRAY);
		return out;
	}
	
	public Mat file2Mat(File file)
	{	
		Mat mat = Highgui.imread(file.getAbsolutePath());
		return mat;
	}
	
	public static BufferedImage mat2Imgg(Mat in)
    {
        BufferedImage out;
        byte[] data = new byte[in.height() * in.width() * (int)in.elemSize()];
        int type;
        in.get(0, 0, data);

        if(in.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(in.width(), in.height(), type);

        out.getRaster().setDataElements(0, 0, in.width(), in.height(), data);
        return out;
    } 
	
	public static BufferedImage mat2Img(Mat mat) throws IOException{		
		MatOfByte bytemat = new MatOfByte();	
		Highgui.imencode(".jpg", mat, bytemat);	
		byte[] bytes = bytemat.toArray();	
		InputStream in = new ByteArrayInputStream(bytes);	
		BufferedImage img = ImageIO.read(in);
		return img;
	}

	public Mat getImg() {
		return img;
	}

	public void setImg(Mat img) {
		this.img = img;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
}
