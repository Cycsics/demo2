package team.second.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import team.second.service.*;



public class CosineSimilarity implements AlgorithmService {
	Map<Double,int[]> vectorMap = new HashMap<Double,int[]>();
	
	int[] tempArray = null;
	
	public CosineSimilarity(double[] data1,double[] data2) {
		vectorMap.clear();
		for(double data : data1) {
			if(vectorMap.containsKey(data)) {
				vectorMap.get(data)[0]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 1;
				tempArray[1] = 0;
				vectorMap.put(data,tempArray);
			}
		}
		
		for(double data : data2) {
			if(vectorMap.containsKey(data)) {
				vectorMap.get(data)[1]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 0;
				tempArray[1] = 1;
				vectorMap.put(data,tempArray);
			}
		}
	}
	
	
	
	@Override
	public double sim() {
		double result = 0;
		result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
		return result;
	}
	
	@Override
	public double  sqrtMulti(Map<Double,int[]>paramMap) {
		double result = 0;  
        result = squares(paramMap);  
        result = Math.sqrt(result);  
        return result;  
	}
		
	@Override
	public double pointMulti(Map<Double, int[]>paramMap) {
		double result = 0;
		Set<Double>keySet = paramMap.keySet();
		for(double db : keySet) {
			int temp[] = paramMap.get(db);
			result += (temp[0] * temp[1]);
		}
		return result;
	}
	
	@Override
	public double squares(Map<Double,int[]>paramMap) {
		double result1 = 0;
		double result2 = 0;
		Set<Double>keySet = paramMap.keySet();
		for(double db : keySet) {
			int temp[] = paramMap.get(db);
			result1 += (temp[0]*temp[0]);
			result2 += (temp[1]*temp[1]);
		}
		return result1*result2;
	}
	public static void main(String[] args) {
		Constant constant = new Constant();
		//double[][] dataBase = constant.getDataBase();
		double[] data1 = {1.78,1.53,1.72,1.47,3.15,1.66,3.02,2.77,0.0,0.0,0.0,0.0,0.0,4.8,0.0,0.0,2.15,1.9,1.98,1.04,1.3,1.87,0.96,2.54,2.29,0.0,2.81,2.56,2.58,0.0,1.14,0.99,0.99,1.36,2.21,0.0};
		double[] data2 = {1.78,1.53,1.72,1.47,3.15,1.43,1.89,1.64,3.69,0.0,0.0,0.0,0.0,4.8,0.0,0.0,2.15,1.9,1.98,1.04,1.3,1.87,0.96,2.54,2.29,0.0,2.81,2.56,2.58,0.0,1.14,0.99,0.99,1.36,2.21,0.0};
		CosineSimilarity alogorithm = new CosineSimilarity(data1,data2);
		System.out.println(alogorithm.sim());
	}


	
}
