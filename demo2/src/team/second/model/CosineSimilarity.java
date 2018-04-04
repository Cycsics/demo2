package team.second.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import team.second.service.*;

/**
 * 
 * @author 12771
 * 传入二维数组，余弦相似度算法计算结果以二维数组返回
 */

public class CosineSimilarity implements AlgorithmService {
	Map<Double,int[]> vectorMap = new HashMap<Double,int[]>();
		
	public CosineSimilarity() {}
	
	/**求余弦相似度值**/
	@Override
	public double[][] ConSimilar(double[][] data) {
		double result = 0;
		double[][] ans = null;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data.length; j++) {
				DataClean(data[i],data[j]);
				result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
				ans[i][j] = result; 
			}
		}
		return ans;
	}
	
	/**数据清洗*/
	public void DataClean(double[] data1, double[] data2) {
		vectorMap.clear();
		int[] tempArray = null;
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
	
	/**分母值   求开方和*/
	public double  sqrtMulti(Map<Double, int[]>paramMap) {
		double result = 0;  
        result = squares(paramMap);  
        result = Math.sqrt(result);  
        return result;  
	}
		
	 /**分子值  求点乘*/
	public double pointMulti(Map<Double, int[]>paramMap) {
		double result = 0;
		Set<Double>keySet = paramMap.keySet();
		for(double db : keySet) {
			int temp[] = paramMap.get(db);
			result += (temp[0] * temp[1]);
		}
		return result;
	}
	
	/**求平方和*/
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
