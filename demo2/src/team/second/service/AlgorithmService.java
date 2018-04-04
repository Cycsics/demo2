package team.second.service;

import java.util.Map;

public interface AlgorithmService {
	
	/**求余弦相似度**/
	double sim();
	 
	double sqrtMulti(Map<Double,int[]>paramMap);
	
	 /**求点乘*/
	double pointMulti(Map<Double, int[]>paramMap);
	 
	 /**求平方和*/
	double squares(Map<Double,int[]>paramMap);
	
	 
}
