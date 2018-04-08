package team.second.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import team.second.service.*;



public class CosineSimilarity implements AlgorithmService {
	Map<Double,int[]> vectorMap = new HashMap<Double,int[]>();
	
	public CosineSimilarity() {}
	
	/*
	 * @param: double[][]
	 * @return double[][]
	 * 
	 * **/
	@Override
	public double[][] cosineSimilarity(double[][] data) {
		double[][] result = null;
		double[][] dataTemp = null;
		dataTemp = trans(data);
		for(int i = 0; i < dataTemp.length; i++) {
			for(int j = 0; j < dataTemp.length; j++) {
				 dataClear(dataTemp[i],dataTemp[j]);
				 result[i][j] = pointMulti(vectorMap) / sqrtMulti(vectorMap);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param data
	 * @return result
	 * 将存储在二维数组的数据行列变换
	 */
	public double[][] trans(double[][] data) {
		double[][] result = null;
		for(int i = 0; i < data[i].length; i++) {
			for(int j = 0; j < data.length; j++) {
				result[i][j] = data[j][i];
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param data1
	 * @param data2
	 * 进行数据清洗，给相似度运算做前期准备工作
	 */
	public void dataClear(double[] data1,double[] data2) {
		int[] tempArray = null;
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
	
	/**
	 * 平方开方
	 * @param paramMap
	 * @return
	 */
	public double  sqrtMulti(Map<Double,int[]>paramMap) {
		double result = 0;  
        result = squares(paramMap);  
        result = Math.sqrt(result);  
        return result;  
	}
		
	/***
	 * 点乘累加
	 * @param paramMap
	 * @return
	 */
	public double pointMulti(Map<Double, int[]>paramMap) {
		double result = 0;
		Set<Double>keySet = paramMap.keySet();
		for(double db : keySet) {
			int temp[] = paramMap.get(db);
			result += (temp[0] * temp[1]);
		}
		return result;
	}
	
	/***
	 * 平方累加
	 * @param paramMap
	 * @return
	 */
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
}
