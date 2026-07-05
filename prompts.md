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

 ## Prompt 3: Generar estudianteReporte (MongoDB)
 **prompt:** En este paso antes de solicitar el prompt coloqué los imports correspondientes a mongo para que no existiera la confución con jakarta.  

import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Realiza en el siguiente archivo EstudianteReporte cumpliendo con los siguientes requisitos de campos:
 * - id (String, anotado con @Id de Spring Data)
 * - nombre (String)
 * - grupo (String)
 * - promedio (double)
 * - estado (String)
 * 
 * Requisitos de métodos:
 * - Constructor vacío explícito.
 * - Getters y setters para todos los campos.
 * - Método toString().
 */
**acción:** Acepte el autocompletado al momento de ejecutar el @Document, con tab. 

## Prompt 4: Generar ReporteEstudianteProcessor 
**prompt:** Necesito que en este archivo creas una nueva instancia de EstudianteReporte.
Copia los valores de nombre, grupo y promedio desde el objeto estudiante.
Valida el umbral: Asigna el estado "APROBADO" si el promedio es estrictamente mayor o igual a 70 (promedio >= 70).
Asigna el estado "REPROBADO" si el promedio es menor a 70. 
Devuelve el objeto EstudianteReporte generado. 
 Loguea "Step 2 - Reporte: {reporte}" y devuelve el reporte. solo agrega este última instrucción ya que no está. 
**acción:** En este paso decidí no acpetar el autocompletado e hice el prompt ahora en el chat, donde tuve que indicar ya que le faltaba. 
Revisando la condicón como se le indicó en el prompt respeto y si declaro <= a 70. 

# Prompt 5: Generar BatchConfig 
**prompt:** En este prompt no seguí el del ejemplo y estrcuture uno distinto: 
En el siguiente archivo genera una clase @Configuration de Spring Batch (Spring Boot 3.2) llamada BatchConfig en el paquete com.academia.batch.config con los siguientes requisitos estrictos de Spring Batch 5:

1. Inyecta mediante constructor el JobRepository y el PlatformTransactionManager (necesarios para la API de Spring Batch 5), además de DataSource para SQL y MongoTemplate para NoSQL.
2. Step 1 llamado "paso1":
   - Usa FlatFileItemReader para leer "estudiantes.csv" desde el classpath (delimitado, saltando 1 línea, columnas: nombre, grupo, nota1, nota2, nota3; targetType: com.academia.batch.model.Estudiante).
   - Procesa con EstudianteProcessor (inyéctalo como bean o parámetro).
   - Escribe en MySQL con JdbcBatchItemWriter usando la query INSERT INTO estudiantes_procesados (nombre, grupo, nota1, nota2, nota3, promedio) VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio) usando beanMapped.
   - Configura un chunk de 3.
3. Step 2 llamado "paso2":
   - Usa JdbcCursorItemReader para hacer un SELECT nombre, grupo, promedio FROM estudiantes_procesados.
   - Procesa con ReporteEstudianteProcessor (inyéctalo como bean o parámetro).
   - Escribe en MongoDB con MongoItemWriter apuntando a la colección "reportes_estudiantes".
   - Configura un chunk de 3.
4. Un Job llamado "procesarCalificacionesJob":
   - Configura RunIdIncrementer.
   - Ejecuta "paso1" y luego "paso2".
   - Usa la API de Spring Batch 5 (new JobBuilder("procesarCalificacionesJob", jobRepository)... y new StepBuilder(..., jobRepository)...). NO utilices StepBuilderFactory ni JobBuilderFactory bajo ninguna circunstancia.
**acción:** Se realizó la consulta mediante chat y revisando el código tuvo dos errores el primero fue en línea 80, donde aquí fue all cocatenar no tenia los espacios entre el promedio y VALUES, porque las hubiera juntado y existia el error de sintaxis, el segundo error fue en la línea 112 no se había declarado build, como en todos los bean. 

## Prompt 6: Clase de arranque
**prompt:** Sólo seguí el autocompletado, donde se fuerón escribiendo las sugerencias cuando iba escribiendo tanto los import como @SpringBoot para terminar de declrar el méotod de arranque.  

## Prompt 7: API REST 
**prompt:** Crea lo siguiente: 
// Entidad JPA (@Entity, @Table name="estudiantes_procesados") que mapea la tabla existente.
// id Long con @Id y @GeneratedValue(IDENTITY); campos nombre, grupo, nota1, nota2, nota3,
// promedio; getters y setters.
**acción:** se ejecuta mediante chat 

## Prompt 8: EstudianteService
**prompt:** Crea lo siguiente: 
// @Service con inyeccion por constructor de EstudianteRepository.
// Metodo contarAprobados() que devuelve cuantos estudiantes tienen promedio >= 70,
// usando findAll() y un stream con filter y count. 
**acción:** Se ejecuta por medio del chat. 

## Prompt 9: Estudiante Controller
**prompt:** Genera lo siguiente en este archivo: 
Genera un @RestController en /api/estudiantes que use
EstudianteRepository y EstudianteService (inyectados por constructor) con: GET / (listar todos), GET /{id}
(200 o 404), GET /aprobados/total (devuelve un Map con el conteo del servicio), POST / (crea, 201 Created),
PUT /{id} (reemplaza, 200 o 404), PATCH /{id} (cambia solo el grupo desde un Map, 200 o 404), DELETE /{id}
(204 o 404). Usa ResponseEntity para los codigos de respuesta.
**acción:** mediante chat copilot.  

## Prompt 10: RestController
**prompt:**  //@RestController en /api/reportes que usa ReporteRepository:
 // GET / lista todos los reportes; GET /estado/{estado} devuelve los que tengan ese estado
// (convertido a mayusculas) usando findByEstado. 
**acción:** ejecución mediante chat. 






