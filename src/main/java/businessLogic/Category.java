package businessLogic;

public class Category {
	private String name;
	
	public Category() {
		this("default");
	}
	
	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
