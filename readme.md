# Guía para Ejecutar y Desplegar el Módulo

Este documento proporciona los pasos detallados para ejecutar y desplegar el módulo de este proyecto Maven.

## 1. Prerrequisitos

Antes de comenzar, asegúrate de tener lo siguiente instalado:

- **Java JDK**: Asegúrate de tener una versión compatible de Java instalado (se recomienda Java 8 o superior).
    - Puedes verificar la instalación de Java con el siguiente comando en la terminal de Windows:
    java -version

- **Maven**: Debes tener Maven instalado: Puedes verificar la instalación en la terminal de Windows con:
    mvn -version

- **VSCode**: Si deseas desarrollar o modificar el proyecto, asegúrate de tener las siguientes extensiones instaladas:
    - Java Extension Pack
    - Maven for Java

- **PostgreSQL**: Crear una base de datos SisAcademico, ejecutar el archivo de tablas.sql y actualizar la clase DBUtil.java:
    - private static final String USER = "postgres";
    - private static final String PASSWORD = "admin"; (En este caso la contraseña de tu gestor)

## 2. Clonar el Repositorio
Primero, clona el repositorio desde GitHub (o desde la ubicación del repositorio en tu caso):
    - https://github.com/StevenJ10/SisAcademico.git

## 3. Limpiar el proyecto:
Una vez clonado el proyecto, abre una terminal (VSCode) y navega hasta la carpeta del proyecto. Para limpiar el proyecto, ejecutar el siguiente comando:
    mvn clean

## 4. Compilar el código:
    mvn compile

### 4.1 Ejecutar en la consola de VSCode
    mvn javafx:run

## 5. Asegurarse que las pruebas unitarias e integración se ejecuten correctamente:
    mvn test

## 6. Empaquetar el código en un archivo distribuible (JAR):
    mvn package

## 7. Instalar el paquete en el repositorio local (Opcional):
    mvn install

## 8. Publicación en un repositorio remoto:
Requisitos para usar el siguiente comando:
    - Configurar el repositorio remoto en pom.xml: 
        <distributionManagement>
            <repository>
                <id>internal-repo</id>
                <url>file:///C:/deploy_maven</url>
            </repository>
        </distributionManagement>
    
    - Ejecutar el comando en la terminal de VSCode:
        mvn deploy

    - Buscar la carpeta donde configuro la ruta en el archivo de configuración ´pom.xml´. Copiar esa ruta y abrir un terminal de windows. 

### 8.1 Prerrequisitos para ejecutar en un repositorio remoto

Antes de comenzar, asegúrate de configurado las variables de entorno de lo siguiente:

- **JavaFX SDK**: Del siguiente enlace descargar la versión adecuada para su sistema https://gluonhq.com/products/javafx/

    - Crear una carpeta en el disco local C: "javafx" y descomprimir el archivo descargado.

    - Abrir las variables del entorno y en variables del sistema selecciona nueva:

        Nombre de la variable: PATH_TO_FX
        Valor de la variable: Ruta donde descomprimió el .rar

- **PostgreSQL**: Del siguiente enlace descargar la versión más cercana a la que se usando en el proyecto https://jdbc.postgresql.org/download/ 

    - Crear una carpeta en el disco local C: "drivers" y mover el archivo jar descargado.

### 8.2 Ejecución
En la terminal de windows dirigirnos en la ruta donde tenemos nuestros archivos del repositorio remoto y ejecutamos el siguiente comando:
    java --module-path "C:\javafx\javafx-sdk-23.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp "demo-1.0-20250121.044115-1.jar;C:\drivers\postgresql-42.7.4.jar" com.example.App


## 9. Simulación con Apache TomCat

### 9.1 Prerrequisitos para ejecutar en un repositorio remoto

Antes de comenzar, asegúrate de tener lo siguiente instalado:

- **Apache Tomcat**: Del siguiente enlace descargar la versión Windows Service Installer: 
    https://tomcat.apache.org/download-11.cgi

- Para comprobar si tienen instalado correctamente el servidor ingresa la siguiente ruta en el navegador:
    - localhost:8080

- Luego la siguiente ruta del proyecto para visualizar la ejecución: 
    - http://localhost:8080/demo-1.0-SNAPSHOT/login.jsp