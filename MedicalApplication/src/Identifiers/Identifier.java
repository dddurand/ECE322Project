package Identifiers;

public class Identifier {
	private String name;
	private String id;
	private char type;

	public Identifier(String id, String name, char type) {
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

	public String toString() {
		return this.id + ": " + this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Identifier)) {
			return false;
		}
		Identifier other = (Identifier) o;
		return name.equals(other.name) && id.equals(other.id) && type == other.type;
	}
}
