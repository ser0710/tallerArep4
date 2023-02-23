package edu.escuelaing.arep.app;

import edu.escuelaing.arep.app.controller.Component;
import edu.escuelaing.arep.app.controller.ErrorMapping;
import edu.escuelaing.arep.app.controller.RequestMapping;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.*;

public class HttpServer {

    private static HttpServer _instance = new HttpServer();

    private static OutputStream outputStream = null;

    public static Map<String, Method> services = new HashMap<>();
    public static Method error = null;

    public static HttpServer getInstance(){
        return _instance;
    }



    /**
     * Método principal, inicia un socket
     * recibe la petición get y en base al path
     * retorna el recurso
     * @param args
     * @throws IOException
     */
    public void run(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        String raiz = "edu.escuelaing.arep.app.controller";
        Set<Class<?>> classSet = getClasses(raiz);
        for(Class<?> clazz : classSet){
            boolean hasMyAnnotation = clazz.isAnnotationPresent(Component.class);
            if(hasMyAnnotation){
                String className = clazz.getName();
                Class<?> c = Class.forName(className);
                Method[] m = c.getMethods();
                for(Method ms : m){
                    if(ms.isAnnotationPresent(RequestMapping.class)){
                        String key = ms.getAnnotation(RequestMapping.class).value();
                        services.put(key, ms);
                        System.out.println("key: " + key + " value: " + ms);
                    }else if(ms.isAnnotationPresent(ErrorMapping.class)){
                        System.out.println(ms);
                        error = ms;
                    }
                }
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
                            outputLine = (String) services.get(path).invoke(null);
                        }else{
                            outputLine = (String) error.invoke(null);
                        }
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
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * metodo que busca los diferentes archivos .class en base a una ruta de un posible
     * directorio, asegurandose que este lo sea, una vez encuentra los archivos .class
     * los concatena con el path suministrado y los agrega a un set que sera retornado
     * @param raiz path de la carpeta que se desea analizar
     * @return set de todas las clases en un directorio especifico
     */
    private static Set<Class<?>> getClasses(String raiz){
        Set<Class<?>> classes = new HashSet<>();
        String path = raiz.replace(".", "/");
        try{
            File file = new File(getClassPaths() + "/" + path);
            if(file.exists() && file.isDirectory()){
                for(File classFile : file.listFiles()){
                    if(classFile.isFile() && classFile.getName().endsWith(".class")){
                        String className = raiz + "." + classFile.getName().replace(".class", "");
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classes;
    }


    /**
     * metodo que busca la ubicacion de la carpeta target
     * @return path de la carpeta target
     */
    private static String getClassPaths() {
        String classPath = System.getProperty("java.class.path");
        String[] classPaths = classPath.split(System.getProperty("path.separator"));
        return classPaths[0];
    }

}

