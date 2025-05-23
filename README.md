# Documentación de la aplicación - Teleisma 🍕

**Teleisma** es un sistema de gestión integral para una pizzería con atención al cliente, gestión de incidencias, ofertas y administración.  
Arquitectura modular basada en Java, Maven y Swing (o JavaFX), pensada para escalabilidad y mantenibilidad.

[![Stars](https://img.shields.io/github/stars/IsmaVargass/teleisma?style=social&label=Stars)](https://github.com/IsmaVargass/teleisma/stargazers)

[![Forks](https://img.shields.io/github/forks/IsmaVargass/teleisma?style=social&label=Forks)](https://github.com/IsmaVargass/teleisma/network/members)

[![Issues](https://img.shields.io/github/issues/IsmaVargass/teleisma)](https://github.com/IsmaVargass/teleisma/issues)

[![Last Commit](https://img.shields.io/github/last-commit/IsmaVargass/teleisma.svg)](https://github.com/IsmaVargass/teleisma/commits/master/)

[![Repo Size](https://img.shields.io/github/repo-size/IsmaVargass/teleisma)](https://github.com/IsmaVargass/teleisma)

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

[![Made with Java](https://img.shields.io/badge/Made%20with-Java-orange.svg)](https://www.java.com/)

---

## 🚀 Características

- **Gestión de usuarios**: Login, roles (admin, gerente, atención).  
- **Módulo de atención al cliente**: Registro y seguimiento de contactos.  
- **Gestión de incidencias**: Crear, asignar y cerrar tickets.  
- **Ofertas y pizzas**: CRUD completo para catálogo de pizzas y promociones.  
- **Interfaz GUI**: Paneles dedicados por rol, con tablas, formularios y validaciones.  
- **Persistencia**: Conexión a base de datos relacional via JDBC (DBUtil).  
- **Tests automatizados**: Cobertura de lógica de negocio y utilidades.

---

## 📦 Estructura del proyecto

```text
teleisma/
├─ .idea/                     # Configuraciones de IntelliJ IDEA
├─ src/
│  ├─ main/
│  │  ├─ java/com/proyecto/teleisma/
│  │  │   ├─ db/              # Conexión y utilidades JDBC (DBUtil.java)
│  │  │   ├─ entidades/       # Clases de dominio (POJOs: Usuario, Pizza, Oferta, Incidencia, ContactoCliente)
│  │  │   ├─ servicios/
│  │  │   │   ├─ api/         # Interfaces de servicios (IUsuarioService, IPizzaService, etc.)
│  │  │   │   ├─ impl/        # Implementaciones de servicios (UsuarioServiceImpl, PizzaServiceImpl, etc.)
│  │  │   │   └─ gui/         # Clases de interfaz gráfica (LoginFrame, MainFrame, paneles por módulo)
│  │  │   └─ App.java         # Punto de entrada `public static void main`
│  │  └─ resources/
│  │      ├─ images/          # Logos e iconos usados en la GUI
│  │      └─ teleismabbdd.sql # Script DDL/DML para crear y poblar la BD
│  └─ test/                   # Pruebas unitarias (JUnit/TestNG)
├─ target/                    # Artefactos de compilación (JAR, clases)
├─ .gitignore                 # Ignorar target/, IDE, credenciales, logs
└─ pom.xml                    # Configuración Maven: dependencias y plugins
```
---

## ⚙️ Requisitos

- **Java 11+**  
- **Maven 3.6+**  
- *(Opcional)* IDE IntelliJ IDEA o Eclipse  

---

## 📤 2. Análisis

### ⚙️ 2.1 Requisitos funcionales

1. **Autenticación**: login de usuarios  
2. **Gestión de pizzas**: CRUD de pizzas  
3. **Gestión de ofertas**: CRUD de ofertas  
4. **Gestión de incidencias**: CRUD de incidencias  
5. **Atención al cliente**: enviar y visualizar historial de mensajes  
6. **Panel de gerente y administrador**: vistas específicas según rol  

### ⚙️ 2.2 Requisitos no funcionales

- **Rendimiento**: < 200 ms en operaciones CRUD  
- **Escalabilidad**: arquitectura modular extendible  
- **Mantenibilidad**: código documentado y organizado por paquetes  
- **Seguridad**: contraseñas cifradas, validaciones frontend/back  
- **Usabilidad**: interfaz clara y accesible en Swing  

---

## 📂 Documentación

Dentro del directorio `docs/` encontrarás los diagramas generados para el proyecto:

- [`Casos de Usos`](docs/Casos_de_Usos.png)  
  Diagrama de casos de uso que muestra las interacciones entre actores y funcionalidades.

- [`Diagrama Entidad Relación`](docs/Diagrama_Entidad_Relación.png)  
  Diagrama ER con las entidades principales y sus relaciones.

- [`Diagrama de clases`](docs/Diagrama_de_clases.png)  
  Diagrama de clases que refleja la estructura de los paquetes y la herencia/implementación de interfaces.

- [`Teleisma Mockup`](docs/Teleisma-Mockup.pdf)  
  Mockups de la interfaz de usuario y flujo de pantallas.

---

## 🙌 Recursos y Agradecimientos

Durante el desarrollo de este proyecto, me he apoyado en las siguientes fuentes de conocimiento y herramientas:

#### 🤖 ChatGPT
- [ChatGPT de OpenAI](https://chat.openai.com): Asistencia en la organización de clases, patrones de diseño y resolución de dudas sobre algoritmos y pruebas.

#### 📘 Documentación oficial de Java
- [Collections](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html)
- [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Manejo de excepciones en Java](https://docs.oracle.com/javase/tutorial/essential/exceptions/)

#### 💬 Stack Overflow
- [https://stackoverflow.com](https://stackoverflow.com): Consulta de errores comunes y mejores prácticas en Java.


