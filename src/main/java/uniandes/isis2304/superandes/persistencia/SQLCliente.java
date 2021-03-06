package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CLIENTE de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLCliente {
	
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
	public SQLCliente (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CLIENTE a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @param nombre - El nombre del cliente
	 * @param correo - El correo del cliente
	 * @param tipo - El tipo de cliente(PERSONA, EMPRESA)
	 * @param direccion - La direccion del cliente
	 * @return El número de tuplas insertadas
	 */
	public long adicionarCliente (PersistenceManager pm, long idCliente, String nombre, String correo, String tipo, String direccion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente () + "(id, nombre, correo, tipo, direccion) values (?, ?, ?, ?, ?)");
        q.setParameters(idCliente, nombre, correo, tipo, direccion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar CLIENTES de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCliente - El nombre del cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorNombre (PersistenceManager pm, String nombreCliente)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE nombre = ?");
        q.setParameters(nombreCliente);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CLIENTE de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorId (PersistenceManager pm, long idCliente)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE id = ?");
        q.setParameters(idCliente);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CLIENTE de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @return El objeto CLIENTE que tiene el identificador dado
	 */
	public Cliente darClientePorId (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE id = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(idCliente);
		return (Cliente) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCliente - El nombre de cliente buscado
	 * @return Una lista de objetos CLIENTE que tienen el nombre dado
	 */
	public List<Cliente> darClientesPorNombre (PersistenceManager pm, String nombreCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente() + " WHERE nombre = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(nombreCliente);
		return (List<Cliente>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CLIENTE
	 */
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la 
	 * base de datos de Superandes, por su tipo
	 * @param pm - El manejador de persistencia
	 * @param tipoCliente - El tipo del cliente buscado
	 * @return Una lista de objetos CLIENTE que tienen el tipo dado
	 */
	public List<Cliente> darClientesPorTipo (PersistenceManager pm, String tipoCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente() + " WHERE tipo = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(tipoCliente);
		return (List<Cliente>) q.executeList();
	}
}
