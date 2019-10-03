package main.webapp.service;

import main.webapp.util.GenericJDBCDAO;
import main.webapp.util.mysql.DTOBuilderHelper;
import main.webapp.domains.EmpBean;
import main.webapp.domains.EmpPojo;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class RestService extends GenericJDBCDAO {

    public Object getLogin() throws IOException {
        StringBuilder sb = new StringBuilder();
        String content = new String(
                Files.readAllBytes(
                        Paths.get("src/main/resources/private/login.html")));
        return sb.append(content).toString();
    }

    public Object getLogiError() throws IOException {
        StringBuilder sb = new StringBuilder();
        String content = new String(
                Files.readAllBytes(
                        Paths.get("src/main/resources/private/loginError.html")));
        return sb.append(content).toString();
    }

    public Object getHomePage() throws IOException {
        StringBuilder sb = new StringBuilder();
        String content = new String(
                Files.readAllBytes(
                        Paths.get("src/main/resources/private/homePage.html")));
        return sb.append(content).toString();
    }

    //######................................................................................................
    public String getWelcome(String user, String password) throws IOException {
        StringBuilder sb = new StringBuilder();
        String res = "";
        if (user.equals("ambuj") && password.equals("pathak")) {
            String content = new String(
                    Files.readAllBytes(
                            Paths.get("src/main/resources/private/htmlMainPage.html")));
            res = sb.append(content).toString();
        } else {
            res = "<h1> User_iD or Password does not match :::(</h1>";
        }


        return res;
    }

    public String getHtmlTable(SparkSession spark) throws IOException {

        Dataset<Row> readDF1 = spark.read().format("csv").option("sep", ",").option("header", true)
                .option("inferSchema", true).load("/Users/Ambuj/Downloads/aapl.csv");

        Dataset<Row> readDF = readDF1.withColumn("Open-Close", readDF1.col("Open")
                .minus(readDF1.col("Close")))
                .withColumn("Low-Close",
                        readDF1.col("Low").minus(readDF1.col("Close")));
        // list = readDF.as(Encoders.bean(EmployeeBean.class)).collectAsList();
        StringBuilder sb = new StringBuilder();
        String contents = new String(
                Files.readAllBytes(
                        Paths.get("src/main/resources/private/html.html")));

        sb.append(contents);

        StructType schema = readDF.schema();
        String str = schema.catalogString().replace("struct", "")
                .replace("<", "").replace(">", "");

        for (String type : str.split(",")) {
            sb.append("<th>" + type.split(":")[0] + "</th>");
        }
        sb.append("</tr></thead><tbody>");

        List<Row> l = readDF.collectAsList();
        StringBuilder sb1 = new StringBuilder();
        String[] type = str.split(",");

        for (Row row : l) {
            sb1.append("<tr>");
            for (int j = 0; j < type.length; j++) {
                String[] value = type[j].split(":");
                if (value[1].equalsIgnoreCase("string"))
                    sb1.append("<td>").append(row.getString(j)).append("</td>");
                else if (value[1].equalsIgnoreCase("int"))
                    sb1.append("<td>").append(row.getInt(j)).append("</td>");
                else if (value[1].equalsIgnoreCase("bigint"))
                    sb1.append("<td>").append(row.getLong(j)).append("</td>");
                else if (value[1].equalsIgnoreCase("double"))
                    sb1.append("<td>").append(row.getDouble(j)).append("</td>");
                else
                    sb1.append("<td>").append(row.getString(j)).append("</td>");
            }
            sb1.append("</tr>");
        }

        sb.append(sb1);

        sb.append("</tbody></table></body>\n" + "</html>");

        return sb.toString();

    }

    @SuppressWarnings("unchecked")
    public List<EmpPojo> getEmpTable() {
        List<EmpPojo> empAttributes = null;
        String query = "SELECT * FROM employee";
        try {
            empAttributes = (List<EmpPojo>) executeQuery(query, DTOBuilderHelper.empAttributesDTOBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empAttributes;

    }

    @SuppressWarnings("unchecked")
    public List<EmpBean> getEmpWithAttributes(String columnNames, SparkSession spark) {
        List<EmpPojo> empAttributes = null;
        String query = "SELECT * FROM emp";
        try {
            empAttributes = (List<EmpPojo>) executeQuery(query, DTOBuilderHelper.empAttributesDTOBuilder);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        Dataset<EmpPojo> ds = spark.createDataset(empAttributes, Encoders.bean(EmpPojo.class));

        String str[] = columnNames.split(",");
        Dataset<Row> ds1 = ds.selectExpr(str);

        return (List<EmpBean>) ds1.as(Encoders.bean(EmpBean.class)).collectAsList();

    }

    public Object getEmp(String columnNames, SparkSession spark) {
        Dataset<Row> readDF = spark.read().format("jdbc").option("url", "jdbc:mysql://localhost:3306/ambuj")
                .option("user", "root").option("password", "Narayan1989#").option("dbtable", "emp")
                .option("driver", "com.mysql.cj.jdbc.Driver").load();

        List<String> list = null;

        if (!columnNames.equals("")) {
            String str[] = columnNames.split(",");
            Dataset<Row> ds = readDF.selectExpr(str);
            list = ds.toJSON().collectAsList();
            // list.forEach( x -> x.replaceAll("\\\\", ""));
        } else if (columnNames.equals("")) {
            list = readDF.toJSON().collectAsList();
            // list.forEach( x -> x.replaceAll("\\\\", ""));
        }

        // list.forEach(x -> System.out.println(x));
        return list.toString().replace("[", "").replace("]", "");
    }

    public Object getFromFile(SparkSession spark) {
        Dataset<Row> ds = spark.read().format("csv").option("sep", ",").option("header", true)
                .option("inferSchema", true).load("/Users/Ambuj/Downloads/aapl.csv");
        List<String> list = null;
        list = ds.toJSON().collectAsList();
        // list.forEach( x -> x.replaceAll("\\\\", ""));
        return list;
    }

}
