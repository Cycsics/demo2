package team.second.main;

import team.second.model.CosineSimilarity;
import team.second.model.ReadData;
import team.second.model.Constant;
/**
 * 
 * @author 12771
 *
 */
public class Main {

	public static void main(String[] args) {
		CosineSimilarity cosineSimilarity = new  CosineSimilarity(); 
		Constant constant = new Constant();
		ReadData readdata = new ReadData();
   
		double[][]  dataBase = readdata.process("C:\\Users\\12771\\Desktop\\暂存\\lz01.xls");
		
	
		constant.setDataBase(dataBase);
		double[][] data = constant.getDataBase();
		
		for(int i = 0; i < data.length; i++) {
	    	for(int j = 0; j < data[i].length; j++) {
	    		System.out.print(data[i][j] +",");
	    	}
	    	System.out.println();
	    }
		
	}

}
