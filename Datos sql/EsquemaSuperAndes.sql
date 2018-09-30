--- Sentencias SQL para la creación del esquema de SuperAndes
--- Las tablas tienen prefijo A_ para facilitar su acceso desde SQL Developer

-- USO
-- Copie el contenido de este archivo en una pestaña SQL de SQL Developer
-- Ejecútelo como un script - Utilice el botón correspondiente de la pestaña utilizada

-- Creación del secuenciador
create sequence superandes_sequence;

-- Creación de la tabla SUCURSAL y especificación de sus restricciones
CREATE TABLE A_SUCURSAL
   (ID NUMBER,
	NOMBRE VARCHAR2(255 BYTE) NOT NULL,
	CIUDAD VARCHAR2(255 BYTE) NOT NULL,
	DIRECCION VARCHAR2(255 BYTE) NOT NULL,
	SEGMENTOMERCADO VARCHAR2(255 BYTE) NOT NULL,
	TAMANOM2 NUMBER NOT NULL,
	SUPERMERCADO VARCHAR2(255 BYTE) NOT NULL,
	CONSTRAINT A_SUCURSAL_PK PRIMARY KEY (ID));

ALTER TABLE A_SUCURSAL
ADD CONSTRAINT fk_s_supermercado
	FOREIGN KEY (supermercado)
	REFERENCES a_supermercado(nombre));
ENABLE;

ALTER TABLE A_SUCURSAL
	ADD CONSTRAINT CK_S_TAMANO
	CHECK (tamano > 0);
ENABLE;

-- Creación de la tabla supermercado y especificación de sus restricciones.
CREATE TABLE A_SUPERMERCADO
   (NOMBRE VARCHAR2(255 BYTE));
   CONSTRAINT A_SUPERMERCADO_PK PRIMARY KEY (NOMBRE));

-- Creación de la tabla bodega y especificación de sus restricciones.
CREATE TABLE A_BODEGA
   (ID NUMBER,
	CAPACIDADVOLUMEN NUMBER NOT NULL,
	CAPACIDADPESO NUMBER NOT NULL,
	PRODUCTO NUMBER NOT NULL,
	SUCURSAL NUMBER NOT NULL,
	CONSTRAINT A_BODEGA_PK PRIMARY KEY (ID);

ALTER TABLE A_BODEGA
ADD CONSTRAINT fk_b_producto
	FOREIGN KEY (producto)
	REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_BODEGA
ADD CONSTRAINT fk_b_sucursal
	FOREIGN KEY (sucursal)
	REFERENCES a_sucursal(id)
ENABLE;

ALTER TABLE A_BODEGA
	ADD CONSTRAINT CK_B_VOLUMEN
	CHECK (capacidadvolumen > 0)
ENABLE;

ALTER TABLE A_BODEGA
	ADD CONSTRAINT CK_B_PESO
	CHECK (capacidadpeso > 0)
ENABLE;

-- Creación de la tabla estante y especificación de sus restricciones.
CREATE TABLE A_ESTANTE
   (ID NUMBER,
	CAPACIDADVOLUMEN NUMBER NOT NULL,
	CAPACIDADPESO NUMBER NOT NULL,
	PRODUCTO NUMBER NOT NULL,
	SUCURSAL NUMBER NOT NULL,
	NIVELABASTECIMIENTOBODEGA NUMBER NOT NULL,
	EXISTENCIAS NOT NULL, 
	CONSTRAINT A_ESTANTE_PK PRIMARY KEY (ID));

ALTER TABLE A_ESTANTE
ADD CONSTRAINT fk_e_producto
	FOREIGN KEY (producto)
	REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_ESTANTE
ADD CONSTRAINT fk_e_sucursal
	FOREIGN KEY (sucursal)
	REFERENCES a_sucursal(id)
ENABLE;

ALTER TABLE A_ESTANTE
	ADD CONSTRAINT CK_E_VOLUMEN
	CHECK (capacidadvolumen > 0);
ENABLE;

ALTER TABLE A_ESTANTE
	ADD CONSTRAINT CK_E_PESO
	CHECK (capacidadpeso > 0);
ENABLE;

ALTER TABLE A_ESTANTE
	ADD CONSTRAINT CK_E_ABASTECIMIENTO
	CHECK (nivelabastecimientobodega > 0);
ENABLE;
ALTER TABLE A_ESTANTE
	ADD CONSTRAINT CK_E_EXISTENCIAS
	CHECK (existencias > 0);
ENABLE;

-- Creación de la tabla producto y especificación de sus restricciones.
CREATE TABLE A_PRODUCTO
   (ID NUMBER,
	NOMBRE VARCHAR2(255 BYTE) NOT NULL,
	MARCA VARCHAR2(255 BYTE) NOT NULL,
	PRESENTACION VARCHAR2(255 BYTE) NOT NULL,
	CODIGOBARRAS VARCHAR2 (255 BYTE) NOT NULL,
	UNIDADMEDIDA VARCHAR2(255 BYTE) NOT NULL,
	CATEGORIA VARCHAR2(255 BYTE) NOT NULL,
	TIPO VARCHAR2(255 BYTE) NOT NULL,
	CONSTRAINT A_PRODUCTO_PK PRIMARY KEY (ID));

ENABLE;

-- Creación de la tabla vende y especificación de sus restricciones.
CREATE TABLE A_VENDE
   (IDSUCURSAL NUMBER,
	IDPRODUCTO NUMBER NOT NULL,
	NIVELREORDEN NUMBER NOT NULL,
	PRECIOUNITARIO NUMBER NOT NULL,
	PRECIOUNIDADMEDIDA NUMBER NOT NULL,
	CONSTRAINT A_VENDE_PK PRIMARY KEY (IDSUCURSAL, IDPRODUCTO, NIVELREORDEN, PRECIOUNITARIO, PRECIOUNIDADMEDIDA));

ALTER TABLE A_VENDE
ADD CONSTRAINT fk_v_sucursal
	FOREIGN KEY (idsucursal)
	REFERENCES a_sucursal(id)
ENABLE;

ALTER TABLE A_VENDE
ADD CONSTRAINT fk_v_producto
	FOREIGN KEY (idproducto)
	REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_VENDE
	ADD CONSTRAINT CK_V_PRECIOU
	CHECK (preciounitario > 0);
ENABLE;

ALTER TABLE A_VENDE
	ADD CONSTRAINT CK_V_PRECIOM
	CHECK (preciounidadmedida > 0);
ENABLE;

ALTER TABLE A_VENDE
	ADD CONSTRAINT CK_V_NIVEL
	CHECK (nivelreorden > 0);
ENABLE;

-- Creación de la tabla proveedor y especificación de sus restricciones.
CREATE TABLE A_PROVEEDOR
   (NIT NUMBER,
	NOMBRE VARCHAR2(255 BYTE) NOT NULL,
	CALIFICACION NUMBER NOT NULL,
	CONSTRAINT A_PROVEEDOR_PK PRIMARY KEY (NIT));

ALTER TABLE A_PROVEEDOR
	ADD CONSTRAINT CK_P_CALIFICACIONA
	CHECK (calificacion > 0);
ENABLE;

ALTER TABLE A_PROVEEDOR
	ADD CONSTRAINT CK_P_CALIFICACIONB
	CHECK (calificacion < 11);
ENABLE;

-- Creación de la tabla pedido y especificación de sus restricciones.
CREATE TABLE A_PEDIDO
   (ID NUMBER,
	IDSUCURSAL NUMBER NOT NULL,
	IDPROVEEDOR NUMBER NOT NULL, 
	FECHAENTREGA DATE NOT NULL,
	ESTADOORDEN VARCHAR2(255 BYTE),
	CALIFICACIONSERVICIO NUMBER,
	COSTOTOTAL NUMBER NOT NULL,
	CONSTRAINT A_PEDIDO_PK PRIMARY KEY (ID));

ALTER TABLE A_PEDIDO
ADD CONSTRAINT fk_ped_sucursal
	FOREIGN KEY (idsucursal)
	REFERENCES a_sucursal(id);
ENABLE;

ALTER TABLE A_PEDIDO
ADD CONSTRAINT fk_ped_proveedor
	FOREIGN KEY (idproveedor)
	REFERECNES a_proveedor(nit);
ENABLE;

ALTER TABLE A_PEDIDO
	ADD CONSTRAINT CK_PED_CALIFICACION
	CHECK (calificacionservicio > 0);
ENABLE;

ALTER TABLE A_PEDIDO
	ADD CONSTRAINT CK_PED_COSTO
	CHECK (costototal > 0);
ENABLE;

ALTER TABLE A_PEDIDO
ADD CONSTRAINT CK_PED_ESTADO
	CHECK (estadoorden IN ('pendiente', 'entregado'))
ENABLE;

-- Creación de la tabla ofrecen y especificación de sus restricciones.
CREATE TABLE A_OFRECEN
   (IDPRODUCTO NUMBER,
	IDPROVEEDOR NUMBER NOT NULL,
	COSTO NUMBER NOT NULL,
	CONSTRAINT A_OFRECE_PK PRIMARY KEY (IDPRODUCTO, IDPROVEEDOR, COSTO));
 
ALTER TABLE A_OFRECEN
ADD CONSTRAINT fk_o_producto
	FOREIGN KEY (idproducto)
	REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_OFRECEN
ADD CONSTRAINT fk_o_proveedor
	FOREIGN KEY (idproveedor)
	REFERENCES a_sucursal(nit)
ENABLE;

ALTER TABLE A_OFRECEN
	ADD CONSTRAINT CK_O_COSTO
	CHECK (costo> 0);
ENABLE;

-- Creación de la tabla factura y especificación de sus restricciones.
CREATE TABLE A_FACTURA
   (NUMERO NUMBER,
	FECHA DATE NOT NULL,
	IDCLIENTE VARCHAR2(255 BYTE) NOT NULL,
	SUCURSAL NUMBER NOT NULL, 
	CONSTRAINT A_FACTURA_PK PRIMARY KEY (NUMERO));

ALTER TABLE A_FACTURA
ADD CONSTRAINT fk_factura_cliente
	FOREIGN KEY (idcliente)
	REFERENCES a_cliente(id)
ENABLE;


ALTER TABLE A_FACTURA
ADD CONSTRAINT fk_factura_sucursal
	FOREIGN KEY (sucursal)
	REFERENCES a_sucursal(id)
ENABLE;


-- Creación de la tabla transaccion y especificación de sus restricciones.
CREATE TABLE A_TRANSACCION
   (IDPRODUCTO NUMBER NOT NULL,
	CANTIDAD NUMBER NOT NULL,
	NUMEROFACTURA NUMBER NOT NULL,
	COSTO NUMBER NOT NULL,
	PROMOCION NUMBER,
	CONSTRAINT A_TRANSACCION_PK PRIMARY KEY (IDPRODUCTO, CANTIDAD, NUMEROFACTURA));

ALTER TABLE A_TRANSACCION
ADD CONSTRAINT fk_t_producto
	FOREIGN KEY (idproducto)
	REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_TRANSACCION
ADD CONSTRAINT fk_tr_factura
	FOREIGN KEY (numerofactura)
	REFERENCES a_factura(numero)
ENABLE;

ALTER TABLE A_TRANSACCION
	ADD CONSTRAINT CK_TR_CANTIDAD
	CHECK (cantidad> 0);
ENABLE;

ALTER TABLE A_TRANSACCION
	ADD CONSTRAINT CK_TR_COSTO
	CHECK (costo> 0);
ENABLE;

ALTER TABLE A_TRANSACCION
ADD CONSTRAINT fk_tr_factura
	FOREIGN KEY (promocion)
	REFERENCES a_promocion(id)
ENABLE;


-- Creación de la tabla promoción y especificación de sus restricciones.
CREATE TABLE A_PROMOCION
   (ID NUMBER,
	IDPRODUCTO NUMBER NOT NULL, 
	PRECIO NUMBER NOT NULL,
	DESCRIPCION VARCHAR2(255 BYTE) NOT NULL,
	FECHAINICIO DATE NOT NULL,
	FECHAFIN DATE NOT NULL, 
	UNIDADESDISPONIBLES NUMBER NOT NULL,
	CONSTRAINT A_PROMOCION_PK PRIMARY KEY (ID));

ALTER TABLE A_PROMOCION
	ADD CONSTRAINT CK_PROM_PRECIO
	CHECK (precio> 0);
ENABLE;

ALTER TABLE A_PROMOCION
ADD CONSTRAINT fk_prom_producto
    FOREIGN KEY (idproducto)
    REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_PROMOCION
	ADD CONSTRAINT CK_PROM_UNIDADES
	CHECK (unidadesdisponibles> -1);
ENABLE;

-- Creación de la tabla cliente y especificación de sus restricciones.
CREATE TABLE A_CLIENTE
   (ID VARCHAR2(255 BYTE),
	
	NOMBRE VARCHAR2(255 BYTE) NOT NULL,
	
	CORREO VARCHAR2(255 BYTE) NOT NULL,
	DIRECCION VARCHAR2(255 BYTE),
	TIPO VARCHAR2(255 BYTE) NOT NULL,
	CONSTRAINT A_CLIENTE_PK PRIMARY KEY (ID));

ALTER TABLE A_CLIENTE
	ADD CONSTRAINT CK_CLIENTE_TIPO
	CHECK (tipo IN ('persona', 'empresa');
ENABLE;

-- Creación de la tabla subpedido y especificación de sus restricciones.
CREATE TABLE A_SUBPEDIDO
   (IDPEDIDO NUMBER NOT NULL,
	IDPRODUCTO NUMBER NOT NULL,
	CANTIDAD NUMBER NOT NULL,
	COSTO NUMBER NOT NULL,
	CONSTRAINT A_PEDIDO_PK PRIMARY KEY (IDPEDIDO, IDPRODUCTO, CANTIDAD, COSTO));

ALTER TABLE A_SUBPEDIDO
ADD CONSTRAINT fk_sub_producto
	FOREIGN KEY (idproducto)
	REFERENCES a_producto(id)
ENABLE;

ALTER TABLE A_SUBPEDIDO
ADD CONSTRAINT fk_sub_pedido
	FOREIGN KEY (idpedido)
	REFERENCES a_pedido(id)
ENABLE;

ALTER TABLE A_SUBPEDIDO
	ADD CONSTRAINT CK_SUB_COSTO
	CHECK (costo > 0);
ENABLE;

ALTER TABLE A_SUBPEDIDO
	ADD CONSTRAINT CK_SUB_CANTIDAD
	CHECK (cantidad > 0);
ENABLE;

COMMIT;
  	
	
	
	
	