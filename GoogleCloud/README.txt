Google Cloud SDK
Copyright 2013 Google Inc. All Rights Reserved.

The Google Cloud SDK contains tools and libraries that allow you to create and
manage resources on Google's Cloud Platform, including App Engine, Compute
Engine, Cloud Storage, Cloud SQL, and BigQuery.

For more information on how to set up and use the Cloud SDK, please see:
  https://cloud.google.com/sdk

MYSQL: gcloud sql connect myinstance --user=root.


System Requirements:
  - The Cloud SDK for Python and for Go requires Python 2.7.x.
  - The Cloud SDK for Java requires Java 1.7+ and Python 2.7.x.
  - The SDK runs on Windows, Mac OS X, and Linux. Some of the individual tools
    bundled with the Cloud SDK might have more stringent requirements.

1.Abrir la consola CMD desde la carpeta bin
2.gcloud init y luego elegir una cuenta y un proyecto. Luego de elegir el proyecto, la consola permite digitar comandos.


GENERAR PROYECTO
1. EN LA CONSOLA DEL BROWSER DE GOOGLE CLOUD IR A APPENGINE U DAR CRICK EN "CREAR APLICACION" 

PROBAR DESPLEGAR UN PROYECTO

https://console.cloud.google.com/appengine/settings?hl=es-419walkthrough_tutorial_id=java_gae_quickstart&project=perfect-tape-277917

Usa Cloud Shell
Cloud Shell es una herramienta de línea de comandos integrada para la consola. Usarás Cloud Shell para implementar tu app.

Abre Cloud Shell
Para abrir Cloud Shell, haz clic en el botón Activar Cloud Shell de la barra de navegación de la esquina superior derecha de la consola.

Clona el código de muestra
Usa Cloud Shell para clonar el código de “Hello World” y examinarlo. El código de muestra se clona del repositorio de tu proyecto a Cloud Shell.

Nota: Si el directorio ya existe, quita los archivos antiguos antes de realizar la clonación. Para ello, usa el siguiente comando:

rm -rf appengine-try-java
En Cloud Shell, ingresa lo siguiente:

git clone \
    https://github.com/musiciancoder/Courses
Luego, cambia al directorio del instructivo:

cd nombresearchivoimportado



Configura tu implementación
Estás en el directorio principal del código de muestra. En él, podrás ver los archivos que configuran tu aplicación.

Explora la aplicación
Ingresa el siguiente comando para visualizar el código de tu aplicación:

cat \
    src/main/java/myapp/DemoServlet.java
Este servlet responde a cualquier solicitud con una respuesta que contiene el mensaje Hello, world!.

Explora tu configuración
En el caso de Java, App Engine usa archivos XML para especificar la configuración de una implementación.

Ingresa el siguiente comando para visualizar el archivo de configuración:

cat pom.xml
La app helloworld usa Maven, lo que significa que debes especificar un modelo de objetos del proyecto (POM), que contiene información sobre el proyecto y los detalles de configuración que Maven usa para compilarlo.


probar como si fuera en local : mvn appengine:run 

https://cloud.google.com/deployment-manager/docs/step-by-step-guide/installation-and-setup?hl=es-419


spring:
application:
name: Courses
datasource:
driverClassName: com.mysql.cj.jdbc.Driver
url: "jdbcmysql://35.245.190.178/infra-mix-278615:us-east4:coursesstudents?useSSL=false"
password: segundousuario
username: leke2020



runtime: java
env: standard
runtime_config:
  jdk: openjdk8
handlers:
- url: /.*
  script: this field is required, but ignored
  manual_scaling:
  instances: 1
  spring:
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/
    username: segundousuario
    password: leke2020
    database-name: cursosestudiantes
    instance-connection-name: coursesstudents