## Prompt 0: Verificación de copilot 
**prompt:** //método para sumar dos numeros 
**acción:** acepte con tab la sugerencia por copilot 

## Prompt 1: Ejecución del archivo pom.xml
**prompt:** En el siguiente proyecto actúa como un desarrollador experto en Spring Boot y Maven. Genera el código completo para un archivo 'pom.xml' utilizando Spring Boot versión 3.2.2 y Java 17. 

Configura los metadatos del proyecto con los siguientes valores obligatorios:
- groupId: com.academia
- artifactId: spring-batch-final-calificaciones
- version: 1.0.0
- packaging: jar

Asegúrate de incluir exactamente las siguientes 6 dependencias con sus respectivos ámbitos (scopes):
1. spring-boot-starter-batch
2. mysql-connector-j (con scope runtime)
3. spring-boot-starter-data-mongodb
4. spring-boot-starter-web
5. spring-boot-starter-data-jpa
6. spring-boot-starter-test (con scope test)

importante no olvides incluir el bloque <parent> correspondiente a Spring Boot 3.2.2 y el plugin de construcción 'spring-boot-maven-plugin'. Devuelve únicamente el archivo XML limpio, estructurado y listo para usar. 
**acción:** se hizo el autocompletado, acepte dandole enter y después en la ventana emergente le dí click a la palomita para aceptar los cambios una vez revisado todo el pom.xml, sólo hice una modificación en la versión del parent. 

## Prompt 2: Generar el modelo Estudiante (Pojo) - autocompletado
**prompt:** /**
 * Clase modelo Estudiante (POJO) para procesamiento en Spring Batch.
 * Campos requeridos:
 * - nombre (String)
 * - grupo (String)
 * - nota1 (double)
 * - nota2 (double)
 * - nota3 (double)
 * - promedio (double)
 * 
 * Requisitos:
 * - Constructor vacío explícito.
 * - Métodos Getters y Setters para todos los campos.
 * - Método toString() que muestre únicamente: nombre, grupo y promedio.
 */ 
 **acción:** Una vez que iba escribiendo la primera linea de código le di tab para ir aceptando el autocompletado, revisando las especificaciones como los campos, el constructor vacío, los métodos getters - setters y al último el método toString, válidando así los 6 campos, finalmente corroborando el código, lo guardé. 

 ## Prompt 3:  
