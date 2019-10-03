package main.webapp.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class GenericJDBCDAO {

    private Connection conn = null;
    Statement stmt = null;
    PreparedStatement prpdstmt = null;

    protected PreparedStatement getQuery(String query, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        return ps;
    }

    protected PreparedStatement getQueryWithParam(String query, Collection<?> param, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        initParams(ps, param);
        return ps;
    }

    protected Object initParams(PreparedStatement ps, Collection<?> param) throws SQLException {
        Object[] values = param.toArray();
        for(int i = 0; i < values.length; i++) {
            if(values[i] == null) {
                ps.setNull(i+1, Types.NULL);
            }else
            {
                ps.setObject(i+1, values[i]);
            }
        }
        return ps;

    }

    public Object executeQuery(String sqlQuery, DTOBuilder transformer) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement queryPS = null;
        ResultSet queryRS = null;
        boolean bResultSet = false;
        try {
            conn = getConnection();
            queryPS = getQuery(sqlQuery, conn);
            bResultSet = queryPS.execute();
            Object result = null;
            if (bResultSet) {
                queryRS = queryPS.getResultSet();
                result = new ResultSetUtils().collect(queryRS, transformer);
            }
            return result;
        }
        finally {
            queryPS.close();
            queryRS.close();
            conn.close();
        }
    }

    protected int executeUpdate(String sqlQuery, Collection<?> params) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement queryPS = null;
        int updateCount  = 0;
        conn = getConnection();
        queryPS = getQueryWithParam(sqlQuery, params, conn);
        updateCount = queryPS.executeUpdate();
        return updateCount;
    }

    private class ResultSetUtils {
        public Collection<Object> collect(ResultSet queryRS, DTOBuilder transformer) throws SQLException {
            ArrayList<Object> rows = new ArrayList<Object>();
            Object row = null;
            if (queryRS != null && transformer != null) {
                while (queryRS.next()) {
                    row = transformer.transform(queryRS);
                    rows.add(row);
                }
            }
            return rows;
        }
    }

    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        String driverClass = "com.mysql.cj.jdbc.Driver";
        String USER = "#####";
        String PASS = "#####";
        String URL = "jdbc:mysql://localhost:3306/ambuj";
        Class.forName(driverClass);
        conn = DriverManager.getConnection(URL, USER, PASS);
        return conn;

    }
}
