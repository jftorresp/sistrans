package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Ofrecen;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperAndes;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SIRVEN de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */

class SQLOfrecen {
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
	private PersistenciaSuperAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */	
	public SQLOfrecen(PersistenciaSuperAndes pp) {
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un OFRECEN a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @param costo - el costo del producto según el proveedor
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarOfrecen (PersistenceManager pm, long idProducto, long idProveedor, double costo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfrecen () + "(idproducto, idproveedor, costo) values (?, ?, ?)");
        q.setParameters(idProducto, idProveedor, costo);
        return (long)q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN OFRECEN de la base de datos de SuperAndes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del bar
	 * @param idProveedor - El identificador de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOfrecen (PersistenceManager pm, long idProducto, long idProveedor) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfrecen () + " WHERE idproducto = ? AND idproveedor = ?");
        q.setParameters(idProducto, idProveedor);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los SIRVEN de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SIRVEN
	 */
	public List<Ofrecen> darOfrecen (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfrecen ());
		q.setResultClass(Ofrecen.class);
		return (List<Ofrecen>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar el identificador y el número de productos que ofrecen los proveedores
	 * @param pm - El manejador de persistencia
	 * @return Una lista de parejas de objetos, el primer elemento de cada pareja representa el identificador de un proveedor,
	 * 	el segundo elemento representa el productos que ofrece.
	 */
	public List<Object []> darProveedorYCantidadProductosOfrecen(PersistenceManager pm)
	{
        String sql = "SELECT idProveedor, count (*) as numProductos";
        sql += " FROM " + pp.darTablaOfrecen ();
       	sql	+= " GROUP BY idProveedor";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
}
