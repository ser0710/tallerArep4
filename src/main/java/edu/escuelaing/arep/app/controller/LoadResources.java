package edu.escuelaing.arep.app.controller;

import edu.escuelaing.arep.app.HttpServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class LoadResources {

    private static LoadResources _instance = new LoadResources();

    public static LoadResources getInstance(){
        return _instance;
    }

    private String type;
    private String file;


    public String head() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type:"+  getType() +"\r\n" +
                "\r\n";
    }

    public String bodyHtml() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/" + getFile()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }

    public String getFile(){
        return file;
    }

    public void setType(String t){
        this.type = t;
    }

    public String getType(){
        return type;
    }

    public void setFile(String f){
        this.file = f;
    }

    public String bodyImg() throws IOException {
        String response = "HTTP/1.1 200 \r\n" +
                "Content-Type: image/png \r\n" +
                "\r\n";
        BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/"+ getFile()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpServer server = HttpServer.getInstance();
        DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        dataOutputStream.writeBytes(response);
        dataOutputStream.write(byteArrayOutputStream.toByteArray());
        return response;
    }

    public String file() throws IOException {
        if(type.startsWith("text")){
            return head() + bodyHtml();
        }else {
            return bodyImg();
        }
    }
}
