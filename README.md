# 🎓 Gestión de Notas Universidad

Aplicación de escritorio desarrollada en Java para gestionar las calificaciones de alumnos de una universidad.

Este proyecto fue realizado como actividad evaluable del módulo **Desarrollo de Interfaces**, del Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM).

## 💻 Tecnologías utilizadas

- Java
- Java Swing
- WindowBuilder
- Eclipse
- MariaDB
- JDBC
- DBeaver

## 📋 Funcionalidades

### 🔐 Inicio de sesión
- Acceso mediante usuario y contraseña.
- Selección del cargo: profesor o alumno.
- Validación de credenciales mediante la base de datos.
- Opción para mostrar u ocultar la contraseña.

### 👨‍🏫 Panel del profesor
- Consulta de los módulos asignados.
- Visualización de alumnos y calificaciones.
- Modificación de notas entre 0 y 10.
- Actualización de las calificaciones en MariaDB.

### 👨‍🎓 Panel del alumno
- Consulta de sus propias calificaciones.
- Visualización de todos los módulos.
- Filtro de notas por asignatura.

## 🗄️ Base de datos

La aplicación utiliza MariaDB para almacenar la información.

Las tablas principales son:

- `usuarios`
- `modulos`
- `matriculas`
- `notas`
- `profesores_modulos`

## 🚀 Ejecución

El proyecto está desarrollado en Eclipse y necesita una base de datos MariaDB configurada para funcionar.

La aplicación se inicia ejecutando la clase `Principal.java`.

## 👨‍💻 Autor

**Julián Ballesteros**

Estudiante de Desarrollo de Aplicaciones Multiplataforma (DAM).
