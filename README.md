# GRADUADOS
Sistema de información para graduados de la UFPS.

Pre-requisitos:

1. Tener maven instalado sobre el sistema en donde sera generado el .war
2. Tener tomcat instalado en el servidor.

Pasos para la creación de un sitio:

1. Editar el .sql
  
  * Se debe cambiar el nombre de creacion de la base de datos y el uso. Por completo son dos lineas que se cambian en este paso.
  * Se debe editar el archivo modificando la insercion en la tabla usuario, colocando los usuarios y contraseñas que deseen. Por         completo se cambian dos lineas.
  
2. Ejecutar el .sql en el sistema manejador de base de datos.

3. Sobre el proyecto actual ejecutar el siguiente comando maven

mvn clean install  -Ddriver=com.mysql.jdbc.Driver -Ddatabasetype=mysql -Dip=35.203.35.232 -Dportdabase=3306 -Duser=root -Dpassword=123454 -Ddatabasename=graduados -Dname=abcd 

En donde se especifica los datos de conexion a la base de datos en donde:

driver = El driver de conexion usado. Por ejemplo com.mysql.jdbc.Driver, com.mysql.jdbc.Driver

databasetype = El sistema manejador de base de datos. Por ejemplo mysql, postgresql,

ip = La direccion ip de la base de datos. Por ejemplo localhost, 127.0.0.1, 35.203.35.232

portdabase = Puerto sobre el cual esta expuesta la base de datos.

user = Usuario de la base de datos

password = Contraseña del usuario.

databasename = Nombre de la base de datos colocada en el item 1 del paso 1.

name = Nombre del sistema.

por ejemplo

mvn clean install  -Ddriver=com.mysql.jdbc.Driver -Ddatabasetype=mysql -Dip=35.203.35.232 -Dportdabase=3306 -Duser=root -Dpassword=123110 -Ddatabasename=graduados -Dname=ufps-graduados 



3. Copiar el .war generado dentro de la carpeta target dentro de la carpeta webapps del tomcat en el server.

