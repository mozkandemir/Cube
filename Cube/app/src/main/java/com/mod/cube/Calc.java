package com.mod.cube;
import java.util.Random;

public class Calc {
	
	public static Random random = new Random();

	public static double map(double x, double in_min, double in_max, double out_min, double out_max){
		  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
}
