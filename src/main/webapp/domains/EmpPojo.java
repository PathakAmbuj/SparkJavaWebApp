package main.webapp.domains;

import java.io.Serializable;

public class EmpPojo implements Serializable{

	private static final long serialVersionUID = -3384509402830114578L;

	private int id ;
    private String name;
    private double salary;
    
	public EmpPojo(int id, String name, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public EmpPojo() {
		
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

	public double getSalary() {
		return salary;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "EmpPojo [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}

	
    
}
