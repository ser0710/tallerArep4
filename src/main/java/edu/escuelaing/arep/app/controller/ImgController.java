package edu.escuelaing.arep.app.controller;

import java.io.IOException;

@Component
public class ImgController {

    LoadResources l = new LoadResources();

    /**
     * metodo para la obtencion de recursos tipo imagen
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/img")
    public static String img() throws IOException {
        LoadResources l = LoadResources.getInstance();
        l.setType("image/png");
        l.setFile("imagen.png");
        return l.file();
    }
}
