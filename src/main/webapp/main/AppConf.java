package main.webapp.main;

public interface AppConf {

    String staticFileLocation = "/public";
    int port = 8081;
    String appName = "SparkJava_WebApp";
    String master = "local[*]";
}
