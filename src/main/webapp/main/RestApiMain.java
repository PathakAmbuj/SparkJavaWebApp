package main.webapp.main;

import com.google.gson.Gson;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import static spark.Spark.get;
import static spark.Spark.port;

import main.webapp.service.RestService;
import spark.Spark;

public class RestApiMain {

    private static RestService service = new RestService();

    public static void main(String[] args) {
        Spark.staticFileLocation(AppConf.staticFileLocation);
        port(AppConf.port);
        Gson gson = new Gson();

        SparkConf conf = new SparkConf().setAppName(AppConf.appName).setMaster(AppConf.master);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();


        get("/", (req, res) -> {
            res.type("html");
            return service.getLogin();
        });

        get("/entry", (req, res) -> {
            res.type("html");
            String user = req.queryParams("txtUserName");
            String password = req.queryParams("txtPassword");
            if (user.equals("ambuj") && password.equals("pathak")) {
                return service.getWelcome(user, password);
            } else {
                return service.getLogiError();
            }
        });

        get("/Home", (request, response) -> {
            response.type("html");
            return service.getHomePage();
        });


        get("/getHtmlTable", (request, response) -> service.getHtmlTable(spark));

        get("/get", (req, res) -> {
            res.type("application/json");
            return service.getEmpTable();
        }, gson::toJson);

        get("/get:id,name", (req, res) -> {
            res.type("application/json");
            String columnName = "id,name";
            return service.getEmpWithAttributes(columnName, spark);
        }, gson::toJson);

        get("/getemp", (req, res) -> {
            res.type("application/json");
            return service.getEmp("", spark);
        });

        get("/getFromFile", (req, res) -> {
            res.type("application/json");
            return service.getFromFile(spark);
        });
        // Dataset<Row> ds = spark.read().json("/Users/Ambuj/Desktop/response.json");
        // ds.show();

    }
}
