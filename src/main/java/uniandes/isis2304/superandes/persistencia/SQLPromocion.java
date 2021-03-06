package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Promocion;
import uniandes.isis2304.superandes.negocio.Subpedido;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROMOCION de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLPromocion {

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
	public SQLPromocion(PersistenciaSuperAndes pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una PROMOCION a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador de la promocion
	 * @param precio - El precio de la promocion
	 * @param descripcion - La descripcion de la promocion
	 * @param fechaInicio - La fecha de inicio de la promocion
	 * @param fechaFin - La fecha final de la promocion
	 * @param unidadesDisponibles - Las unidades disponibles para la venta de la promoción
	 * @param producto - Identificador del producto en promocion	 
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPromocion(PersistenceManager pm, long id, double precio, String descripcion, Timestamp fechaInicio, Timestamp fechaFin, int unidadesDisponibles, long producto) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaFactura() + "(id, idproducto, precio, descripcion, fechainicio, fechafin, unidadesdisponibles) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, precio, descripcion, fechaInicio, fechaFin, unidadesDisponibles, producto);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PROMOCIONES de la base de datos de Superandes, por su id
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador de la promocion
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPromocionPorId(PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion() + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS PROMOCIONES de la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPromocion(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PROMOCION de la 
	 * base de datos de Superandes, por su identificador de sucursal
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador de la sucursal
	 * @return Una lista de objetos PROMOCION que tienen el identifiacor dado
	 */
	public List<Promocion> darPromocionesPorSucursal(PersistenceManager pm, long idSucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromocion() + " WHERE idsucursal = ?");
		q.setResultClass(Promocion.class);
		q.setParameters(idSucursal);
		return (List<Promocion>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PROMOCION de la 
	 * base de datos de Superandes, por su identificador de sucursal
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @return Una lista de objetos PROMOCION que tienen el identifiacor dado
	 */
	public List<Promocion> darPromocionesPorProducto(PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromocion() + " WHERE idproducto = ?");
		q.setResultClass(Promocion.class);
		q.setParameters(idProducto);
		return (List<Promocion>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PROMOCION de la 
	 * base de datos de Superandes, por su identificador de sucursal y producto
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador de la sucursal
	 * @param idProducto - El identificador del producto
	 * @return Una lista de objetos PROMOCION que tienen el identifiacor dado
	 */
	public List<Promocion> darPromocionesPorSucursalyProducto(PersistenceManager pm, long idSucursal, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromocion() + " WHERE idsucursal = ? AND idproducto = ?");
		q.setResultClass(Promocion.class);
		q.setParameters(idSucursal, idProducto);
		return (List<Promocion>) q.executeList();
	}
}
