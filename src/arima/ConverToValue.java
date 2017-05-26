package arima;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ConverToValue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConverToValue ctb = new ConverToValue();
		String filePath = "D:\\GraduationDesign\\3hour\\train.txt";
		String OutFile = "D:\\GraduationDesign\\3hour\\train_ConverToValue.txt";
		int FeaturnNum=12;//所需预测特征数量
		ctb.ScannerResult(FeaturnNum,filePath, OutFile);
	}

	public void ScannerResult(int FeaturnNum,String filePath, String OutFile) {
		ArrayList<Integer> data = new ArrayList<Integer>();
		String query = null;
		boolean flag = true;
		try {
//			int InitNum=0;
//			int trainNum=0;
			int AnswerNum=0;
			String line = null;
			Scanner sc = new Scanner(new FileReader(filePath));
			while (sc.hasNext()) {
				line = sc.nextLine();
				if (line.length() < 12) {
					if (flag ==true) {
						query = line.substring(3, 8);
						flag = false;
					} else if (line.equals("EOF")){
						OutTrainText(FeaturnNum,OutFile, data);
						System.out.println(query + "数量:" + data.size());
						data.clear();
					} else {
						OutTrainText(FeaturnNum,OutFile, data);
						System.out.println(query + "数量:" + data.size());
						data.clear();
						query = line.substring(3, 8);
					}

				} else {
					AnswerNum=Integer.parseInt(line.split("：")[1]);
//					trainNum = converToboolean(InitNum,AnswerNum);
//					InitNum=AnswerNum;
//					data.add(trainNum);
//					如果需要转换成布尔型需要启用以上代码
					data.add(AnswerNum);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void OutTrainText(int FeaturnNum,String OutFile,ArrayList<Integer> data){
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(OutFile,true));
			int index=FeaturnNum-1;
			int local=0;
			for(int x=1;x<=data.size()-FeaturnNum;x++){
				pw.print(data.get(index+1)+" ");
				for(int i=local;i<=index;i++){
					pw.print(data.get(i)+" ");
				}
				pw.println();
				index++;
				local++;
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	private int converToboolean(int InitNum,int AnswerNum){
//		if(AnswerNum-InitNum>=0){
//			return 1;
////		}else if(AnswerNum-InitNum<0){
////			return -1;
//		}else
//			return -1;
//
//	}

}
