package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperAndes;

/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto PRODUCTO de SuperAndes
 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLProducto 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaSuperAndes pp;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLProducto (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una PRODUCTO a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del producto
	 * @param nombre - El nombre del producto
	 * @param marca - La marca del producto
	 * @param presentacion - la presentaci�n del producto
	 * @param codigobarras - El codigo de barras del producto
	 * @param unidadmedida - la unidad de medida del producto
	 * @param categoria - la categor�a del producto
	 * @param tipo - el tipo del producto
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarProducto (PersistenceManager pm, long id, String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProducto () + "(id, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PRODUCTOS de la base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre del producto
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProductoPorNombre (PersistenceManager pm, String nombreProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
        q.setParameters(nombreProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PRODUCTOS de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProductoPorId (PersistenceManager pm, long idProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE id = ?");
        q.setParameters(idProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de PRODUCTOS de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre del producto
	 * @return Una lista de objetos PRODUCTO que tienen el nombre dado
	 */
	public List<Producto> darProductosPorNombre (PersistenceManager pm, String nombreProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
		q.setResultClass(Producto.class);
		q.setParameters(nombreProducto);
		return (List<Producto>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS PRODUCTOS de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PRODUCTO
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
	
}

