package hw3;

//package com.hw2;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.*;

class MyPoint implements Comparable<MyPoint>{
	@Override
	public String toString() {
		return "x:"+this.x + "y:" + this.y + " z:" + this.z + " | " ;
	}
	
	@Override
	public int hashCode() {
		return this.x * 1000 + this.y + this.z;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.x == ((MyPoint) obj).getX() && this.y ==  ((MyPoint) obj).getY() && this.z == ((MyPoint) obj).getZ();
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int x = 0;
	private int y = 0;
	private int z = 0;

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	MyPoint(int x1, int y1,int z1) {
		this.x = x1;
		this.y = y1;
		this.z = z1;
	}

	@Override
	public int compareTo(MyPoint o) {
		// TODO Auto-generated method stub
		if(this.x < o.getX()){
			return -1;
		}else if(this.x == o.getX()){
			if (this.y < o.getY()){
				return -1;
			}else if (this.y == o.getY()){
				return (int) (this.z - o.getZ());
			}else{
				return 1;
			}
			
		}else{
			return 1;
		}
	}
}

class MyCodeWord implements Comparable<MyCodeWord>{
	@Override
	public String toString() {
		return "x:"+this.x + "y:" + this.y + " z:" + this.z + " | " ;
	}
	
	@Override
	public int hashCode() {
		return (int) (this.x * 1000 + this.y + this.z);
	}
	
	@Override
	public boolean equals(Object obj) {
		return Math.abs(this.x - ((MyPoint) obj).getX() + 
				this.y -  ((MyPoint) obj).getY() + 
				this.z - ((MyPoint) obj).getZ()
				) < 0.001 ? true : false;
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	private double x = 0;
	private double y = 0;
	private double z = 0;
	

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	MyCodeWord(double x1, double y1,double z1) {
		this.x = x1;
		this.y = y1;
		this.z = z1;
	}


	@Override
	public int compareTo(MyCodeWord o) {
		// TODO Auto-generated method stub
		if(this.x < o.getX()){
			return -1;
		}else if(this.x == o.getX()){
			if (this.y < o.getY()){
				return -1;
			}else if (this.y == o.getY()){
				return (int) (this.z - o.getZ());
			}else{
				return 1;
			}
			
		}else{
			return 1;
		}
		
	}
}

public class ColorUtil {
	
	
	public static double EuclideanDistance(MyPoint point, MyPoint target) {
		double result = 0;
		result =  Math.sqrt((point.getX() - target.getX()) * (point.getX() - target.getX()) 
				+ (point.getY() - target.getY()) * (point.getY() - target.getY())
				+(point.getZ() - target.getZ())*(point.getZ() - target.getZ()) 
				);
		
		return result;
	}
	
	public static double EuclideanDistance(MyCodeWord point, MyPoint target) {
		double result = 0;
		result =  Math.sqrt((point.getX() - target.getX()) * (point.getX() - target.getX()) 
				+ (point.getY() - target.getY()) * (point.getY() - target.getY())
				+ (point.getZ() - target.getZ()) * (point.getZ() - target.getZ()) 
				);
		
		return result;
	}

	public static void main(String[] args) throws IOException {

	
		
		String fileName = "/Users/hehehehehe/Desktop/mac/576/final/CSCI576_Project_Database/flowers/flowers001.rgb";
		//String fileName = "/Users/chongli/Desktop/576/final/CSCI576_Project_Database/interview/interview350.rgb";
		
		TreeMap<MyCodeWord, HashSet<MyPoint>> result = ColorUtil.getDominateColor(fileName);

		// Use a panel and label to display the image
//		JPanel panel = new JPanel();
//		panel.add(new JLabel(new ImageIcon(imgLeft)));
//
//		JFrame frame = new JFrame("Display images");
//
//		frame.getContentPane().add(panel);
//		frame.pack();
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static TreeMap<MyCodeWord, HashSet<MyPoint>> getDominateColor(String fileName) {
		int equalParam = 0;
		int width = 352;
		int height = 288;

		int[][][] q_bytes = new int[256][256][256];
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				for(int k = 0; k < 256;k++){
					q_bytes[i][j][k] = 0;
				}
				
			}
		}
		BufferedImage imgLeft = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		
		TreeMap<MyCodeWord, HashSet<MyPoint>> codebook = new TreeMap<MyCodeWord, HashSet<MyPoint>>();
		try {
			File file = new File(fileName);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytesLeft = new byte[(int) len];
			
			int offset = 0;
			int numRead = 0;
			while (offset < bytesLeft.length && (numRead = is.read(bytesLeft, offset, bytesLeft.length - offset)) >= 0) {
				offset += numRead;
			}
			is.close();

			////////////init //////////////////////////////////////////////////
			HashSet<MyPoint> allPointSet = new HashSet<MyPoint>();
			int ind = 0;
			
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
			 
					byte a = 0;
					int g0 = bytesLeft[ind] & 0xff;
					int g1 = bytesLeft[ind+height*width]& 0xff ;
					int g2 = bytesLeft[ind+height*width*2]& 0xff ;
					
					int r = g0;
					int g = g1;
					int b = g2;
					
					q_bytes[g0][g1][g2] += 1;
					allPointSet.add(new MyPoint(g0, g1, g2));
					
					//int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					imgLeft.setRGB(x,y,pix);
					ind++;
				}
			}

			

			////////////init end//////////////////////////

			
			// get the first codebook
			int step1 = 32;
			int step2 = 192;
			int step3 = 192;
			for (int i1 = 0; i1 < 256; i1 += step1) {
				for (int j1 = 0; j1 < 256; j1 += step2) {
					for( int k1 = 0; k1 < 256; k1 += step3 ){
						int c1 = i1;
						int r1 = j1;
						int q1 = k1;
						
						HashSet<MyPoint> currentPoints1 = new HashSet<MyPoint>();
						
						for (c1 = i1; c1 < i1 + step1 && c1 < 256; c1++) {
							for (r1 = j1; r1 < j1 + step2 && r1 < 256; r1++) {
								for( q1 = k1; q1 < k1 + step3 && q1 < 256; q1++){
									
									for(int i = 0; i < q_bytes[c1][r1][q1]; i++){
										currentPoints1.add(new MyPoint(c1,r1,q1));
									}
								}
							
							}
						}
						//codebook.put(new MyCodeWord(c1 - step1 / 2, r1 - step2 / 2,q1 - step3 / 2), currentPoints1);
					}
					
				}
			}

//			codebook.put(new MyCodeWord(0,0,0), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(128,0,0), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(0,128,0), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(0,0,128), new HashSet<MyPoint>());
//			
//			codebook.put(new MyCodeWord(255,0,0), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(0,255,0), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(0,0,255), new HashSet<MyPoint>());
//			
//			codebook.put(new MyCodeWord(0,128,128), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(128,128,0), new HashSet<MyPoint>());
//			codebook.put(new MyCodeWord(128,0,128), new HashSet<MyPoint>());
			codebook.put(new MyCodeWord(128,128,128), new HashSet<MyPoint>());
			
			//System.out.println("done init codebook" );
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//run
			for(int round = 0; round < 500; round ++){
				///////////////////////round start/////////////////////
				
				//System.out.print("round: "+round + " " + "codebooksize: " + codebook.entrySet().size() + "||");
				//for(MyCodeWord keyMyPoint : codebook.keySet()){
				//	System.out.print( keyMyPoint.toString() );
				//}
				//System.out.println();
				///////////////print end/////////////
				
				///////////////clear/////////////
				for(MyCodeWord key:codebook.keySet()){
					codebook.get(key).clear();
				}
				///////////////clear end/////////////
				
				///////////////////////update points/////////////////////////////////////////////////////////
				for( MyPoint key : allPointSet){

					double minEuclideanDistance = Double.MAX_VALUE;
					MyCodeWord tempCodeWordPoint = null;
					Iterator<MyCodeWord> codeWordIterator = codebook.keySet().iterator();
					
					//for every codeword
					while(codeWordIterator.hasNext()){
						
						MyCodeWord currentCodeWord = codeWordIterator.next();
						double currentDistance = EuclideanDistance(currentCodeWord,key);
						if(minEuclideanDistance <= currentDistance){
							continue;
						}else{
							tempCodeWordPoint = currentCodeWord;
							minEuclideanDistance = currentDistance;
						}
					}
					
					//update codeword
					codebook.get(tempCodeWordPoint).add(key);
					
				}
				///////////////////////update points end/////////////////////////////////////////////////////////

				///////////////////////update codeword/////////////////////////////////////////////////////////
				Iterator<Entry<MyCodeWord, HashSet<MyPoint>>> codeBookIterator1 = codebook.entrySet().iterator();
				TreeMap<MyCodeWord,HashSet<MyPoint>> tempCodebook1 = new TreeMap<MyCodeWord, HashSet<MyPoint>>();
				while(codeBookIterator1.hasNext()){
					Entry<MyCodeWord, HashSet<MyPoint>> e =  codeBookIterator1.next();
					
					int xSum = 0;
					int ySum = 0;
					int zSum = 0;
					int count = 0;
					
					for(MyPoint key : e.getValue()){
						xSum += key.getX() * q_bytes[key.getX()][key.getY()][key.getZ()];
						ySum += key.getY() * q_bytes[key.getX()][key.getY()][key.getZ()];
						zSum += key.getZ() * q_bytes[key.getX()][key.getY()][key.getZ()];
						count += q_bytes[key.getX()][key.getY()][key.getZ()];
						
					}
					
					if(e.getValue().size() > 0){
						MyCodeWord newCodeWord = new MyCodeWord( (double)Math.rint(xSum*1.0 /count), (double) Math.rint(ySum*1.0/count),(double) Math.rint(zSum*1.0/count));
						tempCodebook1.put(newCodeWord, e.getValue());
					}else{
						tempCodebook1.put(e.getKey(), new HashSet<MyPoint>());
					}
					
				}
				///////////////////////update codeword end/////////////////////////////////////////////////////////
				
				if(codebook.equals(tempCodebook1)){
					equalParam ++;
					if(equalParam == 5){
						break;
					}
				}
				codebook.clear();
				codebook = tempCodebook1;
				
				///////////////////////round end/////////////////////////////////////////////////////////
			}
			
//			System.out.println("done codebook");
			for(MyCodeWord keyMyPoint : codebook.keySet()){
				System.out.println(keyMyPoint.toString() + codebook.get(keyMyPoint).size()) ;
			}
			
			
			System.out.println("done");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return codebook;
	}
}