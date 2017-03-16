package siZe3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
 * 随机生成四则运算式3，2017年03月13日20:20:18
 * 
 * 
 */
import java.util.Scanner;

public class SiZe3 {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		menu();

		scan.close();
	}

	public static void menu() {
		System.out.println("1、判断文件中的题目及统计；2、随机生成题目");
		int p = scan.nextInt();
		if (p == 1) {
			System.out.println("请输入题目文件路径：");
			String tiMuFileName = scan.next();
			System.out.println("请输入答案文件路径：");
			String answerFileName = scan.next();
			try {
				panDuanTiMuFromFile(new File(tiMuFileName), new File(answerFileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("0、整数式   1、分数式");
			int type = scan.nextInt();
			System.out.println("生成的运算式个数：");
			int n = scan.nextInt();
			System.out.println("是否有括号(1有,0没有)");
			int hasKuoHao = scan.nextInt();
			System.out.println("数值范围(最大数)");
			int maxNum = scan.nextInt();
			Date beginDate = new Date();
			List<ShiTi> list = createYunSuanShi(hasKuoHao, maxNum, n, type);
			//List<ShiTiBean> list = createYunSuanShi(1, 20, 1000, 0);
			//将该list保存到数据库
			ShiTiDAO st = new ShiTiDAO();
			try {
				st.insert(list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date endDate = new Date();
			for (int i = 0; i < list.size(); i++) {
				ShiTi s = list.get(i);
				System.out.println((i + 1) + " : " + s.getTiMu() + " = " + s.getAnswer());// + " 运算顺序："
						//+ s.getCalculateOrder() + " 运算数个数：" + s.getCountNumber());
			}
			System.out.println("耗时：" + (endDate.getTime() - beginDate.getTime()) + "ms");
			
		}
	}

	//从文件中读取的题目和答案，判断答案是否正确，并将统计结果输出到文件Grade.txt文件
	public static void panDuanTiMuFromFile(File tiMu, File answer) throws IOException {
		BufferedReader tiMuBR = new BufferedReader(new FileReader(tiMu));
		BufferedReader answerBR = new BufferedReader(new FileReader(answer));
		List<Integer> rightIndexList = new ArrayList<Integer>();
		List<Integer> wrongIndexList = new ArrayList<Integer>();
		int num = Integer.parseInt(tiMuBR.readLine());
		for (int i = 0; i < num; i++) {
			ShiTi stb = new ShiTi();
			String tiMuR = tiMuBR.readLine();
			String[] tiMuRsz = tiMuR.split(":");
			int index = Integer.parseInt(tiMuRsz[0]);
			stb.setTiMu(tiMuRsz[1]);
			String answerR = answerBR.readLine();
			if (stb.getAnswer().equals(answerR)) {
				// 文件中的答案正确
				// 将该题号添加到rightIndexList中
				rightIndexList.add(index);
			} else {
				// 答案不正确，将该题号添加到wrongIndexList中
				wrongIndexList.add(index);
			}
		}
		// 构造输出流对象，将结果信息输出到文件Grade.txt
		PrintWriter pw = new PrintWriter(new File("Grade.txt"));
		pw.print("Correct:" + rightIndexList.size() + "(");
		for (int i = 0; i < rightIndexList.size(); i++) {
			pw.print(rightIndexList.get(i));
			if (i != (rightIndexList.size() - 1)) {
				pw.print(",");
			} else {
				pw.println(")");
			}
		}

		pw.print("Wrong:" + wrongIndexList.size() + "(");
		for (int i = 0; i < wrongIndexList.size(); i++) {
			pw.print(wrongIndexList.get(i));
			if (i != (wrongIndexList.size() - 1)) {
				pw.print(",");
			} else {
				pw.println(")");
			}
		}
		System.out.println("已将统计结果输出到文件Grade.txt");
		// 释放资源
		pw.close();
		answerBR.close();
		tiMuBR.close();
	}

	// 生成整数计算式添加限制条件,type为运算式类型 0代表整数式，1代表真分数式
	public static List<ShiTi> createYunSuanShi(int hasKuoHao, int maxNum, int n, int type) {
		int i = 0;
		List<ShiTi> list = new ArrayList<ShiTi>();
		ShiTi stb = null;
		// ShiTiDAO std = new ShiTiDAO();
		while (i < n) {
			stb = ShiTiOperator.getExpress(maxNum, hasKuoHao, type);

			// 检验重复
			boolean chongFu = false;
			for (int j = 0; j < i; j++) {
				ShiTi t = list.get(j);
				if (ShiTiOperator.calculateOrderSame(stb, t)) {
					chongFu = true;
					System.out.println("出现重复：计算式一：" + t.getTiMu() + " = " + t.getAnswer() + " 运算顺序："
							+ t.getCalculateOrder() + " 运算数个数：" + t.getCountNumber());
					System.out.println("出现重复：计算式二：" + stb.getTiMu() + " = " + stb.getAnswer() + " 运算顺序："
							+ stb.getCalculateOrder() + " 运算数个数：" + stb.getCountNumber());
					System.out.println("\n\n");
					break;
				}
			}
			if (chongFu == false) {
				list.add(stb);
				i++;
			}
		}
		return list;
	}

}
