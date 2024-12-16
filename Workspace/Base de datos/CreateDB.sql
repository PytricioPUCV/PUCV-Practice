CREATE TABLE Vendedor
(
  Apellido VARCHAR NOT NULL,
  Telefono INT NOT NULL,
  Id_Vendedor INT NOT NULL,
  Nombre VARCHAR NOT NULL,
  PRIMARY KEY (Id_Vendedor)
);

CREATE TABLE Servicio_Tecnico
(
  Id_Orden INT NOT NULL,
  Fecha DATE NOT NULL,
  Descripcion VARCHAR NOT NULL,
  Precio INT NOT NULL,
  PRIMARY KEY (Id_Orden)
);

CREATE TABLE Mecanicos
(
  Id_Mecanico INT NOT NULL,
  Nombre VARCHAR NOT NULL,
  Apellido VARCHAR NOT NULL,
  Especialidad VARCHAR NOT NULL,
  PRIMARY KEY (Id_Mecanico)
);

CREATE TABLE AutoAReparar
(
  Marca VARCHAR NOT NULL,
  Id_Vehiculo INT NOT NULL,
  Modelo VARCHAR NOT NULL,
  Año INT NOT NULL,
  Id_Orden INT NOT NULL,
  PRIMARY KEY (Id_Vehiculo),
  FOREIGN KEY (Id_Orden) REFERENCES Servicio_Tecnico(Id_Orden)
);

CREATE TABLE Orden_Mecanico
(
  Id_Orden INT NOT NULL,
  Id_Mecanico INT NOT NULL,
  PRIMARY KEY (Id_Orden, Id_Mecanico),
  FOREIGN KEY (Id_Orden) REFERENCES Servicio_Tecnico(Id_Orden),
  FOREIGN KEY (Id_Mecanico) REFERENCES Mecanicos(Id_Mecanico)
);

CREATE TABLE Cliente
(
  RUT INT NOT NULL,
  Nombre VARCHAR NOT NULL,
  Apellido VARCHAR NOT NULL,
  Direccion VARCHAR NOT NULL,
  Telefono INT NOT NULL,
  PRIMARY KEY (RUT)
);

CREATE TABLE Cliente_Vehiculo
(
  RUT INT NOT NULL,
  Id_Vehiculo INT NOT NULL,
  PRIMARY KEY (RUT, Id_Vehiculo),
  FOREIGN KEY (RUT) REFERENCES Cliente(RUT),
  FOREIGN KEY (Id_Vehiculo) REFERENCES AutoAReparar(Id_Vehiculo)
);

CREATE TABLE Factura
(
  Id_Factura INT NOT NULL,
  Fecha DATE NOT NULL,
  Monto_Total INT NOT NULL,
  RUT INT NOT NULL,
  Id_Vendedor INT NOT NULL,
  PRIMARY KEY (Id_Factura),
  FOREIGN KEY (RUT) REFERENCES Cliente(RUT),
  FOREIGN KEY (Id_Vendedor) REFERENCES Vendedor(Id_Vendedor)
);

CREATE TABLE AutoAVender
(
  Id_Vehiculo INT NOT NULL,
  Marca VARCHAR NOT NULL,
  Modelo VARCHAR NOT NULL,
  Año INT NOT NULL,
  Estado VARCHAR NOT NULL,
  Id_Factura INT NOT NULL,
  PRIMARY KEY (Id_Vehiculo),
  FOREIGN KEY (Id_Factura) REFERENCES Factura(Id_Factura)
);