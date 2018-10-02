package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.negocio.Bodega;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Estante;
import uniandes.isis2304.superandes.negocio.Ofrecen;
import uniandes.isis2304.superandes.negocio.Pedido;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.Promocion;
import uniandes.isis2304.superandes.negocio.Proveedor;
import uniandes.isis2304.superandes.negocio.Subpedido;
import uniandes.isis2304.superandes.negocio.Sucursal;
import uniandes.isis2304.superandes.negocio.Supermercado;

public class PersistenciaSuperAndes {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaSuperAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaSuperAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, supermercado, sucursal, producto, bodega, estante, vende, proveedor, pedido, subpedido,
	 * ofrecen, cliente, factura, promocion, transaccion.
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaSuperAndes
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla SUPERMERCADO de la base de datos
	 */
	private SQLSupermercado sqlSupermercado;
	
	/**
	 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
	 */
	private SQLSucursal sqlSucursal;
	
	/**
	 * Atributo para el acceso a la tabla PRODUCTO de la base de datos
	 */
	private SQLProducto sqlProducto;
	
	/**
	 * Atributo para el acceso a la tabla BODEGA de la base de datos
	 */
	private SQLBodega sqlBodega;
	
	/**
	 * Atributo para el acceso a la tabla ESTANTE de la base de datos
	 */
	private SQLEstante sqlEstante;
	
	/**
	 * Atributo para el acceso a la tabla VENDE de la base de datos
	 */
	private SQLVende sqlVende;
	
	/**
	 * Atributo para el acceso a la tabla PROVEEDOR de la base de datos
	 */
	private SQLProveedor sqlProveedor;
	
	/**
	 * Atributo para el acceso a la tabla PEDIDO de la base de datos
	 */
	private SQLPedido sqlPedido;
	
	/**
	 * Atributo para el acceso a la tabla SUBPEDIDO de la base de datos
	 */
	private SQLSubpedido sqlSubPedido;
	
	/**
	 * Atributo para el acceso a la tabla OFRECEN de la base de datos
	 */
	private SQLOfrecen sqlOfrecen;
	
	/**
	 * Atributo para el acceso a la tabla CLIENTE de la base de datos
	 */
	private SQLCliente sqlCliente;
	
	/**
	 * Atributo para el acceso a la tabla FACTURA de la base de datos
	 */
	private SQLFactura sqlFactura;
	
	/**
	 * Atributo para el acceso a la tabla PROMOCION de la base de datos
	 */
	private SQLPromocion sqlPromocion;
	
	/**
	 * Atributo para el acceso a la tabla TRANSACCION de la base de datos
	 */
	private SQLTransaccion sqlTransaccion;
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaSuperAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("SuperAndes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("superandes_sequence");
		tablas.add ("SUPERMERCADO");
		tablas.add ("SUCURSAL");
		tablas.add ("PRODUCTO");
		tablas.add ("BODEGA");
		tablas.add ("ESTANTE");
		tablas.add ("VENDE");
		tablas.add ("PROVEEDOR");
		tablas.add ("PEDIDO");
		tablas.add ("SUBPEDIDO");
		tablas.add ("OFRECEN");
		tablas.add ("CLIENTE");
		tablas.add ("FACTURA");
		tablas.add ("PROMOCION");
		tablas.add ("TRANSACCION");
}
	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaSuperAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaSuperAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlSupermercado = new SQLSupermercado(this);
		sqlSucursal = new SQLSucursal(this);
		sqlProducto = new SQLProducto(this);
		sqlBodega = new SQLBodega(this);
		sqlEstante = new SQLEstante(this);
		sqlVende = new SQLVende(this);
		sqlProveedor = new SQLProveedor(this);
		sqlPedido = new SQLPedido(this);
		sqlSubPedido = new SQLSubpedido(this);
		sqlOfrecen = new SQLOfrecen(this);
		sqlCliente = new SQLCliente(this);
		sqlFactura = new SQLFactura(this);
		sqlPromocion = new SQLPromocion(this);
		sqlTransaccion = new SQLTransaccion(this);
		sqlUtil = new SQLUtil(this);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de superandes
	 */
	public String darSeqSuperandes ()
	{
		return tablas.get (0);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Supermercado de superandes
	 */
	public String darTablaSupermercado()
	{
		return tablas.get (1);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sucursal de superandes
	 */
	public String darTablaSucursal()
	{
		return tablas.get (2);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Producto de superandes
	 */
	public String darTablaProducto()
	{
		return tablas.get (3);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bodega de superandes
	 */
	public String darTablaBodega()
	{
		return tablas.get (4);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Estante de superandes
	 */
	public String darTablaEstante()
	{
		return tablas.get (5);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Vende de superandes
	 */
	public String darTablaVende()
	{
		return tablas.get (6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Proveedor de superandes
	 */
	public String darTablaProveedor()
	{
		return tablas.get (7);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Pedido de superandes
	 */
	public String darTablaPedido()
	{
		return tablas.get (8);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Subpedido de superandes
	 */
	public String darTablaSubpedido()
	{
		return tablas.get (9);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Ofrecen de superandes
	 */
	public String darTablaOfrecen()
	{
		return tablas.get (10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cliente de superandes
	 */
	public String darTablaCliente()
	{
		return tablas.get (11);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Factura de superandes
	 */
	public String darTablaFactura()
	{
		return tablas.get (12);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Promocion de superandes
	 */
	public String darTablaPromocion()
	{
		return tablas.get (13);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Transaccion de superandes
	 */
	public String darTablaTransaccion()
	{
		return tablas.get (14);
	}
	
	/**
	 * Transacción para el generador de secuencia de SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de SuperAndes
	 */
	private long nextval()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Transacción para el generador de secuencia de SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de SuperAndes
	 */
	private String nextval2()
	{
        String resp = sqlUtil.nextval2 (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle espec�fico del problema encontrado
	 * @param e - La excepci�n que ocurrio
	 * @return El mensaje de la excepci�n JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los SUPERMERCADOS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SUPERMERCADO
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del supermercado
	 * @return El objeto Supermercado adicionado. null si ocurre alguna Excepci�n
	 */
	public Supermercado adicionarSupermercado(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlSupermercado.adicionarSupermercado(pm, nombre);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Supermercado(nombre);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Supermercado, dado el nombre del supermercado
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del supermercados
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSupermercadoPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlSupermercado.eliminarSupermercadoPorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Supermercado con un nombre dado
	 * @param nombre - El nombre del supermercado
	 * @return El objeto Supermercado, construido con base en las tuplas de la tabla SUPERMERCADO con el identificador de nombre dado
	 */
	public Supermercado darSupermercadoPorNombre (String nombre)
	{
		return sqlSupermercado.darSupermercadoPorNombre(pmf.getPersistenceManager(), nombre);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Supermercado
	 * @return La lista de objetos Supermercado, construidos con base en las tuplas de la tabla SUPERMERCADO
	 */
	public List<Supermercado> darSupermercados()
	{
		return sqlSupermercado.darSupermercados(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las SUCURSALES
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SUCURSAL
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la sucursal
	 * @param ciudad - Ciudad de la sucursal
	 * @param direccion - Direccion de la sucursal
	 * @param segmentomercado - Segmento de mercado de la sucursal
	 * @param tamano - Tamaño de la sucursal (en metros cuadrados)
	 * @param supermercado - El supermercado al que pertenece la sucursal
	 * @return El objeto Sucursal adicionado. null si ocurre alguna Excepci�n
	 */
	public Sucursal adicionarSucursal(String nombre, String ciudad, String direccion, String segmentomercado, int tamano, String supermercado)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idSucursal = nextval();
            long tuplasInsertadas = sqlSucursal.adicionarSucursal(pm, idSucursal, nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
            tx.commit();
            
            log.trace ("Inserción de sucursal: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Sucursal(idSucursal, nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Sucursal, dado el identificador de la sucursal
	 * Adiciona entradas al log de la aplicación
	 * @param idSucursal - El identificador de la sucursal
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSucursalPorId (long idSucursal) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlSucursal.eliminarSucursalPorId(pm, idSucursal);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Sucursal, dado el nombre de la sucursal
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la sucursal
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSucursalPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlSucursal.eliminarSucursalesPorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Sucursal con un identificador dado
	 * @param idSucursal - El identificador de la sucursal
	 * @return El objeto Sucursal, construido con base en las tuplas de la tabla SUCURSAL con el identificador dado
	 */
	public Sucursal darSucursalPorId(long idSucursal)
	{
		return sqlSucursal.darSucursalPorId(pmf.getPersistenceManager(), idSucursal);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Sucursal que tienen un supermercado dado
	 * @param supermercado - El supermercado al que pertenece la sucursal
	 * @return La lista de objetos Sucursal, construidos con base en las tuplas de la tabla SUCURSAL
	 */
	public List<Sucursal> darSucursalesPorSupermercado(String supermercado)
	{
		return sqlSucursal.darSucursalesPorSupermercado(pmf.getPersistenceManager(), supermercado);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Sucursal
	 * @return La lista de objetos Sucursal, construidos con base en las tuplas de la tabla SUCURSAL
	 */
	public List<Sucursal> darSucursales()
	{
		return sqlSucursal.darSucursales(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PRODUCTOS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla PRODUCTO
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto
	 * @param marca - Marca del producto
	 * @param presentacion - Presentacion del producto
	 * @param codigobarras - Código de barras del producto
	 * @param unidadmedida - Las unidades de medida del producto
	 * @param categoria - La categoria del producto (perecederos, no perecederos, aseo, abarrotes, etc)
	 * @param tipo - El tipo de producto por categoria
	 * @return El objeto Producto adicionado. null si ocurre alguna Excepci�n
	 */
	public Producto adicionarProducto(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idProducto = nextval ();
            long tuplasInsertadas = sqlProducto.adicionarProducto(pm, idProducto, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
            tx.commit();
            
            log.trace ("Inserci�n de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Producto(idProducto, nombre, marca, presentacion, unidadmedida, categoria, tipo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Producto, dado el nombre del producto
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarProductoPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProducto.eliminarProductoPorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Producto, dado el identificador del producto
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El identificador del producto
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarProductoPorId (long idProducto) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProducto.eliminarProductoPorId(pm, idProducto);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Producto que tienen un nombre dado
	 * @param nombre - El nombre del producto
	 * @return La lista de objetos Producto, construidos con base en las tuplas de la tabla PRODUCTO
	 */
	public List<Producto> darProductosPorNombre(String nombre)
	{
		return sqlProducto.darProductosPorNombre(pmf.getPersistenceManager(), nombre);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Producto
	 * @return La lista de objetos Producto, construidos con base en las tuplas de la tabla PRODUCTO
	 */
	public List<Producto> darProductos()
	{
		return sqlProducto.darProductos(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las BODEGAS
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BODEGAs
	 * Adiciona entradas al log de la aplicación
	 * @param capacidadVolumen - La capacidad en volumen de la bodega (metros cúbicos)
	 * @param capacidadPeso - La capacidad en peso de la bodega (en kg)
	 * @param producto - Identificador del producto que almacena la bodega
	 * @param sucursal - La sucursal a la que pertenece la bodega
	 * @param existencias - Las existencias disponibles en la bodega
	 * @return El objeto Bodega adicionado. null si ocurre alguna Excepción
	 */
	public Bodega adicionarBodega(double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int existencias)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idBodega = nextval ();
            long tuplasInsertadas = sqlBodega.adicionarBodega(pm, idBodega, capacidadVolumen, capacidadPeso, producto, sucursal, existencias);
            tx.commit();
            
            log.trace ("Inserción de la bodega: " + idBodega + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Bodega(idBodega, capacidadVolumen, capacidadPeso, existencias, producto, sucursal);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bodega, dado el identificador de la bodega
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El identificador de la bodega
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBodegaPorId (long idBodega) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBodega.eliminarBodegaPorId(pm, idBodega);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Bodega con un identificador dado
	 * @param idBodega - El identificador de la bodega
	 * @return El objeto Bodega, construido con base en las tuplas de la tabla BODEGA con el identificador dado
	 */
	public Bodega darBodegaPorId(long idBodega)
	{
		return sqlBodega.darBodegaPorId(pmf.getPersistenceManager(), idBodega);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Bodega que tienen una sucursal dada
	 * @param sucursal - La sucursal a la que pertenece la bodega
	 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla BODEGA
	 */
	public List<Bodega> darBodegasPorSucursal(long sucursal)
	{
		return sqlBodega.darBodegasPorSucursal(pmf.getPersistenceManager(), sucursal);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Bodega
	 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla BODEGA
	 */
	public List<Bodega> darBodegas()
	{
		return sqlBodega.darBodegas(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que aumenta las existencias en 10 unidades de una bodega con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasBodegaEnDiez(long idBodega)
	{
		return sqlBodega.aumentarExistenciasBodegasEnDiez(pmf.getPersistenceManager(), idBodega);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ESTANTES
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ESTANTE
	 * Adiciona entradas al log de la aplicación
	 * @param capacidadVolumen - La capacidad en volumen del estante(metros cúbicos)
	 * @param capacidadPeso - La capacidad en peso del estante (en kg)
	 * @param producto - Identificador del producto que almacena el estante
	 * @param sucursal - La sucursal a la que pertenece el estante
	 * @param nivelabastecimientobodega - Cantidad de unidades mínimas que debe tener en la bodega por producto
	 * @param existencias - Las existencias disponibles en la bodega
	 * @return El objeto Estante adicionado. null si ocurre alguna Excepción
	 */
	public Estante adicionarEstante(double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idEstante = nextval ();
            long tuplasInsertadas = sqlEstante.adicionarEstante(pm, idEstante, capacidadVolumen, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias);
            tx.commit();
            
            log.trace ("Inserción del estante: " + idEstante + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Estante(idEstante, capacidadVolumen, capacidadPeso, existencias, producto, sucursal, nivelabastecimientobodega);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Estante, dado el identificador del estante
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El identificador del estante
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarEstantePorId (long idEstante) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlEstante.eliminarEstantePorId(pm, idEstante);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Estante con un identificador dado
	 * @param idEstante - El identificador del estante
	 * @return El objeto Estante, construido con base en las tuplas de la tabla ESTANTE con el identificador dado
	 */
	public Estante darEstantePorId(long idEstante)
	{
		return sqlEstante.darEstantePorId(pmf.getPersistenceManager(), idEstante);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Estante que tienen una sucursal dada
	 * @param sucursal - La sucursal a la que pertenece al estante
	 * @return La lista de objetos Estante, construidos con base en las tuplas de la tabla ESTANTE
	 */
	public List<Estante> darEstantesPorSucursal(long sucursal)
	{
		return sqlEstante.darEstantesPorSucursal(pmf.getPersistenceManager(), sucursal);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Estante
	 * @return La lista de objetos Estante, construidos con base en las tuplas de la tabla ESTANTEs
	 */
	public List<Estante> darEstantes()
	{
		return sqlEstante.darEstantes(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que aumenta las existencias en 10 unidades de un estante con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasEstanteEnDiez(long idEstante)
	{
		return sqlEstante.aumentarExistenciasEstantesEnDiez(pmf.getPersistenceManager(), idEstante);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación VENDE
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROVEEDORES
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla PROVEEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificacion que tiene el proveedor
	 * @return El objeto Proveedor adicionado. null si ocurre alguna Excepción
	 */
	public Proveedor adicionarProveedor(String nombre, int calificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idProveedor = nextval ();
            long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, idProveedor, nombre, calificacion);
            tx.commit();
            
            log.trace ("Inserción del proveedor: " + idProveedor + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Proveedor(idProveedor, nombre, calificacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Proveedor, dado el nombre del proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarProoveedorPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProveedor.eliminarProveedorPorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Proveedor, dado el identificador del proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param idProveedor - El identificador del proveedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarProveedorPorId (long idProveedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProveedor.eliminarProveedorPorId(pm, idProveedor);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Proveedor que tienen un nombre dado
	 * @param nombre - El nombre del proveedor
	 * @return La lista de objetos Proveedor, construidos con base en las tuplas de la tabla PROVEEDOR
	 */
	public List<Proveedor> darProveedoresPorNombre(String nombre)
	{
		return sqlProveedor.darProveedorPorNombre(pmf.getPersistenceManager(), nombre);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Proveedor
	 * @return La lista de objetos Proveedor, construidos con base en las tuplas de la tabla PROVEEDOR
	 */
	public List<Proveedor> darProveedores()
	{
		return sqlProveedor.darProveedores(pmf.getPersistenceManager());
	}	
	
	/* ****************************************************************
	 * 			Métodos para manejar los PEDIDOS
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla PEDIDO
	 * Adiciona entradas al log de la aplicación
	 * @param proveedor - El proveedor del pedido
	 * @param sucursal - La sucursal que hace el pedido
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @param estadoOrden - El estado de orden del pedido
	 * @param cantidad - El numero de unidades solicitadas
	 * @param calificacion - La calificacion del pedido
	 * @param costoTotal - El costo total del pedido
	 * @return El objeto Pedido adicionado. null si ocurre alguna Excepción
	 */
	public Pedido adicionarPedido(long proveedor, long sucursal, Timestamp fechaEntrega, String estadoOrden, int cantidad, int calificacion, double costoTotal, long producto, int cantidadSub, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            //adicionar subpedido
        	tx.begin();
            long idPedido = nextval();
            long tuplasInsertadas = sqlSubPedido.adicionarSubPedido(pm, idPedido, producto, cantidadSub, costo);
            tx.commit();
            
            log.trace ("Inserción del subpedido: " + idPedido + ": " + tuplasInsertadas + " tuplas insertadas");
        	
        	tx.begin();
            long tuplasInsertadas2 = sqlPedido.adicionarPedido(pm, idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoTotal);
            tx.commit();
            
            log.trace ("Inserción del pedido: " + idPedido + ": " + tuplasInsertadas2 + " tuplas insertadas");
            
            return new Pedido(idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoTotal);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Pedido, dado el identificador del pedido
	 * Adiciona entradas al log de la aplicación
	 * @param idPedido - El identificador del pedido
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPedidoPorId (long idPedido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPedido.eliminarPedidoPorId(pm, idPedido);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido con un identificador dado
	 * @param idPedido - El identificador del Pedido
	 * @return El objeto Pedido, construido con base en las tuplas de la tabla PEDIDO con el identificador dado
	 */
	public Pedido darPedidoPorId(long idPedido)
	{
		return sqlPedido.darPedidoPorId(pmf.getPersistenceManager(), idPedido);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen un identificador dado
	 * @param idPedido - El identificador del pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorId(long idPedido)
	{
		return sqlPedido.darPedidosPorId(pmf.getPersistenceManager(), idPedido);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una sucursal dada
	 * @param idSucursal - El identificador de la sucursal que hace el pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorSucursal(long idSucursal)
	{
		return sqlPedido.darPedidosPorSucursal(pmf.getPersistenceManager(), idSucursal);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen un proveedor dado
	 * @param idProveedor - El identificador del proveedor que hace el pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorProveedor(long idProveedor)
	{
		return sqlPedido.darPedidosPorProveedor(pmf.getPersistenceManager(), idProveedor);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una sucursal dada y un proveedor dado
	 * @param idSucursal - El identificador de la sucursal que hace el pedido
	 * @param idProveedor - El identificador del proveedor que hace el pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorProveedorYSucursal(long idProveedor, long idSucursal)
	{
		return sqlPedido.darPedidosPorProveedorYSucursal(pmf.getPersistenceManager(), idProveedor, idSucursal);
	}
	
	/**
	 * Método que cambia el estado de orden de un pedido (entregado o pendiente) dependiendo de si el pedido se emitió o si ya fue entregado.
	 * @param idPedido - el identificador del pedido
	 * @param estadoOrden - El estado de orden del pedido (pendiente, entregado)
	 * @return El número de tuplas modificadas
	 */
	public long cambiarEstadoOrdenPedido(long idPedido, String estadoOrden)
	{
		return sqlPedido.cambiarEstadoOrdenPedido(pmf.getPersistenceManager(), idPedido, estadoOrden);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una calificacion dada
	 * @param calificacion - La calificacion del pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorCalificacion(int calificacion)
	{
		return sqlPedido.darPedidosPorCalificacion(pmf.getPersistenceManager(), calificacion);
	}
	
	/**
	 * Método que cambia la calificacion de un pedido dado
	 * @param idPedido - El estado de orden del pedido (pendiente, entregado)
	 * @param calificacion - La calificacion a cambiar del pedido
	 * @return El número de tuplas modificadas
	 */
	public long cambiarCalificacionPedido(long idPedido, int calificacion)
	{
		return sqlPedido.cambiarCalificacionPedido(pmf.getPersistenceManager(), idPedido, calificacion);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una fecha de entrega dada dada
	 * @param fechaEntrega- La fecha de entrega del pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorFechaEntrega(Timestamp fechaEntrega)
	{
		return sqlPedido.darPedidosPorFechaEntrega(pmf.getPersistenceManager(), fechaEntrega);
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Pedido, la cual su estado de orden es 'Entregado'
	 * Adiciona entradas al log de la aplicación
	 * @param estadoOrden - El estado de orden del pedido
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPedidosTerminados (String estadoOrden) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            estadoOrden = "Entregado";
        	tx.begin();
            long resp = sqlPedido.eliminarPedidosTerminados(pm, estadoOrden);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidos()
	{
		return sqlPedido.darPedidos(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los SUBPEDIDOS
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SUBPEDIDO
	 * Adiciona entradas al log de la aplicación
	 * @param producto - El producto del subpedido
	 * @param cantidad - La cantidad de unidades pedidas por producto
	 * @param costo - El costo del subpedido
	 * @return El objeto Subpedido adicionado. null si ocurre alguna Excepción
	 */
	public Subpedido adicionarSubPedido(long producto, int cantidad, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            //adicionar subpedido
        	
        	tx.begin();
            long idPedido = nextval ();
            long tuplasInsertadas = sqlSubPedido.adicionarSubPedido(pm, idPedido, producto, cantidad, costo);
            tx.commit();
            
            log.trace ("Inserción del pedido: " + idPedido + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Subpedido(producto, idPedido, cantidad, costo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Subpedido, dado el identificador del pedido
	 * Adiciona entradas al log de la aplicación
	 * @param idPedido - El identificador del pedido
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSubPedidoPorId (long idPedido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlSubPedido.eliminarSubPedidoPorId(pm, idPedido);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Subpedido con un identificador dado
	 * @param idPedido - El identificador del Pedido
	 * @return El objeto Subpedido, construido con base en las tuplas de la tabla SUBPEDIDO con el identificador dado
	 */
	public Subpedido darSubPedidoPorId(long idPedido)
	{
		return sqlSubPedido.darSubPedidoPorId(pmf.getPersistenceManager(), idPedido);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Subpedido que tienen un producto dado
	 * @param producto - El identificador del producto pedido
	 * @return La lista de objetos Subpedido, construidos con base en las tuplas de la tabla SUBPEDIDO
	 */
	public List<Subpedido> darSubPedidosPorProducto(long producto)
	{
		return sqlSubPedido.darSubPedidosPorProducto(pmf.getPersistenceManager(), producto);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Subpedido
	 * @return La lista de objetos Subpedido, construidos con base en las tuplas de la tabla `SUBPEDIDO
	 */
	public List<Subpedido> darSubPedidos()
	{
		return sqlSubPedido.darSubPedidos(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación OFRECEN
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla OFRECEN
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @param costo - el costo del producto según el proveedor
	 * @return El objeto Ofrecen adicionado. null si ocurre alguna Excepción
	 */
	public Ofrecen adicionarOfrecen(long idProducto, long idProveedor, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	tx.begin();
            long tuplasInsertadas = sqlOfrecen.adicionarOfrecen(pm, idProducto, idProveedor, costo);
            tx.commit();
            
            log.trace ("Inserción de ofrecen: [" + idProducto + ", " + idProveedor + "]. " + tuplasInsertadas + " tuplas insertadas");
            
            return new Ofrecen(idProducto, idProveedor, costo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Ofrecen, dado el identificador del producto y del proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOfrecen(long idProducto, long idProveedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOfrecen.eliminarOfrecen(pm, idProducto, idProveedor);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Ofrecen
	 * @return La lista de objetos Ofrecen, construidos con base en las tuplas de la tabla OFRECEN
	 */
	public List<Ofrecen> darOfrecen()
	{
		return sqlOfrecen.darOfrecen(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que encuentra el identificador y el número de productos que ofrecen los proveedores
	 * @return Una lista de parejas de objetos, el primer elemento de cada pareja representa el identificador de un proveedor,
	 * 	el segundo elemento representa el productos que ofrece.
	 */
	public List<Object []> darProveedorYCantidadProductosOfrecen()
	{
		return sqlOfrecen.darProveedorYCantidadProductosOfrecen(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CLIENTE
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del cliente
	 * @param correo - El correo del cliente
	 * @param tipo - El tipo de cliente(PERSONA, EMPRESA)
	 * @param direccion - La direccion del cliente
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente(String nombre, String correo, String tipo, String direccion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	
        	tx.begin();
        	long idCliente = nextval();
            long tuplasInsertadas = sqlCliente.adicionarCliente(pm, idCliente, nombre, correo, tipo, direccion);
            tx.commit();
            
            log.trace ("Inserción del cliente: " + idCliente + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Cliente(idCliente, nombre, direccion, correo, tipo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Cliente, dado el identificador del cliente
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El identificador del cliente
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClientePorId (long idCliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCliente.eliminarClientePorId(pm, idCliente);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Cliente, dado el nombre del cliente
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del cliente
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClientePorNombre(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCliente.eliminarClientePorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Cliente con un identificador dado
	 * @param idCliente - El identificador del Cliente
	 * @return El objeto Cliente, construido con base en las tuplas de la tabla CLIENTE con el identificador dado
	 */
	public Cliente darClientePorId(long idCliente)
	{
		return sqlCliente.darClientePorId(pmf.getPersistenceManager(), idCliente);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Cliente que tienen un nombre dado
	 * @param nombre - El nombre del cliente
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> darClientesPorNombre(String nombre)
	{
		return sqlCliente.darClientesPorNombre(pmf.getPersistenceManager(), nombre);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Cliente que tienen un tipo dado (PERSONA, EMPRESA)
	 * @param tipo - El tipo del cliente (PERSONA, EMPRESA)
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> darClientesPorTipo(String tipo)
	{
		return sqlCliente.darClientesPorTipo(pmf.getPersistenceManager(), tipo);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla Cliente
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> darClientes()
	{
		return sqlCliente.darClientes(pmf.getPersistenceManager());
	}
		
	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla PROMOCION. Primero se a�ade un producto especial y luego es asociado con la promoci�n
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto
	 * @param marca - Marca del producto
	 * @param presentacion - Presentacion del producto
	 * @param codigobarras - Código de barras del producto
	 * @param unidadmedida - Las unidades de medida del producto
	 * @param categoria - La categoria del producto (perecederos, no perecederos, aseo, abarrotes, etc)
	 * @param tipo - El tipo de producto por categoria
	 * @param descripcion de la promocion
	 * @param fechainicio de la promocion
	 * @param fechafin de la promocion
	 * @param unidadesdisponibles para la promocion
	 * @return El objeto Proveedor adicionado. null si ocurre alguna Excepción
	 */
	public Promocion adicionarPromocion(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo, double precio, String descripcion, Timestamp fechaInicio, Timestamp fechaFin, int unidadesdisponibles)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	tx.begin();
            long idProducto = nextval ();
            long tuplasInsertadas = sqlProducto.adicionarProducto(pm, idProducto, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
            tx.commit();
            
            log.trace ("Insercion de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            tx.begin();
            long idPromocion = nextval ();
            long tuplasInsertadas2 = sqlPromocion.adicionarPromocion(pm, idPromocion, precio, descripcion, fechaInicio, fechaFin, unidadesdisponibles, idProducto);
            tx.commit();
            
            log.trace ("Inserción de la promocion: " + descripcion + ": " + tuplasInsertadas2 + " tuplas insertadas");
            
            return new  Promocion(idPromocion, precio, descripcion, fechaInicio, fechaFin, unidadesdisponibles, idProducto);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
}
