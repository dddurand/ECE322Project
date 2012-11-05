package Identifiers;

public class identifier {

	private String name;
	private String id;
	private char type;

	public identifier(String id, String name, char type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return this.id;
	}

	public char getType() {
		return this.type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String Id) {
		this.id = Id;
	}

	public void setType(char type) {
		this.type = type;
	}

	// DUSTIN CHANGED NOV 7
	public String toString() {
		return this.id + ": " + this.name;
	}

}
