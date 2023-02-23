package edu.escuelaing.arep.app;

import edu.escuelaing.arep.app.spark.Route;
import edu.escuelaing.arep.app.spark.Spark;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * metodo main, obtiene una instancia del servidor y lo corre
 */
public class app {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        HttpServer server = HttpServer.getInstance();
        server.run(args);

    }
}
