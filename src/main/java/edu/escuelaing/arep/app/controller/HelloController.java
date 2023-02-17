package edu.escuelaing.arep.app.controller;

public class HelloController {

    @RequestMapping(value = "/hello")
    public String index() {
        return  "HTTP/1.1 200 \r\n" +
                "Content-Type:text/html\r\n" +
                "\r\n"+
                "Greetings from Spring Boot!";
    }
}