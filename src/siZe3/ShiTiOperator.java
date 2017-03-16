package siZe3;

import java.util.Random;

//试题类，
/*
 * 方法有：生成一个试题，计算试题答案，
 * 
 * 
 */
public class ShiTiOperator {
	// 获取一个运算式
	public static void main(String[] args) {
		ShiTi a = new ShiTi();
		a.setTiMu("(1 + 2) + (3 + 4)");
		a.setCountNumber(4);
		ShiTi b = new ShiTi();
		b.setTiMu("(1 + 2) + (4 + 3)");
		b.setCountNumber(4);
		System.out.println(calculateOrderSame(a, b));
	//	System.out.println(calculateOrderSame2(a, b));
	}
	
	
	public static ShiTi getExpress(int maxNum, int hasKuoHao, int type) {

		ShiTi stb = new ShiTi();

		Random rd = new Random();
		char[] fuHao = { '+', '-', '*', '/' };
		while (true) {
			int[] bracket = null;// 存储括号位置
			int expressLength = rd.nextInt(3) + 2;// 随机生成一个2~4之间的整数作为该运算式的运算数的个数
			stb.setCountNumber(expressLength);
			String[] number = new String[expressLength];// 存储运算数的数组
			String[] symbol = new String[expressLength - 1];// 存储运算符的数组

			String express = "";
			number[0] = getOperatorNumber(type, maxNum);
			for (int i = 0; i < expressLength - 1; i++) {
				symbol[i] = fuHao[rd.nextInt(4)] + "";// 生成运算符
				number[i + 1] = getOperatorNumber(type, maxNum);
			}

			if (hasKuoHao == 1) {
				// 需要加括号
				bracket = randomAddBracket(expressLength);
			}

			// 构建表达式
			for (int i = 0; i < expressLength; i++) {
				// 添加左括号
				if (hasKuoHao == 1) {
					for (int j = 0; j < bracket[i]; j++) {
						express += "(";
					}
				}

				express += number[i];// 加上运算数

				// 添加右括号
				if (hasKuoHao == 1) {
					for (int j = 0; j > bracket[i]; j--) {
						express += ")";
					}
				}

				if (i != expressLength - 1) {
					express += " " + symbol[i] + " ";// 加运算符，并在两侧加空格来与运算数分隔
				}

			}
			stb.setTiMu(express);
			if (!(stb.getAnswer().equals("ERROR"))) {
				// System.out.println("生成的运算式为：" + express + "=" + result[0]);
				return stb;
			}
		}

	}

	// 随机生成括号，参数为运算式的运算数的个数
	private static int[] randomAddBracket(int length) {
		int[] brackets = new int[length];
		for (int i = 0; i < brackets.length; i++)
			brackets[i] = 0;
		Random rd = new Random();
		for (int i = 2; i < length; i++) {// 添加的括号长度（括号包围的运算数的个数）
			for (int j = 0; j < length - i + 1; j++) {
				int t = rd.nextInt(2);// 随机生成0或1，0代表不加括号，1代表加括号
				if (t == 1) {
					if (brackets[j] >= 0 && brackets[j + i - 1] <= 0) {// 要加的括号的第一个运算数周围没有右括号，且
																		// 最后一个运算数周围没有左括号
						int counteract = 0;
						for (int k = j; k < j + i; k++) {// 将要加的括号之间的所有运算数对应的brackets相加，
															// 如果和为0说明这个括号之间的括号是匹配的，不会出现括号交叉现象
							counteract += brackets[k];
						}
						if (counteract == 0) {
							brackets[j]++;
							brackets[j + i - 1]--;
						}
					}
				}
			}
		}
		return brackets;
	}

	// 随机生成一个运算数（ type==0代表生成整数，type==1代表生成真分数，maxNum代表数值范围 0~(maxNum-1) )
	private static String getOperatorNumber(int type, int maxNum) {
		Random rd = new Random();
		int a;
		while (true) {
			a = rd.nextInt(maxNum);
			if (type == 0) {// 随机生成一个整数
				return "" + a;
			} else {// 随机生成一个真分数
				if (a == 0) {
					continue;
				}
				int b = rd.nextInt(a);
				FenShu c = new FenShu(b, a);
				return c.toString();
			}
		}
	}
	

	/*
	 * 
	 * 
	 * 　先比较两个运算式的运算数的个数，如果运算数个数不相同则运算式肯定不同，否则继续判断

　　　　获取两个试题的逻辑运算顺序字符串，并用‘，’对该字符串分割为一个字符串数组

　　　　每4个元素为一组子表达式（运算数1，运算符，运算数2），可知应该有（运算数个数 - 1）组子表达式

　　　　利用循环对每一组表达式进行验证，验证运算符和两个运算数是否相同，如果运算符是加或乘，则可以进行翻转比较

　　　　（即将其中一个运算式的运算数交换与另一个运算式的运算数对应比较）
　　　　只要其中出现不符合相同条件的则返回FALSE，如果相同判断都成立则返回TRUE

	 */
	
	public static boolean calculateOrderSame(ShiTi a, ShiTi b) {

	
		//比较两个运算式的运算数个数
		if(a.getCountNumber() != b.getCountNumber())
		{
			return false;
		}
		
		// 取出运算式的运算顺序字符串，
		String aorder = a.getCalculateOrder();
		String border = b.getCalculateOrder();

		// 将a,b运算式的运算顺序字符串进行分割，按序取出每一个运算数和运算符
		String[] asplit = aorder.split(",");
		String[] bsplit = border.split(",");

		int n = a.getCountNumber() - 1;//共有n组子表达式
		
		for(int i = 0;i < n;i++)
		{
			//取a运算式该子表达式的两个运算数a1,a2,运算符af
			String a1 = asplit[0 + i * 3];
			String af = asplit[1 + i * 3];
			String a2 = asplit[2 + i * 3];
			//取b运算式该子表达式的两个运算数b1,b2,运算符bf
			String b1 = bsplit[0 + i * 3];
			String bf = bsplit[1 + i * 3];
			String b2 = bsplit[2 + i * 3];
			
			if(af.equals(bf))
			{//运算符相同
				if(  a1.equals(b1)  &&  a2.equals(b2)  )
				{//对应的运算数相同
					continue;//这一个表达式相同，验证下一个子表达式
				}
				else if(  (   af.equals("+")   ||    af.equals("*")   )    )
				{//运算符为加或乘 
					if(a1.equals(b2)   &&   a2.equals(b1))
					{//两子表达式翻转后相同
						continue;//这一个表达式相同，验证下一个子表达式
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
			
		}
		return true;
	}

}
