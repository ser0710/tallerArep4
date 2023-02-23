# Taller 4

Aplicación que por medio del uso de anotaciones se accede a diferentes servicios web, entre ellos archivos html
e imagenes


## Getting Started

### Prerequisites

Git: permite el control de versiones del proyecto

Java: lenguaje en el cual esta desarrollado la totalidad del proyecto

Maven: Software que gestiona proyectos java 


### Installing

Clonamos el repositorio

```
git clone https://github.com/ser0710/tallerArep4.git
```

Entramos en la carpeta donde se encuentra el pom.xml
y ejecutamos

```
java -cp target/classes edu.escuelaing.arep.app.app
```

Una vez veamos el mensaje de "Listo para recibir ..."
entramos al buscador de preferencia
si buscamos la url http://localhost:35000/web donde podremos ver una 
pagina html con css incluido y un pequeño fragmento de javascript.
En caso de querer ver una imagen accederemos al link http://localhost:35000/img.
Finalmente si se ingresa un link no valido será reenviado a una página en concreto.



## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Versioning

version 1.0

## Authors

Sergio Andres Rozo Pulido


