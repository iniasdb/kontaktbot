package botje.model;

public class Person {
	
	private String memberName;
	private int amount;
	
	public Person(String memberName, int amount) {
		this.memberName = memberName;
		this.amount = amount;
	}

	public String getMemberName() {
		return memberName;
	}

	public int getAmount() {
		return amount;
	}

}
