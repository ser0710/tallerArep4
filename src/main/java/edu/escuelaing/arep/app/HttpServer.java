package edu.escuelaing.arep.app;

import edu.escuelaing.arep.app.controller.RequestMapping;
import edu.escuelaing.arep.app.spark.Response;
import edu.escuelaing.arep.app.spark.Spark;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.*;

public class HttpServer {

    private static HttpServer _instance = new HttpServer();

    private static OutputStream outputStream = null;

    public Map<String, Method> services = new HashMap<>();

    public static HttpServer getInstance(){
        return _instance;
    }



    /**
     * Método principal, inicia un socket
     * recibe la petición get y agrega el nombre a de la
     * película seleccionada a la URL de la API
     * @param args
     * @throws IOException
     */
    public void run(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        String className = args[0];
        Class c = Class.forName(className);
        Method[] m = c.getMethods();
        for(Method ms : m){
            if(ms.isAnnotationPresent(RequestMapping.class)){
                String key = ms.getAnnotation(RequestMapping.class).value();
                services.put(key, ms);
                System.out.println("key:" + key + " method: " + services.get(key));
            }
        }
        // Cargar clase con forname
        // Extraer metodos con anotacion RequestMapping
        // Extraer el valor del path
        // Etraer instancia del metodo
        // poner en la tabla el metoodo con llave path
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = null;
            String path;
            outputStream = clientSocket.getOutputStream();
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("GET")) {
                    path = inputLine.split(" ")[1];
                    if(!Objects.equals(path, "/favicon.ico")){
                        if(services.containsKey(path)){
                            System.out.println(services.get(path).getName());
                            System.out.println(path);
                            outputLine = (String) services.get(path).invoke(null);

                        }
//                        if(Spark.cache.containsKey(path)){
//                            outputLine = Spark.cache.get(path).getResponse();
//                        }
//                        else{
//                            Spark.setCache(path);
//                            outputLine = Spark.cache.get(path).getResponse();
//                        }
                    }
                }
//                else if(inputLine.startsWith("POST")){
//                    path = inputLine.split(" ")[1];
////                    String finalPath = path;
//                    String paths = path.split("\\?")[0];
//                    String query = path.split("\\?")[1];
//                    outputLine = Spark.post(path, query);
//                }
//                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    };
}

