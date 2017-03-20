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
		a.setTiMu("4 + 2 / 3 - 1");//设置题目a
		System.out.println("答案为：" + a.getAnswer());
//		ShiTi b = new ShiTi();
//		b.setTiMu("2 + 1 + 4 + 3");//设置题目b
//		System.out.println("是否重复判断结果为：" + calculateOrderSame(a, b));
		
	}
	
	
	public static ShiTi getExpress(int maxNum, int hasKuoHao, int type) throws MyException {

		if(maxNum <= 0)
		{
			throw new MyException("最大数值应为正数");
		}
		
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
	private static int[] randomAddBracket(int length) throws MyException {
		if(length <= 1)
		{
			throw new MyException("运算式长度不能小于2");
		}
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
						int counteract1 = 0,counteract2 = 0,counteract3 = 0;
						for (int k = j; k < j + i; k++) {// 将要加的括号之间的所有运算数对应的brackets相加，
															// 如果和为0说明这个括号之间的括号是匹配的，不会出现括号交叉现象
							counteract1 += brackets[k];
						}
						for (int k = 0; k < j - 1; k++) {// 将要加的括号之前的所有运算数对应的brackets相加，
							// 如果和为0说明这个括号之间的括号是匹配的，不会出现括号交叉现象
							counteract2 += brackets[k];
						}
						for (int k = j + i; k < length; k++) {// 将要加的括号之后的所有运算数对应的brackets相加，
							// 如果和为0说明这个括号之间的括号是匹配的，不会出现括号交叉现象
							counteract3 += brackets[k];
						}
						
						if (counteract1 == 0 && counteract2 == 0 && counteract3 == 0) {
							brackets[j]++;
							brackets[j + i - 1]--;
							j += i;
						}
					}
				}
			}
		}
		return brackets;
	}

	// 随机生成一个运算数（ type==0代表生成整数，type==1代表生成真分数，maxNum代表数值范围 0~(maxNum-1) )
	private static String getOperatorNumber(int type, int maxNum) throws MyException {
		if(maxNum <= 0)
		{
			throw new MyException("最大数值应为正数");
		}
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
	
//--------------------------------------------------
	
	public static boolean calculateOrderSame(ShiTi a, ShiTi b) throws MyException {

		if(a == null || b == null)
		{
			throw new MyException("试题无效！");
		}
		//比较两个运算式的运算数个数
		if(a.getCountNumber() != b.getCountNumber())
		{
			return false;
		}
		
		//比较两运算式的答案是否相同
		if(!a.getAnswer().equals(b.getAnswer()))
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
			//取a运算式该子表达式的两个运算数a1,a2,运算符af,运算结果ar
			String a1 = asplit[0 + i * 3];
			String af = asplit[1 + i * 3];
			String a2 = asplit[2 + i * 3];
			//取b运算式该子表达式的两个运算数b1,b2,运算符bf,运算结果br
			String b1 = bsplit[0 + i * 3];
			String bf = bsplit[1 + i * 3];
			String b2 = bsplit[2 + i * 3];

			if(af.equals(bf))
			{
				//两子表达式符号相同
				if(a1.equals(b1) && a2.equals(b2))
				{
					continue;//该子表达式相同，继续判断下一个子表达式
				}
				else if(  (af.equals("+") || af.equals("*"))   &&   a1.equals(b2)  && a2.equals(b1)   )
				{
					continue;//该子表达式相同，继续判断下一个子表达式
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
	
	//--------------------------------------------------

	
	

	

}
