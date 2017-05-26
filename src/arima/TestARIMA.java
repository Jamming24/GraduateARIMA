package arima;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TestARIMA {

	/**
	 * @param args
	 */
	@SuppressWarnings("finally")
	public static void main(String[] args) {
		// TODO Auto-generated method stub 12特征
		
		TestARIMA t=new TestARIMA();
		ArrayList<String[]> arraylist=t.getFeatureData();
		ArrayList<Integer> PredictValue=new ArrayList<Integer>();
		ArrayList<Integer> AnswerValue=new ArrayList<Integer>();
		ArrayList<Integer> lastValue=new ArrayList<Integer>();
		ArrayList<Double> ErrorRate;
		int value=-1;
		double[] FeatureData=new double[12];
		int x=0; 
		for(int i=0;i<arraylist.size()-1;i++){
			String[] temp=arraylist.get(i);
			for(int j=1;j<=temp.length-1;j++){
				FeatureData[j-1]=Double.parseDouble(temp[j]); 
			}
			try {
//				for(int ii=0;ii<temp.length;ii++){
//					System.out.print(temp[ii]+" ");
//				}
				value=Integer.parseInt(temp[0]);
				x++;
//				System.out.println(x+">>>>>>>>>");
				PredictValue.add(t.getPredictValue(FeatureData));
				AnswerValue.add(value);
				lastValue.add(Integer.parseInt(temp[temp.length-1]));
				
			} catch (Exception e) {
//				System.out.println("捕获异常");
			}finally{
				continue;
			}
		}
		
		
		ErrorRate=t.UpAndDown(PredictValue, AnswerValue, lastValue);
		System.out.println("PredictValue:"+PredictValue.size());
//		System.out.println("AnswerValue:"+AnswerValue.size());
		double count=0;
		for(int index=0;index<PredictValue.size();index++){
			if(PredictValue.get(index).equals(AnswerValue.get(index))){
				count++;
				System.out.print(PredictValue.get(index)+" ");
			}
		}
		System.out.println("匹配数量:"+count);
		System.out.println(count/PredictValue.size()*100+"%");
		for(double b:ErrorRate){
			System.out.println(b);
		}
	
		System.out.println("ErrorRatesize："+ErrorRate.size());

	}
	
	public int getPredictValue(double[] FeatureData){
		ARIMA arima=new ARIMA(FeatureData);
		int []model=arima.getARIMAmodel();
//		System.out.println("Best model is [p,q]="+"["+model[0]+" "+model[1]+"]");
		
		int PredictValue=arima.aftDeal(arima.predictValue(model[0],model[1]));
//		System.out.println("Predict value="+PredictValue);
		return PredictValue;
	}
	
	public ArrayList<String[]> getFeatureData(){
		String Filetest="D:\\GraduationDesign\\6hour\\test_ConverToValue.txt";
		String Filetrain="D:\\GraduationDesign\\8hour\\train_ConverToValue.txt";
		ArrayList<String[]> arraylist=new ArrayList<String[]>();
		String[] temp=new String[12];
		try {
			Scanner scTest=new Scanner(new FileReader(Filetest));
			Scanner scTrain=new Scanner(new FileReader(Filetrain));
			while(scTest.hasNext()){
				temp=scTest.nextLine().split(" ");
				if(!isAllzero(temp)){
					arraylist.add(temp);
				}else
					continue;
			}
			while(scTrain.hasNext()){
				temp=scTrain.nextLine().split(" ");
				if(!isAllzero(temp)){
					arraylist.add(temp);
				}else
					continue;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("累计特征组数量:"+arraylist.size());
	
		
		return arraylist;
	}
	
	public ArrayList<Double> UpAndDown(ArrayList<Integer> PredictValue,ArrayList<Integer> AnswerValue,ArrayList<Integer> lastValue){
		double correctUpAndDown=0;
		ArrayList<Double> ErrorRate=new ArrayList<Double>();
		double error=0.0;
		
		for(int i=0;i<AnswerValue.size()-1;i++){
			if((PredictValue.get(i)-lastValue.get(i))>0&&(AnswerValue.get(i)-lastValue.get(i))>0){
				correctUpAndDown++;
			}else if((PredictValue.get(i)-lastValue.get(i))==0&&(AnswerValue.get(i)-lastValue.get(i))==0){
				correctUpAndDown++;
			}else if((PredictValue.get(i)-lastValue.get(i))<0&&(AnswerValue.get(i)-lastValue.get(i))<0){
				correctUpAndDown++;
			}
//			System.out.println("PredictValue:"+PredictValue.get(i));
			error=((double)PredictValue.get(i)-AnswerValue.get(i))/PredictValue.get(i);
			if((PredictValue.get(i)-AnswerValue.get(i))!=0){
				ErrorRate.add(error);
			}
		}
		
		System.out.println("准确率："+correctUpAndDown/AnswerValue.size()*100+"%");
		System.out.println("趋势准确率数量："+correctUpAndDown); 
		return ErrorRate;
	}
	
	public boolean isAllzero(String[] featurns){
		boolean flag=true;
//		System.out.println(featurns.length);
		for(int i=1;i<featurns.length;i++){
			if(!featurns[i].equals("0")){
				flag=false;
				break;
			}
		}
		return flag;
	}

}
