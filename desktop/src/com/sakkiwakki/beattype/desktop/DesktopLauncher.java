package com.sakkiwakki.beattype.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.sakkiwakki.beattype.BeatType;

import static com.sakkiwakki.beattype.BeatType.W_HEIGHT;
import static com.sakkiwakki.beattype.BeatType.W_WIDTH;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DesktopLauncher {
	
	
	private static int insertionsortexecutioncount = 0; 
	private static int selectionsortexecutioncount = 0; 
	private static ArrayList bruh = new ArrayList();
	private static ArrayList <Integer> intbruh = new ArrayList<Integer>(); 
	private static int[] sorting = {296, 917, 514, 412, 101, 54, 67, 3, 8};
    private static int[] sorting2 = {296, 917, 514, 412, 101, 54, 67, 3 , 8}; 
    private static Object[][] greatmusic = new Object[2][2]; 
    private static int[][] goodmusic = {{85, 91, 15, 16}, {4, 18, 21, 67}, {5, 39, 41, 84}};
    public static void rowSearch(int[][] arr) {
    	for (int i = 0; i < arr.length; i++)
    		for (int j = 0; j < arr[i].length; j++)
    			System.out.print(arr[i][j]);
		System.out.println();
	}
	public static void colSearch(int[][] arr) {
    	int j = 0;
    	int i = 0;
		for (i = 0; i < 4; i++)
			for (j = 0; j < 3; j++)
				System.out.print(arr[j][i]);
		System.out.println();
	}
    public static boolean searchIntBruh (int c)
    { 
    	boolean found = false; 
    	for(int i = 0; i < intbruh.size(); i++) 
    	{ 
    		if(intbruh.get(i)==(c)) 
    		{ 
    			 found = true; 
    			 break;
    		} 
    		found = false;
    	} 
    	return found;
    } 
    public static void insertionSort(int [] bleebruh) 
    { 
    	for (int i = 1; i < bleebruh.length; i++)
        { 
    		int temp = bleebruh[i]; 
    		int p = i; 
    		while (p > 0 && temp < bleebruh[p - 1]) 
    		{ 
    			bleebruh[p] = bleebruh[p - 1]; 
    			
    			p--; 
    		} 
    		bleebruh[p] = temp;	
    		insertionsortexecutioncount++;
    	} 	
    } 	
    public static void selectionSort(int [] sheshs) 
    { 
    	for (int i = 0; i < sheshs.length - 1; i++) 
    	{ 
    		int min = i; 
    		for (int h = i + 1; h < sheshs.length; h++)
    		{ 
    			if (sheshs[h] < sheshs[min]) 
    			{ 
    				min = h;  
    			}
    		}	
    		if (i != min) 
    		{ 
    			int temp = sheshs[i];
    			sheshs[i] = sheshs[min];
    			sheshs[min] = temp; 
    			selectionsortexecutioncount++; 
    		}
    	}		
    } 
    public static void deletionWithoutSkip(ArrayList<Integer> objects) 
    { 
    	for(int i = 0; i < objects.size(); i++) 
    	{
    		if(objects.get(i) == 112) 
    		{ 
    			objects.remove(i); 
    			i--;
    		}
    	} 
    } 
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setResizable(false);
		config.setTitle("BeatType");
		config.setWindowedMode(W_WIDTH, W_HEIGHT);

		new Lwjgl3Application(new BeatType(), config);
		
		Calculator happyTypeBeat = new Calculator(37410, "happyTypeBeat");
		Calculator sadTypeBeat = new Calculator(4701254, "sadTypeBeat"); 
		Calculator calmTypeBeat = new Calculator(5718902, "calmTypeBeat"); 
		Calculator quickTypeBeat = new Calculator(999777666, "quickTypeBeat");  
		bruh.add(happyTypeBeat.toString()); 
		bruh.add(sadTypeBeat.toString()); 
		bruh.add(0, calmTypeBeat.toString()); 
		greatmusic[0][0] = happyTypeBeat; 
		greatmusic[0][1] = sadTypeBeat; 
		greatmusic[1][0] = calmTypeBeat; 
		greatmusic[1][1] = quickTypeBeat; 
		System.out.println(Arrays.toString(greatmusic)); 
		System.out.println(Arrays.toString(goodmusic));  
		System.out.println(bruh); 
		System.out.println(bruh.remove(0)); 
		intbruh.add(176); 
		System.out.println(intbruh.add(214));
		intbruh.add(112); 
		intbruh.add(112); 
		intbruh.add(1, 54); 
		intbruh.add(38); 
		intbruh.add(4);  
		intbruh.add(4);
		System.out.println(intbruh); 
		System.out.println(intbruh.size()); 
		 
		System.out.println(searchIntBruh(4)); 
		System.out.println(searchIntBruh(69)); 
		selectionSort(sorting); 
		insertionSort(sorting2); 
		System.out.println(Arrays.toString(sorting));
		System.out.println(Arrays.toString(sorting2)); 
		System.out.println(selectionsortexecutioncount);
		System.out.println(insertionsortexecutioncount); 
		deletionWithoutSkip(intbruh);
		System.out.println(intbruh);
		System.out.println(intbruh.get(3));

		rowSearch(goodmusic);
		colSearch(goodmusic);

		for (int[] arr: goodmusic)
			for (int i: arr)
				System.out.println(i);
	}
}
