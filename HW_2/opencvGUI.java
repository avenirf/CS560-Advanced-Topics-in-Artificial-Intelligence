package hw2;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.Image;
import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.Scrollbar;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.BoxLayout;

import java.awt.CardLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import javax.swing.JEditorPane;
import javax.swing.JTextField;

public class opencvGUI {
	
	imageCreation image = new imageCreation();
	Mat mat = new Mat();
	
	JFileChooser fc = new JFileChooser();
	
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					opencvGUI window = new opencvGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public opencvGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 956, 608);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JPanel panel = new JPanel();
		
		final JPanel panel_1 = new JPanel();
		
		final JSlider slider = new JSlider();
		slider.setMajorTickSpacing(5);
		slider.setToolTipText("");
		slider.setSnapToTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMaximum(150);
		
		final JSlider slider_1 = new JSlider();
		slider_1.setMajorTickSpacing(5);
		slider_1.setSnapToTicks(true);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setMinorTickSpacing(1);
		slider_1.setMaximum(150);
		
		final JButton btnNewButton = new JButton("Open...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();	
				int returnVal = fc.showOpenDialog(null);
				System.out.println(fc.getSelectedFile());
		        //File file = new File(fc.getSelectedFile().getPath());
		        //System.out.println(file);
				
		        mat = Highgui.imread(fc.getSelectedFile().getAbsolutePath());
		        
		        try {
					image.setImage(ImageIO.read(fc.getSelectedFile()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(fc.getSelectedFile().getName().equals("balloon2.jpg")||fc.getSelectedFile().getName().equals("balloon5.jpg")||
						fc.getSelectedFile().getName().equals("balloon22.jpg")){
					
					BufferedImage newImage = null;
					try {
						newImage = image.mat2Img(mat);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Image scaledImage = image.getImage().getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_SMOOTH);
			        panel.removeAll();
			        panel.add(new JLabel(new ImageIcon(scaledImage)));
					
			        Image scaledImage2 = newImage.getScaledInstance(panel_1.getWidth(),panel_1.getHeight(),Image.SCALE_SMOOTH);
					panel_1.removeAll();
					panel_1.add(new JLabel(new ImageIcon(scaledImage2)));
					
				} else{
			        
			        //mat = image.file2Mat(fc.getSelectedFile());
			        //Mat mat = image.matify(image.getImage());
			        //File file = new File("/testdata/apple1.jpg");
			        
			        panel.removeAll();
			        panel.add(new JLabel(new ImageIcon(image.getImage())));
			        
			        BufferedImage newImage = null;
					try {
						newImage = image.mat2Img(mat);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					image.setImg(mat);
					
			        panel_1.removeAll();
			        panel_1.add(new JLabel(new ImageIcon(newImage)));
				}
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		JButton btnTogray = new JButton("toGray");
		btnTogray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        BufferedImage newImage = null;
				try {
					newImage = image.mat2Img(image.toGray(mat));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        panel_1.removeAll();
		        panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		JButton btnResize = new JButton("reSize");
		btnResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				double input = Double.parseDouble(textField.getText());
				
		        BufferedImage newImage = null;
				try {
					newImage = image.mat2Img(image.resizeImage(mat, input));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        panel_1.removeAll();
		        panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		textField = new JTextField();
		textField.setText("40");
		textField.setColumns(10);
		
		JButton btnLinedet = new JButton("lineDet");
		btnLinedet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        BufferedImage newImage = null;
				try {
					newImage = image.mat2Img(image.lineDetection(mat));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        panel_1.removeAll();
		        panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		JButton btnCanny = new JButton("Canny");
		btnCanny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        BufferedImage newImage = null;
				try {
					newImage = image.mat2Img(image.cannyDetection(mat));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        panel_1.removeAll();
		        panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		JButton btnCoindet = new JButton("coinDet");
		btnCoindet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//System.out.println(fc.getSelectedFile().getName());
				
		        BufferedImage newImage = null;
				try {
					if (fc.getSelectedFile().getName().equals("coins1.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 50, 10, 150));
					} else if(fc.getSelectedFile().getName().equals("coins2.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 50, 32, 35));
					} else {
						int input = Integer.parseInt(textField.getText());
						newImage = image.mat2Img(image.coinDetection(mat, input, slider.getValue(), slider_1.getValue()));
					}
					//50.150.10
					//50.35.32
					//40.30.65
					//50.100.40
					
					//50.40.20
					//30.37.20
					
					//30.30.17
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        panel.removeAll();
		        panel.add(new JLabel(new ImageIcon(image.getImage())));
				
				panel_1.removeAll();
				panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		JButton btnOtsu = new JButton("otsu");
		btnOtsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        BufferedImage newImage = null;
				try {
					newImage = image.mat2Img(image.otsuThres(mat));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        panel_1.removeAll();
		        panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
			}
		});
		
		JLabel label = new JLabel("");
		
		JLabel lblNewLabel = new JLabel("");
		
		JButton btnBalldet = new JButton("ballDet");
		btnBalldet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//System.out.println(fc.getSelectedFile().getName());
				
		        BufferedImage newImage = null;
				try {
					if (fc.getSelectedFile().getName().equals("balloon1.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 50, 40, 100));
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(newImage)));
					} else if(fc.getSelectedFile().getName().equals("balloon2.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 40, 40, 55));
						
						Image scaledImage = image.getImage().getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_SMOOTH);
				        panel.removeAll();
				        panel.add(new JLabel(new ImageIcon(scaledImage)));
						
				        Image scaledImage2 = newImage.getScaledInstance(panel_1.getWidth(),panel_1.getHeight(),Image.SCALE_SMOOTH);
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(scaledImage2)));
						
					} else if(fc.getSelectedFile().getName().equals("balloon3.jpg")) {
						newImage = image.mat2Img(image.coinDetection(mat, 40, 50, 50));
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(newImage)));
					} else if(fc.getSelectedFile().getName().equals("balloon4.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 30, 20, 37));
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(newImage)));
					} else if(fc.getSelectedFile().getName().equals("balloon5.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 50, 45, 110));
						
						Image scaledImage = image.getImage().getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_SMOOTH);
				        panel.removeAll();
				        panel.add(new JLabel(new ImageIcon(scaledImage)));
						
				        Image scaledImage2 = newImage.getScaledInstance(panel_1.getWidth(),panel_1.getHeight(),Image.SCALE_SMOOTH);
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(scaledImage2)));
					} else if(fc.getSelectedFile().getName().equals("balloon6.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 30, 17, 30)); 
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(newImage)));
					} else if(fc.getSelectedFile().getName().equals("balloon20.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 40, 30, 40)); 
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(newImage)));
					} else if(fc.getSelectedFile().getName().equals("balloon22.jpg")){
						newImage = image.mat2Img(image.coinDetection(mat, 40, 50, 57));	
						
						Image scaledImage = image.getImage().getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_SMOOTH);
				        panel.removeAll();
				        panel.add(new JLabel(new ImageIcon(scaledImage)));
						
				        Image scaledImage2 = newImage.getScaledInstance(panel_1.getWidth(),panel_1.getHeight(),Image.SCALE_SMOOTH);
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(scaledImage2)));
					} else {					
						int input = Integer.parseInt(textField.getText());
						newImage = image.mat2Img(image.coinDetection(mat, input, slider.getValue(), slider_1.getValue()));
						panel_1.removeAll();
						panel_1.add(new JLabel(new ImageIcon(newImage)));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        frame.revalidate();
		        frame.repaint();
				
			}
		});
		
		JButton btnBalldet_1 = new JButton("ballDet2");
		btnBalldet_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        BufferedImage newImage = null;
				try {
					newImage = image.mat2Img(image.balloonDetection(mat));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        panel_1.removeAll();
		        panel_1.add(new JLabel(new ImageIcon(newImage)));
		        
		        frame.revalidate();
		        frame.repaint();
				
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(label)
											.addGap(82))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnNewButton)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnTogray)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnResize)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnCanny)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnOtsu)
											.addGap(163)
											.addComponent(btnLinedet)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnCoindet)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnBalldet)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnBalldet_1)
											.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGap(905))))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(panel, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
									.addGap(68)))
							.addGap(0))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(slider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE)
								.addComponent(slider_1, GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE))
							.addGap(68))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTogray)
						.addComponent(btnResize)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCanny)
						.addComponent(btnOtsu)
						.addComponent(btnNewButton)
						.addComponent(btnCoindet)
						.addComponent(btnLinedet)
						.addComponent(btnBalldet)
						.addComponent(btnBalldet_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))))
					.addContainerGap())
		);
		
		frame.getContentPane().setLayout(groupLayout);
	}
}
