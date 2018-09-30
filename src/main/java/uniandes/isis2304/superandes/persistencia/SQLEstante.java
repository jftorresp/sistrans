package uniandes.isis2304.superandes.persistencia;

	import java.util.List;

	import javax.jdo.PersistenceManager;
	import javax.jdo.Query;

	import uniandes.isis2304.superandes.negocio.Estante;


	/**
	 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ESTANTE de SuperAndes
	 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
	 * 
	 * @author n.cobos
	 */
	class SQLEstante 
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
		public SQLEstante (PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		/**
		 * Crea y ejecuta la sentencia SQL para adicionar un ESTANTE a la base de datos de SuperAndes
		 * @param pm - El manejador de persistencia
		 * @param id - El identificador del estante
		 * @param capacidadVolumen - el volumen del estante
		 * @param capacidadPeso - Capacidad de peso del estante
		 * @param producto - producto que almacena el estante 
		 * @param sucursal - la sucursal a la que pertenece el estante
		 * @param nivelabastecimientobodega nivel mínimo de productos antes de reabastecer
		 * @param existencias - existencias del producto en el estante
		 * @return El número de tuplas insertadas
		 */
		public long adicionarEstante (PersistenceManager pm, long id, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias) 
		{
	        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEstante () + "(id, capacidadvolumen, capacidadpeso, producto, sucursal, nivelabastecimientobodega, existencias) values (?, ?, ?, ?, ?, ?, ?)");
	        q.setParameters(id, capacidadVolumen, capacidadPeso, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias);
	        return (long) q.executeUnique();
		}


		/**
		 * Crea y ejecuta la sentencia SQL para eliminar UN ESTANTE de la base de datos de SuperAndes, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param idEstante - El identificador del estante
		 * @return EL número de tuplas eliminadas
		 */
		public long eliminarEstantePorId (PersistenceManager pm, long idEstante)
		{
	        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstante () + " WHERE id = ?");
	        q.setParameters(idEstante);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la información de UN ESTANTE de la 
		 * base de datos de SuperAndes, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param idEstante - El identificador del estante
		 * @return El objeto ESTANTE que tiene el identificador dado
		 */
		public Estante darEstantePorId (PersistenceManager pm, long idEstante) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstante () + " WHERE id = ?");
			q.setResultClass(Estante.class);
			q.setParameters(idEstante);
			return (Estante) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ESTANTEES de la 
		 * base de datos de SuperAndes, por su nombre
		 * @param pm - El manejador de persistencia
		 * @param sucursal - numero de la sucursal
		 * @return Una lista de objetos ESTANTE que pertenecen a cierta sucursal
		 */
		public List<Estante> darEstantesPorSucursal (PersistenceManager pm, long sucursal) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstante () + " WHERE sucursal = ?");
			q.setResultClass(Estante.class);
			q.setParameters(sucursal);
			return (List<Estante>) q.executeList();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ESTANTES de la 
		 * base de datos de SuperAndes
		 * @param pm - El manejador de persistencia
		 * @return Una lista de objetos ESTANTE
		 */
		public List<Estante> darEstantes (PersistenceManager pm)
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstante ());
			q.setResultClass(Estante.class);
			return (List<Estante>) q.executeList();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para aumentar en diez el número de existencias de las estantes de la 
		 * base de datos de SuperAndes
		 * @param pm - El manejador de persistencia
		 * @param id - estante a la cual se le quiere realizar el proceso
		 * @return El número de tuplas modificadas
		 */
		public long aumentarExistenciasEstantesEnDiez (PersistenceManager pm, long id)
		{
	        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaEstante () + " SET existencias = existencias + 10 WHERE id = ?");
	        q.setParameters(id);
	        return (long) q.executeUnique();
		}

}
