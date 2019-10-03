package main.webapp.util.mysql;

import main.webapp.domains.EmpPojo;
import main.webapp.util.GenericJDBCDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class AppConfigDAO extends GenericJDBCDAO {

	@SuppressWarnings("unchecked")
	public List<EmpPojo> getEmpAttributes() {
		List<EmpPojo> empAttributes = null; 
		String query = "SELECT * FROM employee";
		try {
			empAttributes = (List<EmpPojo>) executeQuery(query, DTOBuilderHelper.empAttributesDTOBuilder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empAttributes;

	}

	public void updateEmployee(List<EmpPojo> list) throws ClassNotFoundException, SQLException {
		//List<String> params = null;
		int updateCount = 0;
		Connection  conn= getConnection();
		String updateQuery = "UPDATE employee SET ID = ? WHERE Name = ?";
		PreparedStatement stmt = getQuery(updateQuery, conn);
		
		for (int i = 0; i < list.size(); i++) {
			//params.add(list.get(i).getName());
			stmt.setInt(1, i+1);
			stmt.setString(2, list.get(i).getName());
			updateCount = stmt.executeUpdate();
			System.out.println("Total ::::" + updateCount + ":::: Rows got UPDATED");
		}
	}
}
