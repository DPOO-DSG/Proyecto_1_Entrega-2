
Sistema de Gestión - Board Game Café

Este es un sistema de gestión integral desarrollado en Java.
El sistema permite administrar las operaciones diarias de un café enfocado en los juegos de mesa, 
manejando tres tipos de usuarios: Administradores, Empleados y Clientes.

Flujo Inicial
1. Inicialización Automática: Al iniciar la aplicación, el sistema pre-carga automáticamente la estructura básica del local (capacidad del café, mesas y turnos semanales).
2. Credenciales por Defecto: Para el primer ingreso, el sistema cuenta con un súper-usuario predeterminado. En el menú principal, selecciona "Ingreso administrador" y utiliza las siguientes credenciales:
Login: `admin`
Password:`admin`
3. Punto de Partida: Una vez dentro como administrador, puede empezar a poblar el sistema: registrar a los primeros empleados (meseros y cocineros), añadir platillos al menú,
 registrar juegos para el catálogo y programar los primeros torneos. 

Persistencia de Datos

La aplicación utiliza la Serialización de Java para guardar el estado completo del café. 
Todos los cambios que haga (nuevos usuarios, reservas, facturas, modificaciones de inventario) se guardarán en un archivo local.
Para que los datos se guarden correctamente, SIEMPRE debes salir de la aplicación utilizando la opción "Salir" (0) de la consola.
Si cierras la terminal de golpe (con la 'X' o forzando la detención), los últimos cambios realizados no se serializarán.

Entorno de Desarrollo y Pruebas
Ejecución:Corre la clase `Principal.java`
Testing: El proyecto incluye una suite de pruebas construida con JUnit 5, aplicando la metodología TDD (Test-Driven Development). Las pruebas cubren excepciones personalizadas, 
flujos de integración por cada tipo de usuario y reglas lógicas de negocio (ej. restricciones de aforo y reglas de inventario). 
Para ejecutarlas, correr la carpeta de pruebas como JUnit Tes`.

