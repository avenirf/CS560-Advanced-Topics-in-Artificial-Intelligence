package hw3;

import org.opencv.core.Core;
import org.opencv.core.*;

public class part_1 {
	
	//double [ ] [ ] scores = new double [ 10 ] [ 2 ] ;//matrix 10 rows 2 columns
	static double [ ] [ ] data = {   
		{ 2.5, 2.4 },
        { 0.5, 0.7 },
        { 2.2, 2.9 },
        { 1.9, 2.2 },
        { 3.1, 3.0 },
        { 2.3, 2.7 },
        { 2.0, 1.6 },
        { 1.0, 1.1 },
        { 1.5, 1.6 },
        { 1.1, 0.9 }
	};
	
	static double firstColumn[] = {2.5, 0.5, 2.2, 1.9, 3.1, 2.3, 2.0, 1.0, 1.5, 1.1};
	static double secondColumn[] = {2.4, 0.7, 2.9, 2.2, 3.0, 2.7, 1.6, 1.1, 1.6, 0.9};

	public static void main(String[] args) {
	
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		double sum=0;
		for(int i=0; i<10; i++){
			sum+=data[i][0];
		}
		
		sum=sum/10;
		for(int i=0; i<10; i++){
			data[i][0]=data[i][0]-sum;
		}
		
		sum=0;
		
		for(int j=0; j<10; j++){
			sum+=data[j][1];
		}
		
		sum=sum/10;
		for(int j=0; j<10; j++){
			data[j][1]=data[j][1]-sum;
		}
		
		Mat mean = new Mat();
		Mat mat_data = new Mat(10, 2, CvType.CV_64FC1);
		for (int i=0;i<10;i++){
	        mat_data.put(i,0, data[i]);
		}
		Mat covar = new Mat();
		Mat eigenvalues = new Mat();
		Mat eigenvectors = new Mat(2,2,CvType.CV_64FC1);
		Core.calcCovarMatrix(mat_data, covar, mean, Core.COVAR_NORMAL | Core.COVAR_ROWS);
		Core.eigen(covar, true, eigenvalues, eigenvectors);
	
		Mat result = new Mat(2,10,CvType.CV_64FC1);
		
		Mat m1 = new Mat(CvType.CV_64FC1);
		Mat m2 = new Mat(CvType.CV_64FC1);
		m1=eigenvectors.t();
		m2=mat_data.t();

		Core.gemm(m1, m2, 1, new Mat(), 0, result);    
		
		String dump0 = mat_data.t().dump();
		System.out.println("dataadjust");
		System.out.println(dump0);
		
		String dump = covar.dump();
		System.out.println("covariance matrix");
		System.out.println(dump);
		
		System.out.println("eigenvalues");
		String dump2 = eigenvectors.t().dump();
		System.out.println(dump2);
		
		System.out.println("eigenvectors");
		String dump3 = eigenvectors.dump();
		System.out.println(dump3);
		
		System.out.println("result");
		String dump4 = result.dump();
		System.out.println(dump4);
	}
}