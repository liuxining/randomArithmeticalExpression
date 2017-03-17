package siZe3;

public class ErChaShu {
	private ErChaShu leftChild;
	private ErChaShu rightChild;
	private String data;

	public ErChaShu getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(ErChaShu leftChild) {
		this.leftChild = leftChild;
	}

	public ErChaShu getRightChild() {
		return rightChild;
	}

	public void setRightChild(ErChaShu rightChild) {
		this.rightChild = rightChild;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ErChaShu(){}
}
