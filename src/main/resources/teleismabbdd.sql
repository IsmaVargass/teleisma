DROP DATABASE IF EXISTS teleismabbdd;
CREATE DATABASE IF NOT EXISTS teleismabbdd;

USE teleismabbdd;

CREATE TABLE IF NOT EXISTS roles (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL,
role_id INT NOT NULL,
FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS pizzas (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
descripcion VARCHAR(255),
precio DECIMAL(8,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS ofertas (
id INT AUTO_INCREMENT PRIMARY KEY,
descripcion VARCHAR(255) NOT NULL,
descuento VARCHAR(255) NOT NULL, 
pizza_id INT NOT NULL,
FOREIGN KEY (pizza_id) REFERENCES pizzas(id)
);

CREATE TABLE IF NOT EXISTS ventas (
id INT AUTO_INCREMENT PRIMARY KEY,
pizza_id INT NOT NULL,
usuario_id INT NOT NULL,
fecha DATETIME NOT NULL,
cantidad INT NOT NULL,
FOREIGN KEY (pizza_id) REFERENCES pizzas(id),
FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

INSERT INTO roles (nombre) VALUES ('cliente'),('admin'),('gerente');
INSERT INTO usuarios (nombre,email,password,role_id) VALUES
('Soraya','soraya@ejemplo.com','1234',1),
('Admin','admin@ejemplo.com','admin',2),
('Ismael','ismael@ejemplo.com','1234',3);
INSERT INTO pizzas (nombre,descripcion,precio) VALUES
('Margherita','Tomate, mozzarella y albahaca',7.50),
('Pepperoni','Queso y pepperoni',8.20),
('Cuatro Quesos','Mozzarella, gorgonzola, parmesano y emmental',9.00),
('Hawaiana','Jamón, piña y queso',8.75);
INSERT INTO ofertas (descripcion,descuento,pizza_id) VALUES
('10% en Margherita','10%',1),
('2€ menos en Pepperoni',2.00,2);
INSERT INTO ventas (pizza_id,usuario_id,fecha,cantidad) VALUES
(1,1,'2025-05-10 12:30:00',2),
(2,2,'2025-05-11 20:15:00',1),
(3,1,'2025-05-11 21:00:00',1),
(1,2,'2025-05-12 13:00:00',3);

SELECT * FROM ofertas;
SELECT id FROM pizzas;
SELECT * FROM pizzas;

ALTER TABLE ofertas MODIFY descuento VARCHAR(255) NOT NULL;

ALTER TABLE pizzas
  CHANGE COLUMN descripcion ingredientes VARCHAR(255) NOT NULL;
SELECT * FROM pizzas;

ALTER TABLE usuarios
  ADD COLUMN direccion VARCHAR(255),
  ADD COLUMN fecha_nacimiento DATE,
  ADD COLUMN dni VARCHAR(20);

-- Usuarios con dirección, fecha de nacimiento y DNI
INSERT INTO usuarios (nombre, email, password, role_id, direccion, fecha_nacimiento, dni) VALUES
('Lucía Fernández', 'lucia@ejemplo.com', 'clave123', 1, 'Calle Mayor 23, Madrid', '1992-06-15', '12345678A'),
('Carlos García', 'carlos@ejemplo.com', 'pass456', 1, 'Avda. Libertad 10, Valencia', '1985-11-02', '87654321B'),
('Ana López', 'ana@ejemplo.com', 'segura789', 1, 'Paseo del Prado 3, Sevilla', '2000-04-25', '11223344C');

-- Actualizamos los que ya tenemos y añadimos la info.
UPDATE usuarios
SET direccion = 'Calle Luna 5, Málaga',
    fecha_nacimiento = '1990-01-01',
    dni = '44556677D'
WHERE nombre = 'Soraya';

UPDATE usuarios
SET direccion = 'Calle Sol 9, Barcelona',
    fecha_nacimiento = '1980-08-20',
    dni = '99887766E'
WHERE nombre = 'Admin';

UPDATE usuarios
SET direccion = 'Calle Estrella 12, Bilbao',
    fecha_nacimiento = '1975-12-10',
    dni = '33445566F'
WHERE nombre = 'Ismael';

SHOW TABLES;


CREATE TABLE IF NOT EXISTS incidencias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  direccion VARCHAR(255) NOT NULL,
  telefono VARCHAR(20) NOT NULL,
  descripcion TEXT NOT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

INSERT INTO incidencias (usuario_id, direccion, telefono, descripcion) VALUES
(1, 'Calle Luna 5, Málaga', '600123456', 'Cliente reporta que la pizza llegó fría'),
(2, 'Calle Sol 9, Barcelona', '611987654', 'Retraso en la entrega de 30 minutos'),
(3, 'Calle Estrella 12, Bilbao', '622555777', 'Corte de electricidad durante el pedido');

-- Tabla para almacenar las solicitudes de atención al cliente
CREATE TABLE IF NOT EXISTS contacto_clientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  asunto VARCHAR(255) NOT NULL,
  mensaje TEXT NOT NULL,
  telefono VARCHAR(20),
  fecha DATETIME NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
-- Nota: el correo se enviará en tiempo real y no necesita guardarse; esta tabla es opcional si quieres auditar los mensajes.

