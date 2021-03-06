package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Sucursal;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperAndes;

	/**
	 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto SUCURSAL de SuperAndes
	 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
	 * 
	 * @author n.cobos
	 */
	class SQLSucursal 
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
		public SQLSucursal (PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		/**
		 * Crea y ejecuta la sentencia SQL para adicionar un SUCURSAL a la base de datos de SuperAndes
		 * @param pm - El manejador de persistencia
		 * @param idSucursal - El identificador de la sucursal
		 * @param nombre - El nombre de la sucursal
		 * @param ciudad - La ciudad de la sucursal
		 * @param direccion - La direcci�n de la sucursal 
		 * @param segmentomercado - el segmento del mercado al cual se dirige la sucursal
		 * @param tamano - tama�o de area en metros cuadrados
		 * @param supermercado - supermercado al cual pertenece la sucursal
		 * @return El n�mero de tuplas insertadas
		 */
		public long adicionarSucursal (PersistenceManager pm, long idSucursal, String nombre, String ciudad, String direccion, String segmentomercado, double tamano, String supermercado) 
		{
	        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSucursal () + "(id, nombre, ciudad, direccion, segmentomercado, tamano, supermercado) values (?, ?, ?, ?, ?, ?, ?)");
	        q.setParameters(idSucursal, nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para eliminar SUCURSALES de la base de datos de SuperAndes, por su nombre
		 * @param pm - El manejador de persistencia
		 * @param nombreSucursal - El nombre de la sucursal
		 * @return EL n�mero de tuplas eliminadas
		 */
		public long eliminarSucursalesPorNombre (PersistenceManager pm, String nombreSucursal)
		{
	        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal () + " WHERE nombre = ?");
	        q.setParameters(nombreSucursal);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para eliminar UN SUCURSAL de la base de datos de SuperAndes, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param idSucursal - El identificador de la sucursal
		 * @return EL n�mero de tuplas eliminadas
		 */
		public long eliminarSucursalPorId (PersistenceManager pm, long idSucursal)
		{
	        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal () + " WHERE id = ?");
	        q.setParameters(idSucursal);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN SUCURSAL de la 
		 * base de datos de SuperAndes, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param idSucursal - El identificador de la sucursal
		 * @return El objeto SUCURSAL que tiene el identificador dado
		 */
		public Sucursal darSucursalPorId (PersistenceManager pm, long idSucursal) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE id = ?");
			q.setResultClass(Sucursal.class);
			q.setParameters(idSucursal);
			return (Sucursal) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS SUCURSALES de la 
		 * base de datos de SuperAndes, por su nombre
		 * @param pm - El manejador de persistencia
		 * @param nombreSupermercado - El nombre del supermercado que posee esa sucursal 
		 * @return Una lista de objetos SUCURSAL que son del supermercado de 
		 */
		public List<Sucursal> darSucursalesPorSupermercado (PersistenceManager pm, String nombreSupermercado) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE supermercado = ?");
			q.setResultClass(Sucursal.class);
			q.setParameters(nombreSupermercado);
			return (List<Sucursal>) q.executeList();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS SUCURSALES de la 
		 * base de datos de SuperAndes
		 * @param pm - El manejador de persistencia
		 * @return Una lista de objetos SUCURSAL
		 */
		public List<Sucursal> darSucursales (PersistenceManager pm)
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal ());
			q.setResultClass(Sucursal.class);
			return (List<Sucursal>) q.executeList();
		}

			

}