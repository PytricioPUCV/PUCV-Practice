CREATE TABLE Cliente
(
  Rut VARCHAR NOT NULL,
  NombreEmpresa VARCHAR NOT NULL,
  PersonaContacto VARCHAR NOT NULL,
  CorreoElectronico VARCHAR NOT NULL,
  Telefono VARCHAR NOT NULL,
  Domicilio VARCHAR NOT NULL,
  Giro VARCHAR NOT NULL,
  PRIMARY KEY (Rut)
);

CREATE TABLE JefeProyecto
(
  IdJefe INT NOT NULL,
  Nombre VARCHAR NOT NULL,
  Telefono VARCHAR NOT NULL,
  PRIMARY KEY (IdJefe)
);

CREATE TABLE Ingeniero
(
  Rut VARCHAR NOT NULL,
  Nombre VARCHAR NOT NULL,
  Domicilio VARCHAR NOT NULL,
  Telefono VARCHAR NOT NULL,
  Sueldo INT NOT NULL,
  Banco VARCHAR NOT NULL,
  NumeroCuenta VARCHAR NOT NULL,
  PRIMARY KEY (Rut)
);

CREATE TABLE DetalleSueldo
(
  SueldoBruto INT NOT NULL,
  SueldoLiquido INT NOT NULL,
  NombreProyecto VARCHAR NOT NULL,
  Empresa VARCHAR NOT NULL,
  HorasTrabajadas INT NOT NULL,
  IdDetalle INT NOT NULL,
  Rut VARCHAR NOT NULL,
  PRIMARY KEY (IdDetalle),
  FOREIGN KEY (Rut) REFERENCES Ingeniero(Rut)
);

CREATE TABLE Proyecto
(
  Codigo INT NOT NULL,
  Descripcion VARCHAR NOT NULL,
  Valor INT NOT NULL,
  MesesDuracion INT NOT NULL,
  FechaInicio DATE NOT NULL,
  FechaFin DATE NOT NULL,
  Nombre VARCHAR NOT NULL,
  Rut VARCHAR NOT NULL,
  IdJefe INT NOT NULL,
  PRIMARY KEY (Codigo),
  FOREIGN KEY (Rut) REFERENCES Cliente(Rut),
  FOREIGN KEY (IdJefe) REFERENCES JefeProyecto(IdJefe)
);

CREATE TABLE ParticipacionProyecto
(
  IdParticipacion INT NOT NULL,
  HorasTrabajo INT NOT NULL,
  Codigo INT NOT NULL,
  Rut VARCHAR NOT NULL,
  PRIMARY KEY (IdParticipacion),
  FOREIGN KEY (Codigo) REFERENCES Proyecto(Codigo),
  FOREIGN KEY (Rut) REFERENCES Ingeniero(Rut)
);