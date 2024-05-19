# Procesamiento

## Descripción
Esta aplicación se conecta a un servidor para realizar peticiones desde Android Studio y manejar una base de datos, permitiendo realizar diversas operaciones como consulta, modificación, y eliminación de registros. Consta de una pantalla de login que servira tambien de registro en modo "user" si se introduce un email no existente.

## Funcionalidades
Lista las principales características y funcionalidades de la aplicación, como:
- Consulta de datos.
- Modificación de registros.
- Eliminación de registros.
- Búsqueda de información específica.
- Paginación y filtrado en consultas.

## Tecnologías Utilizadas
- Android Studio
- Lenguaje de programación Java
- Base de datos MySQL
- Bibliotecas como Retrofit para peticiones HTTP.

## Instalación
1. Clonar el repositorio: `git clone https://github.com/EEP-202324/dam_proyecto_android_ada_psp-Ruben3242.git`
2. Instalar dependencias necesarias.
3. Configuración adicional si es necesaria.

## Uso
- Realizar una consulta básica, tanto de un id como de una lista.
- Modificar un registro existente o eliminarlo.
- Cómo utilizar la paginación en la aplicación.

## Problemas y Soluciones
Durante el desarrollo de la aplicación, enfrenté varios desafíos, incluyendo:
- **Implementación del método PUT desde Android:** Configurar correctamente los headers y el cuerpo de la petición para asegurar la compatibilidad con el backend.
- **Manejo de la paginación en Android:** Implementar una lógica efectiva para cargar datos de manera eficiente sin sobrecargar la memoria del dispositivo.

## Logros
Este proyecto cumple con todos los criterios establecidos en las rúbricas de ADA, PSP, y Android, incluyendo:
- Gestión de base de datos MySQL.
- Consultas avanzadas y manejo de errores.
- Implementación de pruebas unitarias efectivas para cada funcionalidad.
- Lanazamiento automatico del servidor para igresar el owner.
- Cargar la webs de los centros añadidos.

## Video Demostrativo
Incluye aquí el enlace al video de Stream que muestra la demostración del funcionamiento de la aplicación y la explicación del código:
- [Ver Video Demostrativo](https://eepmad-my.sharepoint.com/:v:/g/personal/ruben-lopez1_eep-igroup_com/EWIxBCM2IeFEpmfPIgxesroBRtjkdo-L3TyAXuxJsNeYmA?e=jOhNuu&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D)

## Instrucciones Adicionales
Si se desea usar y probar todas las funcionalidades se debera tener en cuenta:
- Clonar el repositorio
- Crear la base de datos con el nombre de "gestion_ifema"
- Levantar el servidor antes de realizar pruebas

## Autores
- Rubén López

## Agradecimientos
- https://stackoverflow.com/
- https://chat.openai.com/


