package main.webapp.util;

import java.sql.SQLException;

public interface DTOBuilder {

	public Object transform(Object rs) throws SQLException;
}
