package hw3;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

public class part_2 {
	
	public Mat file2Mat(File file)
	{	
		Mat mat = Highgui.imread(file.getAbsolutePath());
		return mat;
	}
	
	public static List<Mat> randomSet(int m) throws IOException{
		List<Mat> images = new ArrayList<Mat>();
		List<Mat> eigencraters = new ArrayList<Mat>();
		int Min = 1;
		int Max = 2631;
		for (int i=0; i<m; i++){
			int random = Min + (int)(Math.random() * ((Max - Min) + 1));
			File file = new File("./CraterRecognitionData/craters/image ("+random+").pgm");			
			System.out.println(file.getAbsolutePath());
			Mat image = Highgui.imread(file.getAbsolutePath(), CvType.CV_64FC1);
			images.add(image);
		}
		
		List<Mat> data = new ArrayList<Mat>();
		for(int i=0; i<images.size(); i++){
			data.add(images.get(i).reshape(0, 784));
		}
		
		for(int i=0; i<784; i++){
			int sum = 0;		
			for(int j=0; j<data.size(); j++){
				sum+=data.get(j).get(i, 0)[0];
				//System.out.println(data.get(j).dump());
			}
			for(int j=0; j<data.size(); j++){
				data.get(j).get(i, 0)[0]-=sum/m;
				//System.out.println(data.get(j).dump());
			}
		}
		
		Mat A = new Mat(784, m, CvType.CV_64FC1);
		
		for(int i=0; i<m; i++){
			for(int j=0; j<784; j++){
				A.put(j, i, data.get(i).get(j, 0));
			}
		}
		Mat result = new Mat(m, m, CvType.CV_64FC1);
		Core.gemm(A.t(), A, 1, new Mat(), 0, result);
		
		Mat eigen_val = new Mat();
		Mat eigen_vec = new Mat();
		Core.eigen(result, true, eigen_val, eigen_vec);
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a value:");
		int new_m = Integer.parseInt(in.nextLine());
		in.close();
		
		List<Mat> eigen_vec_list = new ArrayList<Mat>();
		for (int i=0; i<new_m; i++){
			eigen_vec_list.add(eigen_vec.col(i));
		}

		for(int i=0; i<eigen_vec_list.size(); i++){
			Mat eigencrater = new Mat(784, 1, CvType.CV_64FC1);
			Core.gemm(A, eigen_vec_list.get(i), 1, new Mat(), 0, eigencrater);
			eigencraters.add(eigencrater);
		}
		
		/*for(int i=0; i<5; i++){
			Mat image = new Mat();
			image=eigencraters.get(i).reshape(0,28);
			Highgui.imwrite("output/"+i+".jpg", image);
			LoadImage("output/"+i+".jpg");
		}*/
		
		return images;
	}
	
	public static BufferedImage mat2Img(Mat mat) throws IOException{		
		MatOfByte bytemat = new MatOfByte();	
		Highgui.imencode(".jpg", mat, bytemat);	
		byte[] bytes = bytemat.toArray();	
		InputStream in = new ByteArrayInputStream(bytes);	
		BufferedImage img = ImageIO.read(in);
		return img;
	}
	
	public static void main(String[] args) throws IOException {
		List<Mat> TD = randomSet(50);
		//BufferedImage image = ImageIO.read(new File("./CraterRecognitionData/craters/image (1).pgm"));
		System.out.println("last"+TD);
		for(int i=0;i<TD.size();i++){
			//System.out.println(TD.get(i));
		}
	}
}
