package main.webapp.util.mysql;

import main.webapp.domains.EmpPojo;
import main.webapp.util.DTOBuilder;


import java.sql.ResultSet;
import java.sql.SQLException;

public class DTOBuilderHelper {

	static public DTOBuilder empAttributesDTOBuilder = new DTOBuilder() {
		public Object transform(Object rs) throws SQLException {
			
			EmpPojo empDataDTO = new EmpPojo();
			ResultSet resultSet = (ResultSet) rs;
			empDataDTO.setId(resultSet.getInt("ID"));
			empDataDTO.setName(resultSet.getString("Name"));
			empDataDTO.setSalary(resultSet.getDouble("salary"));
			return empDataDTO;
		}
		
	};
}
