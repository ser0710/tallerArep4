package edu.escuelaing.arep.app;

import edu.escuelaing.arep.app.spark.Route;
import edu.escuelaing.arep.app.spark.Spark;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class app {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        HttpServer server = HttpServer.getInstance();
//        Spark.get("/hello", (req, res) -> {
//            res.setType("text/html");
//            return res.getResponse();
//        });
        server.run(args);

    }
}
