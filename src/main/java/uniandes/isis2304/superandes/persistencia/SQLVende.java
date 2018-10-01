package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Vende;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VENDE de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLVende {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	PersistenciaSuperAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLVende(PersistenciaSuperAndes pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SUBPEDIDO a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param sucursal - El identificador de la sucursal
	 * @param producto - El identificador del producto
	 * @param nivelReorden - El nivel de reorden de la venta
	 * @param precioUnitario - El precio unitario de la venta
	 * @param precioUnidadMedida - El precio por unidad de medida de la venta 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarVende(PersistenceManager pm, long sucursal, long producto, int nivelReorden, double precioUnitario, double precioUnidadMedida) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPedido() + "(idsucursal, idproducto, nivelreorden, preciounitario, preciounidadmedida) values (?, ?, ?, ?, ?)");
        q.setParameters(sucursal, producto, nivelReorden, precioUnitario, precioUnidadMedida);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS VENTAS de la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVende(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVende());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN VENDE de la base de datos de SuperAndes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador de la sucursal
	 * @param idProducto - El identificador del producto
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVende(PersistenceManager pm, long idSucursal, long idProducto) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVende() + " WHERE idsucursal = ? AND idproducto = ?");
        q.setParameters(idSucursal, idProducto);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para ELIMINAR TODAS LAS VENTAS DE UNA SUCURSAL de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador de la sucursal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVendePorIdSucursal(PersistenceManager pm, long idSucursal) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVende() + " WHERE idsucursal = ?");
        q.setParameters(idSucursal);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para ELIMINAR TODAS LAS VENTAS DE UN PRODUCTO de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVendePorIdProducto(PersistenceManager pm, long idProducto) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVende() + " WHERE idproducto = ?");
        q.setParameters(idProducto);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los VENDE de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VENDE
	 */
	public List<Vende> darVende(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVende());
		q.setResultClass(Vende.class);
		return (List<Vende>) q.execute();
	}
}
