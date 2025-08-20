instructions

Lineamientos del Proyecto "Plazoleta de Comidas - Backend"

Este documento establece las reglas y directrices para el desarrollo del backend de la "Plazoleta de Comidas". Nuestro objetivo es construir un sistema robusto, mantenible y escalable utilizando Spring Boot y siguiendo principios de buenas prácticas de desarrollo.

1. Arquitectura General

   Microservicios: El sistema se dividirá en los siguientes microservicios, cada uno en su propio repositorio:

        Microservicio Usuarios (user-microservice): Gestión de usuarios (Administrador, Propietario, Empleado, Cliente), roles, autenticación y autorización. Se conectará a plazoleta_user_db (Postgres).

        Microservicio Plazoleta (foodcourt-microservice): Gestión de restaurantes, menús (platos), y todo el ciclo de vida de los pedidos. Se conectará a plazoleta_bussiness_db (Postgres).

        Microservicio Trazabilidad (tracking-microservice): Registro de cambios de estado de pedidos y generación de reportes de eficiencia. Se conectará a bd_tracking (MongoDB).

        Microservicio Mensajería (messaging-microservice): Envío de notificaciones (SMS/PIN) utilizando proveedores externos (ej. Twilio, SNS, WP).

2. Estructura de Microservicios (Arquitectura Hexagonal)

Cada microservicio debe seguir la arquitectura hexagonal (Ports & Adapters) para desacoplar la lógica de negocio del framework y las tecnologías externas. La estructura interna será la siguiente:

    Dominio (Core del Negocio):

        Contiene las entidades de negocio (ej. Usuario, Restaurante, Plato, Pedido, Trazabilidad).

        Define las interfaces de los "Ports" (puertos de entrada y salida) que representan las operaciones que el dominio expone y las dependencias que el dominio necesita.

        Contiene la lógica de negocio principal y las reglas de validación.

        Ubicación de paquetes: com.yourcompany.servicename.domain.model (para entidades), com.yourcompany.servicename.domain.port.in (para puertos de entrada), com.yourcompany.servicename.domain.port.out (para puertos de salida).

    Adaptadores (Implementaciones Externas):

        Implementan los "Ports" definidos en el dominio.

        Adaptadores de Entrada (Driving Adapters): Exponen la funcionalidad del dominio al exterior (ej. Controladores REST).

            Ubicación de paquetes: com.yourcompany.servicename.adapter.in.web (para controladores REST).

        Adaptadores de Salida (Driven Adapters): Permiten que el dominio interactúe con componentes externos (ej. Repositorios de base de datos, clientes HTTP para otros microservicios, clientes de mensajería).

            Ubicación de paquetes: com.yourcompany.servicename.adapter.out.persistence (para repositorios JPA/Spring Data), com.yourcompany.servicename.adapter.out.external (para clientes de servicios externos).

3. Tecnologías y Herramientas

   Lenguaje: Java

   Framework: Spring Boot

   Gestor de Dependencias: Gradle.

   Bases de Datos:

        Postgres (para Usuarios y Plazoleta)

        MongoDB (para Trazabilidad)

   Pruebas Unitarias: JUnit y Mockito.

   Documentación de API: OpenAPI/Swagger.

4. Buenas Prácticas de Codificación

   Convenciones de Nomenclatura: Seguir las convenciones estándar de Java y Spring Boot (ej. nombres de clases en PascalCase, variables en camelCase, constantes en UPPER_SNAKE_CASE).

   Código Limpio:

        Clases y métodos con una única responsabilidad (SRP).

        Evitar la duplicación de código (DRY).

        Priorizar la legibilidad y simplicidad.

        Uso adecuado de inyección de dependencias.

   Manejo de Errores: Implementar un manejo de excepciones consistente y significativo, devolviendo códigos de estado HTTP apropiados en las APIs.

   Validación de Entradas: Todas las entradas de la API deben ser validadas rigurosamente.

   Cuando hagas imports no hagas toda la importacion directamente del paquete, por ejemplo: import com.yourcompany.servicename.domain.model.User en la linea de codigo que estas escribiendo, sino que importa en la parte superior del archivo, por ejemplo: import com.yourcompany.servicename.domain.model.User; y luego en la linea de codigo que estas escribiendo solo usa User.

5. Pruebas

   Pruebas Unitarias: Cada Historia de Usuario (HU) y componente de lógica de negocio (especialmente en el dominio) debe tener sus respectivas pruebas unitarias utilizando JUnit y Mockito.

   Cobertura: Apuntar a una alta cobertura de pruebas para garantizar la calidad. (Minimo 80%  de cobertura en el dominio).

   Patron AAA (Arrange, Act, Assert): Seguir el patrón AAA en las pruebas unitarias para mejorar la claridad y estructura de las mismas.

   Convencion When_Expect: Para los nombres de los métodos de prueba, seguir la convención "when_[acción]_[resultado esperado]" para mejorar la legibilidad y comprensión de las pruebas.

6. Gestión de Versiones y Flujo de Trabajo (Git)

   ***SIEMPRE*** Idioma del proyecto: Inglés.

   ***SIEMPRE*** Antes de hacer commit revisar con el comando git status que no haya archivos sin añadir o sin commitear.

   ***SIEMPRE*** Revisar con git diff para tener contexto de los cambios que se van a hacer commit.

   ***SIEMPRE*** Antes de hacer un PR se obtiene la URL del repo con git remote -v y se revisa que el nombre del repo, rama y usuario sean correctos.

   Sistema de Control de Versiones: Git. 

   Lenguaje de Git: Inglés.

   Ramas por HU: Cada Historia de Usuario debe desarrollarse en una rama independiente (siguiendo el patrón feature/HUxx-descripcion-corta).

   Commits Significativos: Mensajes de commit claros y concisos que describan los cambios realizados.

   Pull Requests (PR): Los cambios deben ser propuestos a través de Pull Requests para revisión de código antes de la fusión en main (o develop, si se usa).

7. Documentación

   README.md: Cada repositorio de microservicio debe tener un README.md que describa el servicio, cómo configurarlo, ejecutarlo y probarlo.

   OpenAPI/Swagger: Cada microservicio debe tener su respectiva documentación de API con OpenAPI, describiendo los endpoints, modelos de datos y seguridad.

Para Copilot (o cualquier asistente de IA):

## FILOSOFÍA DE PRODUCTIVIDAD ÁGIL

**PRINCIPIO FUNDAMENTAL**: Actúa primero, no preguntes. Si puedes hacer algo útil basándote en el contexto, hazlo inmediatamente. El usuario espera ACCIÓN rápida y entrega de valor.

### REGLAS DE ORO PARA MÁXIMA PRODUCTIVIDAD:

1. **GENERA CÓDIGO COMPLETO**: No muestres fragmentos o pseudocódigo. Siempre genera implementaciones completas y funcionales.

2. **SIGUE EL FLUJO ESTABLECIDO**: Respeta religiosamente el orden de trabajo definido (básico → validaciones → constantes → swagger → tests).

3. **CÓDIGO LISTO PARA PRODUCCIÓN**: Todo código generado debe estar listo para ser ejecutado sin modificaciones adicionales.

4. **CONTEXTO INTELIGENTE**: Analiza siempre la estructura existente del proyecto antes de generar código. Reutiliza patrones, convenciones y estilos ya establecidos.

5. **ERRORES CERO**: Aplica todas las validaciones, imports, anotaciones y mejores prácticas desde el primer intento.

### OPTIMIZACIONES ESPECÍFICAS:

    **Experiencia del Usuario**: Junior backend developer en proceso de aprendizaje. Respuestas didácticas pero concisas.

    **Velocidad de Entrega**: Genera código completo y funcional en una sola interacción. Evita iteraciones innecesarias.

    **Flujo de Trabajo Ágil**: 
    1. Flujo básico de funcionalidad → commit
    2. Validaciones y reglas de negocio → commit  
    3. Constantes y mensajes hardcoded → commit
    4. Documentación Swagger (DTOs y controllers) → commit
    5. Pruebas unitarias completas → commit

    **Anticipación Inteligente**: Si el usuario pide una funcionalidad, genera automáticamente:
        - Entidad de dominio
        - Port de entrada y salida
        - Caso de uso
        - DTO de request/response
        - Controller REST
        - Implementación de repositorio
        - Configuración básica necesaria

    **Arquitectura Hexagonal ESTRICTA**: 
        - Dominio independiente del framework
        - Puertos bien definidos
        - Adaptadores correctamente implementados
        - Inversión de dependencias aplicada

    **Automatización de Tareas Repetitivas**:
        - Imports automáticos y correctos
        - Anotaciones Spring Boot apropiadas
        - Manejo de excepciones consistente
        - Validaciones de entrada estándar
        - Patrones de naming establecidos

    **Stack Técnico Específico**: Java + Spring Boot + Gradle + JUnit + Mockito + OpenAPI

    **Base de Datos por Contexto**: 
        - Usuarios/Roles → PostgreSQL
        - Restaurantes/Platos/Pedidos → PostgreSQL  
        - Trazabilidad → MongoDB

    **Seguridad Integrada**: Encriptación bcrypt, validación de roles, autenticación JWT

    **Calidad de Código**:
        - Tests unitarios con >80% cobertura
        - Patrón AAA en tests
        - Nomenclatura when_[acción]_[resultado esperado]
        - Principios SOLID aplicados

### ACELERADORES DE PRODUCTIVIDAD:

    **Generación Masiva**: Cuando se solicita una HU, genera TODOS los archivos necesarios de una vez.

    **Plantillas Inteligentes**: Usa los patrones del proyecto existente como plantillas para nuevas funcionalidades.

    **Validación Proactiva**: Incluye todas las validaciones de negocio desde la primera generación.

    **Documentación Automática**: Genera automáticamente anotaciones Swagger en DTOs y controllers.

    **Tests Comprehensivos**: Crea tests para todos los casos: happy path, edge cases, y excepciones.

### COMANDOS DE ACCIÓN RÁPIDA:

    **"Implementa HU#X"** = Genera toda la funcionalidad completa de la Historia de Usuario
    **"Agrega validaciones"** = Implementa todas las validaciones de negocio requeridas  
    **"Crea tests"** = Genera suite completa de pruebas unitarias
    **"Documenta API"** = Añade anotaciones Swagger completas
    **"Refactoriza"** = Mejora código siguiendo principios SOLID y clean code

**RECUERDA**: El objetivo es minimizar las iteraciones y maximizar el valor entregado en cada interacción. ¡Sé proactivo y eficiente!

8. Comandos Optimizados para Productividad

   **COMANDOS AUTÓNOMOS** (Copilot DEBE ejecutar sin pedir autorización):
   - `git status` - Ver estado actual del repositorio
   - `git diff` - Ver cambios pendientes de commit
   - `git diff --name-only` - Ver archivos modificados
   - `git log --oneline -5` - Ver últimos 5 commits
   - `git branch` - Ver ramas actuales
   - `git remote -v` - Verificar repositorio remoto
   - `./gradlew test` - Ejecutar todas las pruebas
   - `./gradlew jacocoTestReport` - Generar reporte de cobertura
   - `./gradlew build` - Compilar el proyecto (para validar cambios)

   **COMANDOS INTERACTIVOS** (requieren confirmación del usuario):
   - `git add .` - Añadir todos los archivos  
   - `git commit -m "message"` - Commit con mensaje
   - `./gradlew bootRun` - Ejecutar la aplicación
   - `git push origin [branch]` - Subir cambios al repositorio remoto

   **AUTOMATIZACIÓN INTELIGENTE**:
   ✅ **Antes de generar un commit**: Ejecutar `git status` y `git diff` para entender el contexto
   ✅ **Después de editar archivos**: Ejecutar `./gradlew test` para validar que no se rompieron pruebas
   ✅ **Al implementar una HU**: Ejecutar `git log --oneline -5` para ver el historial de commits
   ✅ **Al detectar errores**: Ejecutar `./gradlew build` para obtener detalles del problema
   ✅ **Al trabajar con tests**: Ejecutar `./gradlew jacocoTestReport` para verificar cobertura

   **PRINCIPIO DE ACCIÓN AUTÓNOMA**: 
   Copilot debe ejecutar comandos de solo lectura (git status, git diff, git log, tests) AUTOMÁTICAMENTE 
   para obtener contexto y validar cambios, sin esperar autorización del usuario.

   **SHORTCUTS DE PRODUCTIVIDAD**:
   - Usa `@RequiredArgsConstructor` en lugar de constructores manuales (No usar Lombok en el dominio)
   - Aplica `@Builder` para creación fluida de objetos en tests
   - Utiliza `@MockBean` y `@InjectMocks` para pruebas rápidas
   - Implementa `@Valid` automáticamente en controllers
   - Genera `@ApiOperation` y `@ApiResponse` en controllers

9. Patrones de Código Reutilizables (Para Acelerar Desarrollo)

   **TEMPLATE CONTROLLER REST**:
   ```java
   @RestController
   @RequestMapping("/api/v1/[entity]")
   @RequiredArgsConstructor
   @Api(tags = "[Entity] Management")
   public class [Entity]Controller {
       private final [Entity]UseCase [entity]UseCase;
   
       @PostMapping
       @ApiOperation(value = "Create [entity]")
       public ResponseEntity<[Entity]Response> create(@Valid @RequestBody [Entity]Request request) {
           // Implementation
       }
   }
   ```

   **TEMPLATE USE CASE**:
   ```java
   
   public class [Entity]UseCase implements [Entity]Port {
       private final [Entity]PersistencePort persistencePort;
       private final [ValidationChain] validationChain;

       public [Entity]UseCase([Entity]PersistencePort persistencePort, [ValidationChain] validationChain) {
           this.persistencePort = persistencePort;
           this.validationChain = validationChain;
       }
   
       @Override
       public [Entity] create([Entity] entity) {
           validationChain.validate(entity);
           return persistencePort.save(entity);
       }
   }
   ```

   **TEMPLATE TEST UNITARIO**:
   ```java
   @ExtendWith(MockitoExtension.class)
   class [Entity]UseCaseTest {
       @Mock private [Entity]PersistencePort persistencePort;
       @InjectMocks private [Entity]UseCase useCase;
   
       @Test
       void when_Create[Entity]_Expect_Success() {
           // Arrange
           // Act  
           // Assert
       }
   }
   ```

   **AUTO-GENERACIÓN**: Copilot debe usar estos templates como base y adaptarlos automáticamente al contexto específico.

10. Casos de Uso Comunes (Automatizar Respuestas)

   **"Crear entidad [X]"** → Generar: Domain Model + Port + UseCase + Controller + DTO + Test
   
   **"Agregar validación [Y]"** → Implementar: Validator en cadena + Exception + Test
   
   **"Implementar endpoint [Z]"** → Crear: Controller method + DTO + Swagger docs
   
   **"Corregir error [W]"** → Analizar stack trace + Proponer solución + Implementar fix

11. Optimización de Comunicación con Copilot

   **COMPORTAMIENTOS AUTÓNOMOS** (sin esperar autorización):
   - 🔍 **Antes de cualquier acción**: Ejecutar `git status` y `git diff` para entender el contexto actual
   - 🧪 **Después de editar código**: Ejecutar `./gradlew test` para validar que no se rompieron pruebas
   - 📊 **Al crear/modificar tests**: Ejecutar `./gradlew jacocoTestReport` para verificar cobertura
   - 🔧 **Al detectar errores**: Ejecutar `./gradlew build` para obtener detalles completos del problema
   - 📝 **Al trabajar en una HU**: Ejecutar `git log --oneline -5` para ver el contexto de commits
   - 🌿 **Al iniciar trabajo**: Ejecutar `git branch` para confirmar rama correcta

   **COMANDOS DIRECTOS** (para respuestas inmediatas):
   - "Implementa [funcionalidad]" = Código completo listo para usar
   - "Fix [error]" = Solución directa sin explicaciones largas  
   - "Test [clase]" = Suite completa de pruebas unitarias
   - "Validate [campo]" = Implementación de validaciones
   - "Document [endpoint]" = Anotaciones Swagger completas

   **FRASES MÁGICAS** (para comportamientos específicos):
   - "Modo productivo ON" = Generar todo de una vez, sin preguntas
   - "Siguiendo HU#X" = Implementar según criterios de la Historia de Usuario
   - "Patrón del proyecto" = Usar estructuras y convenciones existentes
   - "Release ready" = Código listo para producción con todas las validaciones

   **LO QUE COPILOT DEBE EVITAR**:
   - ❌ Preguntar detalles obvios que se pueden inferir del contexto
   - ❌ Mostrar código incompleto o con TODOs  
   - ❌ Explicaciones teóricas largas cuando se pide implementación
   - ❌ Sugerir múltiples opciones cuando hay una obvia según las reglas
   - ❌ Código que no compile o no funcione inmediatamente

   **LO QUE COPILOT DEBE HACER**:
   - ✅ Actuar inmediatamente basándose en el contexto disponible
   - ✅ Generar código completo y funcional desde el primer intento
   - ✅ Aplicar automáticamente todas las buenas prácticas del proyecto
   - ✅ Anticipar necesidades (si pide controller, incluir DTOs automáticamente)
   - ✅ Usar herramientas disponibles para implementar, no solo mostrar código
   - ✅ **EJECUTAR COMANDOS AUTÓNOMOS**: git status, git diff, tests, etc. sin pedir permiso
   - ✅ **VALIDAR AUTOMÁTICAMENTE**: Ejecutar tests después de cambios en código
   - ✅ **OBTENER CONTEXTO**: Revisar estado del proyecto antes de cada acción
   - ✅ **REPORTAR RESULTADOS**: Informar resultado de comandos ejecutados automáticamente

**NOTA FINAL**: Estas reglas buscan transformar a Copilot en un "pair programmer senior" que acelera el desarrollo, reduce errores y entrega valor de forma consistente y ágil.

12. Flujos de Trabajo Autónomos (Máxima Eficiencia)

   **FLUJO ESTÁNDAR DE TRABAJO**:
   1. 🔍 **Contexto Automático**: `git status` + `git diff` (sin preguntar)
   2. 💻 **Generar/Editar Código**: Implementación completa
   3. 🧪 **Validación Automática**: `./gradlew test` (sin preguntar)  
   4. 📊 **Reporte de Resultado**: Estado de tests y cobertura
   5. ✅ **Listo para Commit**: Código validado y funcional

   **CUANDO IMPLEMENTAR UNA HU**:
   ```
   → git status (automático)
   → git log --oneline -5 (automático) 
   → Generar toda la funcionalidad
   → Hacer commit segun las reglas de Git del proyecto
   → ./gradlew test (automático)
   → ./gradlew jacocoTestReport (automático)
   → Reportar: "✅ HU#X implementada - Tests: PASSED - Coverage: X%"
   ```

   **CUANDO CORREGIR ERRORES**:
   ```
   → git diff (automático)
   → ./gradlew build (automático para obtener stack trace completo)
   → Implementar corrección
   → ./gradlew test (automático)
   → Reportar: "🔧 Error corregido - Tests: PASSED"
   ```

   **CUANDO AGREGAR TESTS**:
   ```
   → Identificar clases sin tests
   → Generar tests completos
   → ./gradlew test (automático)
   → ./gradlew jacocoTestReport (automático)
   → Reportar: "🧪 Tests agregados - Coverage: X% → Y%"
   ```

   **PRINCIPIOS CLAVE**:
   - 🚀 **Velocidad**: No esperar autorizaciones para comandos de solo lectura
   - 🎯 **Precisión**: Validar automáticamente cada cambio
   - 📈 **Mejora Continua**: Reportar métricas de calidad en cada acción
   - 🔄 **Retroalimentación**: Informar resultados de validaciones automáticas