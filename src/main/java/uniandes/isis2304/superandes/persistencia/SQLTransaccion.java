package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Transaccion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TRANSACCION de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLTransaccion 
{
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
	public SQLTransaccion (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TRANSACCION a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @param cantidad - numero de productos
	 * @param numerofactura - la factura a la que está asociada la transacción
	 * @param costo - costo de la transacción
	 * @param promocion - identificador de la promoción a la cual está asociada (puede no tener)
	 * @return El número de tuplas insertadas
	 */
	public long adicionarTransaccion (PersistenceManager pm, long idProducto, int cantidad, long numerofactura, double costo, long promocion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaTransaccion () + "(idproducto, cantidad, numerofactura, costo, promocion) values (?, ?, ?, ?, ?)");
        q.setParameters(idProducto, cantidad, numerofactura, costo, promocion);
        return (long) q.executeUnique();
	}

	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN TRANSACCION de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param numerofactura - numero de factura de la transacción
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarTransaccionPorNumeroFactura (PersistenceManager pm, long numerofactura)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTransaccion () + " WHERE numerofactura = ?");
        q.setParameters(numerofactura);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TRANSACCIONES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreSupermercado - El nombre del supermercado que posee esa transaccion 
	 * @return Una lista de objetos TRANSACCION que son del supermercado de 
	 */
	public List<Transaccion> darTransaccionesPorNumeroFactura (PersistenceManager pm, long numerofactura) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTransaccion () + " WHERE numerofactura= ?");
		q.setResultClass(Transaccion.class);
		q.setParameters(numerofactura);
		return (List<Transaccion>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TRANSACCIONES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos TRANSACCION
	 */
	public List<Transaccion> darTransacciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTransaccion ());
		q.setResultClass(Transaccion.class);
		return (List<Transaccion>) q.executeList();
	}

	
}

