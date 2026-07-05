# Sistema de Carga y Reporte de Calificaciones (Spring Batch & NoSQL)

Este proyecto consiste en un ecosistema backend desarrollado en Java que implementa un pipeline de procesamiento por lotes (**Spring Batch**) para migrar, procesar y evaluar datos académicos desde fuentes relacionales y archivos planos hacia un entorno NoSQL altamente eficiente.

---

## Stack Tecnológico

*   **Lenguaje:** Java 17 / 21
*   **Framework Principal:** Spring Boot 3.x
*   **Procesamiento por Lotes:** Spring Batch
*   **Bases de Datos:** 
    *   MySQL 8 (Origen de Datos Académicos)
    *   MongoDB 7 (Almacenamiento de Reportes Finales)
*   **Infraestructura Local:** Docker & Docker Compose
*   **Herramientas de Desarrollo:** VS Code, GitHub Copilot, Postman

---

## Arquitectura del Pipeline (Spring Batch)

El flujo de procesamiento masivo está estructurado bajo el patrón clásico **Chunk-Oriented Processing**:

1.  **Reader (Lectura):** Extracción de registros estudiantiles desde un archivo plano `estudiantes.csv` y cruce con entidades persistidas en MySQL.
2.  **Processor (Lógica de Negocio):** Filtrado, cálculo automatizado de promedios académicos y asignación dinámica del estado final del alumno.
3.  **Writer (Escritura):** Persistencia e inserción masiva en documentos estructurados dentro de la colección `reportes_estudiantes` en MongoDB.

---

## Bitácora de Soporte y Solución de Problemas (Troubleshooting)

Durante la fase de integración y despliegue local en contenedores Docker, se identificaron y resolvieron los siguientes incidentes críticos:

### 1. Error de Conexión / HTTP 500 en Endpoints de Consulta
*   **Causa Raíz:** Desalineación en el puerto expuesto por el contenedor NoSQL. El archivo `application.properties` apuntaba al puerto fuera de rango `27018`, mientras que el contenedor real (`mongo-academia`) exponía nativamente el puerto estándar `27017`.
*   **Solución:** Corrección del parámetro de red en la URI de conexión:
    ```properties
    spring.data.mongodb.uri=mongodb://root:root123@localhost:27017/academia?authSource=admin
    ```

### 2. Errores de Autenticación (`Unauthorized` / `Command aggregate requires auth`)
*   **Causa Raíz:** Confusión de credenciales en el entorno de pruebas local. Se intentó interrogar la base de datos NoSQL utilizando el set de accesos pertenecientes al motor relacional (MySQL).
*   **Solución:** Identificación y aislamiento de roles. Acceso exitoso al shell de MongoDB (`mongosh`) mediante la terminal interactiva de Docker inyectando el perfil administrativo correspondiente:
    ```bash
    docker exec -it mongo-academia mongosh -u root -p root123 --authenticationDatabase admin
    ```

---

## Pruebas Unitarias y Calidad de Código (JUnit 5)

Utilizando metodologías asistidas por IA, se construyeron arneses de prueba unitarios robustos enfocados en la validación analítica de los procesadores (`EstudianteProcessorTest` y `ReporteEstudianteProcessorTest`).

Se priorizó la cobertura de **casos límite e intervalos frontera** para mitigar errores de regresión numérica:
*   **Caso Límite Superior (Aprobatorio):** Validación exacta de que un promedio de `70.0` asigne estrictamente el estado `APROBADO`.
*   **Caso Límite Inferior (Reprobatorio):** Validación de que un promedio analítico de `69.9` transicione correctamente al estado `REPROBADO`.

### Ejecución del Suite de Pruebas
```bash
mvn test
