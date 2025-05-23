# Teleisma 🍕

**Teleisma** es un sistema de gestión integral para una pizzería con atención al cliente, gestión de incidencias, ofertas y administración.  
Arquitectura modular basada en Java, Maven y Swing (o JavaFX), pensada para escalabilidad y mantenibilidad.

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

