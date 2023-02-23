package edu.escuelaing.arep.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class HtmlController {

    LoadResources l = new LoadResources();

    /**
     * metodo para la obtencion de recursos simple tipo html
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/hello")
    public static String index() {
        return  "HTTP/1.1 200 \r\n" +
                "Content-Type:text/html\r\n" +
                "\r\n"+
                "Greetings from Spring Boot!";
    }

    /**
     * metodo para la obtencion de recursos tipo html
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/web")
    public static String web() throws IOException {
        LoadResources l = LoadResources.getInstance();
        l.setType("text/html");
        l.setFile("web.html");
        return l.file();
    }

    /**
     * metodo para el manejo de urls no validas
     * @return
     * @throws IOException
     */
    @ErrorMapping()
    public static String error() throws IOException {
        LoadResources l = LoadResources.getInstance();
        l.setType("text/html");
        l.setFile("404.html");
        return l.file();
    }

    /**
     * metodo para la obtencion de recursos tipo css
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/web.css")
    public static String css() throws IOException {
        LoadResources l = LoadResources.getInstance();
        l.setType("text/css");
        l.setFile("web.css");
        return l.file();
    }

    /**
     * metodo para la obtencion de recursos tipo javascript
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/web.js")
    public static String js() throws IOException {
        LoadResources l = LoadResources.getInstance();
        l.setType("text/javascript");
        l.setFile("web.js");
        return l.file();
    }
}