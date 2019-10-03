package main.webapp.domains;

public class EmployeeBean {

	private int user_id ;
    private String name;
    private int age;
    private int salary;
    private String city;
    private long contact;
    private String blood_group;
    private int exp_in_year;
    private String department;
	public EmployeeBean() {
		super();
	}
	public EmployeeBean(int user_id, String name, int age, int salary, String city, long contact, String blood_group,
			int exp_in_year, String department) {
		super();
		this.user_id = user_id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.city = city;
		this.contact = contact;
		this.blood_group = blood_group;
		this.exp_in_year = exp_in_year;
		this.department = department;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public int getSalary() {
		return salary;
	}
	public String getCity() {
		return city;
	}
	public long getContact() {
		return contact;
	}
	public String getBlood_group() {
		return blood_group;
	}
	public int getExp_in_year() {
		return exp_in_year;
	}
	public String getDepartment() {
		return department;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}
	public void setExp_in_year(int exp_in_year) {
		this.exp_in_year = exp_in_year;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
