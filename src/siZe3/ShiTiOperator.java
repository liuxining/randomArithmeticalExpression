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
		a.setTiMu("1 + 6 - 2");
		a.setCountNumber(3);
//		ErChaShu T = getTree(a.getCalculateOrder().split(","), a.getAnswer(), a.getCountNumber() - 1);
//		System.out.println(T.getData());
		ShiTi b = new ShiTi();
		b.setTiMu("6 + 1 - 2");
		b.setCountNumber(3);
		System.out.println(calculateOrderSame(a, b));
	//	System.out.println(calculateOrderSame2(a, b));
//		ErChaShu T = getTree(2);
//		System.out.println(T.getData());
//		System.out.println(T.getLeftChild().getData());
//		System.out.println(T.getRightChild().getData());
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
		
		int s = a.getCountNumber() - 1;
		
		//构建逻辑顺序二叉树
		ErChaShu Ta = getTree(asplit, a.getAnswer(), s);
		ErChaShu Tb = getTree(bsplit, b.getAnswer(), s);
		
		boolean same = treeSame(Ta, Tb);
		return same;
	}
	
	public static ErChaShu getTree(String[] orderExpress,String data,int index)
	{
		ErChaShu T = null;
		T = new ErChaShu();
		T.setData(data);
		if(index == 0)
		{
			T.setLeftChild(null);
			T.setRightChild(null);
			return T;
		}
		//取a运算式该子表达式的两个运算数a1,a2,运算符af
		String a1 = orderExpress[index * 3 - 3];
		String af = orderExpress[index * 3 - 2];
		String a2 = orderExpress[index * 3 - 1];
		
		String[] a1s = a1.split("s");
		String[] a2s = a2.split("s");
		
		String n1 = a1s[0];
		String n2 = a2s[0];
		
		int n1s = Integer.parseInt(a1s[1]);
		int n2s = Integer.parseInt(a2s[1]);
		
		if( (af.equals("+") || af.equals("*"))  && compare(n1,n2) == '>' )
		{
			String ts = n1;
			n1 = n2;
			n2 = ts;
			
			int t = n1s;
			n1s = n2s;
			n2s = t;
		}
		T.setData(af);
		T.setLeftChild(getTree(orderExpress, n1, n1s));
		T.setRightChild(getTree(orderExpress, n2, n2s));

		return T;

	}
	
	
	
	
	//判断两个二叉树是否相同
	public static boolean treeSame(ErChaShu Ta,ErChaShu Tb)
	{
		if(Ta == null || Tb == null)
		{
			if(Ta == null && Tb == null)
			{
				return true;
			}
			return false;
		}
		if(Ta.getData().equals(Tb.getData()))
		{
			//两结点数值相同
			
			//递归判断Ta和Tb的左孩子，Ta和Tb的右孩子是否相同
			ErChaShu al = Ta.getLeftChild();
			ErChaShu ar = Ta.getRightChild();
			ErChaShu bl = Tb.getLeftChild();
			ErChaShu br = Tb.getRightChild();
			
			boolean ls;
			boolean rs;
			
			ls = treeSame(al, bl);
			rs = treeSame(ar, br);
			if(ls == true && rs == true)
			{
				return true;
			}
			else if(Ta.getData().equals("+") || Ta.getData().equals("*"))
			{
				ls = treeSame(al, br);
				rs = treeSame(ar, bl);
				if(ls == true && rs == true)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	//比较a和b，如果a大于b则返回'>'
	public static char compare(String a,String b)
	{
		int a_index = a.indexOf("/");
		int b_index = b.indexOf("/");
		if(a_index == -1 && b_index == -1)
		{
			int az = Integer.parseInt(a);
			int bz = Integer.parseInt(b);
			if(az > bz)
			{
				return '>';
			}
			else if(az == bz)
			{
				return '=';
			}
			else
			{
				return '<';
			}
		}
		else
		{
			FenShu af = new FenShu(a);
			FenShu bf = new FenShu(b);
			return af.compare(bf);
		}
	}
	
	

	

}
