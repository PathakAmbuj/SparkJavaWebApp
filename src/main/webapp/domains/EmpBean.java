package main.webapp.domains;

import java.io.Serializable;

public class EmpBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id ;
    private String name;
    
    
    
	public EmpBean() {
		
	}

	public EmpBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EmpBean [id=" + id + ", name=" + name + "]";
	}
    
    
}
