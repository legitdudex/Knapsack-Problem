import java.util.*;
import java.io.*;
import java.lang.*;



class knapsackme{
	private ArrayList<Integer> Weights;
	private ArrayList<Integer> Values;
	private int bagCapacity;
	
	public knapsackme(int bagCapacity){
		this.Weights = new ArrayList<Integer>();
		this.Values = new ArrayList<Integer>();
		this.bagCapacity = bagCapacity;
	}

	public void addToObjects(int weight, int value){
		this.Weights.add(weight);
		this.Values.add(value);
	}

	
	public static double[] getRatios(knapsackme myKnapsack){
		double[] ratios = new double[myKnapsack.Weights.size()];
		for(int i = 0; i < myKnapsack.Weights.size(); i++){
			ratios[i] = (myKnapsack.Values.get(i) * 1.0) / (myKnapsack.Weights.get(i) * 1.0);
		}
		return ratios;
	}

	public static double[][] getThreeTuple(knapsackme myKnapsack, double[] ratios){
		double[][] returnArray = new double[myKnapsack.Weights.size()][4];
		for(int i = 0; i < myKnapsack.Weights.size(); i++){
			double[] myThreeTuple = new double[]{ratios[i], myKnapsack.Weights.get(i) * 1.0, myKnapsack.Values.get(i) * 1.0, i * 1.0}; 
			returnArray[i] = myThreeTuple;
		}
		return returnArray;
	}

	public static void main(String[] args){
		if(args.length != 3){
			System.out.println("Require filenames for weight, value, and capacity separated by spaces\n");
		}
		try{
			BufferedReader brWeights = new BufferedReader(new FileReader(new File(args[0])));
			BufferedReader brValues = new BufferedReader(new FileReader(new File(args[1]))); 
			BufferedReader brCapacity = new BufferedReader(new FileReader(new File(args[2])));

			knapsackme myKnapsack = new knapsackme(Integer.parseInt(brCapacity.readLine().replaceAll("\\s+", "")));
			int weight;
			String weightString;
			int value = Integer.parseInt(brValues.readLine().replaceAll("\\s+", ""));
			while((weightString = brWeights.readLine()) != null){
				weight = Integer.parseInt(weightString.replaceAll("\\s+", ""));
				myKnapsack.addToObjects(weight, value);
				String valueString = brValues.readLine();
				if(valueString != null){
					value = Integer.parseInt(valueString.replaceAll("\\s+", ""));
				}
			}
			double[] ratios = getRatios(myKnapsack);
			double[][] myArrayOfThreeTuples = getThreeTuple(myKnapsack, ratios);
			Arrays.sort(myArrayOfThreeTuples, new Comparator<double[]>(){
				public int compare(double[] p1, double[] p2){
					return Double.valueOf(p1[0]).compareTo(Double.valueOf(p2[0]));
				}
			});
			
			System.out.println("Here we have the ratios of our knapsack in sorted order. We can identify their initial indexes by the fourth element of each array"); 
			for(int i = 0; i < myArrayOfThreeTuples.length; i++){
				System.out.printf("ratio: %f, weight: %f, value: %f, index: %f\n", myArrayOfThreeTuples[i][0], myArrayOfThreeTuples[i][1], myArrayOfThreeTuples[i][2], myArrayOfThreeTuples[i][3]);
			}

			//now we can start adding to our knapsack
			boolean continueLoop = true;
			int[] returnArray = new int[myKnapsack.Weights.size()];
			for(int i = 0; i < myKnapsack.Weights.size(); i++){
				returnArray[i] = 0;
			}
			int startingIndex = myKnapsack.Weights.size() - 1;
			double bestValue = 0, capacityTaken = 0;
			while(continueLoop){
				if(startingIndex < 0){ break; } 
				else if(myArrayOfThreeTuples[startingIndex][1] > myKnapsack.bagCapacity){
					startingIndex --;
				}
				else{
					myKnapsack.bagCapacity -= myArrayOfThreeTuples[startingIndex][1];
					returnArray[(int) myArrayOfThreeTuples[startingIndex][3]] = 1;
					bestValue += myArrayOfThreeTuples[startingIndex][2];
					capacityTaken += myArrayOfThreeTuples[startingIndex][1];
					startingIndex --;	
				}
			}
			
			System.out.println("");
			System.out.println("Solution to the knapsack problem with inputs: ");
			System.out.printf("Most Efficient Summed Value of Items: %f, Most Efficient Capacity Taken: %f\n", bestValue, capacityTaken);
			System.out.printf("Bag Capacity Remaining: %d\n", myKnapsack.bagCapacity);
			System.out.println("List representing the bag elements we should take: 1 = take, 0 = don't take\n");
			
			for(int i = 0; i < myKnapsack.Weights.size(); i++){
				System.out.printf("Bag Element %d: %d\n", i + 1, returnArray[i]);
			}
		} catch(FileNotFoundException e){ e.printStackTrace();} catch(IOException e){ e.printStackTrace(); }
		
	}
}
