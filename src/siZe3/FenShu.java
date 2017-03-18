package siZe3;

public class FenShu {
	private int denominator, numerator;
	private boolean chengLi;

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public boolean isChengLi() {
		return chengLi;
	}

	public void setChengLi(boolean chengLi) {
		this.chengLi = chengLi;
	}

	public FenShu(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		if (denominator == 0) {
			this.chengLi = false;
		} else {

			this.chengLi = true;
			yueJian();
		}
	}

	// 根据字符串构造分数
	public FenShu(String str) {
		if(str == null)
		{
			this.chengLi = false;
		}
		int index = str.indexOf("/");
		if (index == -1) {
			this.numerator = Integer.parseInt(str);
			this.denominator = 1;
			this.chengLi = true;

		} else {
			this.denominator = Integer.parseInt(str.substring(index + 1));
			if (this.denominator == 0) {
				chengLi = false;
			} else {
				chengLi = true;
				int zhengShu = str.indexOf("'");
				if (zhengShu == -1) {
					// 没有整数部分
					this.numerator = Integer.parseInt(str.substring(0, index));
				} else {
					// 有整数部分
					this.numerator = Integer.parseInt(str.substring(0, zhengShu)) * this.denominator
							+ Integer.parseInt(str.substring(zhengShu + 1, index));
				}
				yueJian();
			}
		}
	}

	public FenShu() {
	}

	// 约简
	private void yueJian() {
		int y = 1;
		for (int i = numerator; i > 1; i--) {
			if (numerator % i == 0 && denominator % i == 0) {
				y = i;
				break;
			}
		}
		// int nc = numerator,dc = denominator;
		// if(nc != 0){
		// while(nc != dc - nc){
		// y = dc - nc;
		// if(nc > y){
		// dc = nc;
		// nc = y;
		// }else{
		// dc = y;
		// }
		// }
		// y = nc;
		//
		numerator /= y;
		denominator /= y;

	}

	// 加
	public FenShu add(FenShu b) {

		FenShu c = null;
		if (this.chengLi && b.isChengLi()) {
			int nNumerator = this.numerator * b.getDenominator() + this.denominator * b.getNumerator();
			int nDenominator = this.denominator * b.getDenominator();
			c = new FenShu(nNumerator, nDenominator);
		} else {
			c = new FenShu();
			c.setChengLi(false);
		}
		return c;
	}

	// 减
	public FenShu subtract(FenShu b) {
		FenShu c = null;
		if (this.chengLi && b.isChengLi()) {
			int nNumerator = this.numerator * b.getDenominator() - this.denominator * b.getNumerator();
			int nDenominator = this.denominator * b.getDenominator();
			c = new FenShu(nNumerator, nDenominator);
		} else {
			c = new FenShu();
			c.setChengLi(false);
		}
		return c;
	}

	// 乘
	public FenShu multiply(FenShu b) {
		FenShu c = null;
		if (this.chengLi && b.isChengLi()) {

			int nNumerator = this.numerator * b.getNumerator();
			int nDenominator = this.denominator * b.getDenominator();
			c = new FenShu(nNumerator, nDenominator);
		} else {
			c = new FenShu();
			c.setChengLi(false);
		}
		return c;
	}

	// 除
	public FenShu divide(FenShu b) {
		FenShu c = null;
		if (this.chengLi && b.isChengLi() && (b.getNumerator() != 0)) {

			int nNumerator = this.numerator * b.getDenominator();
			int nDenominator = this.denominator * b.getNumerator();
			c = new FenShu(nNumerator, nDenominator);
		} else {
			c = new FenShu();
			c.setChengLi(false);
		}
		return c;
	}

	// 输出分数形式
	public String toString() {
		if (this.chengLi) {
			if (numerator != 0) {
				if (numerator % denominator == 0)
					return "" + numerator / denominator;
				else if (numerator > denominator) {
					return (numerator / denominator) + "'" + (numerator % denominator) + "/" + denominator;
				}
				return numerator + "/" + denominator;
			}
			return "0";
		}
		return "ERROR";
	}
	
	//判断该分数与b分数的大小关系，
	public char compare(FenShu b)
	{
		if(this.chengLi && b.isChengLi())
		{
			int c = subtract(b).getNumerator();
			if(c > 0)
			{
				return '>';
			}
			else if(c == 0)
			{
				return '=';
			}
			else
			{
				return '<';
			}
		}
		return 'E';
	}

}
