package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


class SQLUtil {

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
	 * El manejador de persistencia general de la aplicaciÃ³n
	 */
	PersistenciaSuperAndes pp;
	
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaciÃ³n
	 */
	public SQLUtil(PersistenciaSuperAndes pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo nÃºmero de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El nÃºmero de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqSuperandes() + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}
	
	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas TRANSACCION, PROMOCION, FACTURA, CLIENTE, OFRECEN, SUBPEDIDO, 
	 * PEDIDO, PROVEEDOR, VENDE, ESTANTE, BODEGA, PRODUCTO, SUCURSAL, SUPERMERCADO respectivamente.
	 */
	public long [] limpiarSuperAndes(PersistenceManager pm)
	{
        Query qTransaccion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTransaccion());          
        Query qPromocion= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion());
        Query qFactura= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura());
        Query qCliente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente());
        Query qOfrecen= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfrecen());
        Query qSubpedido= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSubpedido());
        Query qPedido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido ());
        Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor());
        Query qVende = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVende ());
        Query qEstante = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstante ());
        Query qBodega = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBodega ());
        Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto ());
        Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal ());        
        Query qSupermercado = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado ());


        long transaccionEliminados = (long) qTransaccion.executeUnique ();
        long promocionEliminados = (long) qPromocion.executeUnique ();
        long facturaEliminadas = (long) qFactura.executeUnique ();
        long clienteEliminadas = (long) qCliente.executeUnique ();
        long ofrecenEliminados = (long) qOfrecen.executeUnique ();
        long subpedidoEliminados = (long) qSubpedido.executeUnique ();
        long pedidoEliminados = (long) qPedido.executeUnique ();
        long proveedorEliminados = (long) qProveedor.executeUnique ();
        long vendeEliminados = (long) qVende.executeUnique ();
        long estanteEliminados = (long) qEstante.executeUnique ();
        long bodegaEliminados = (long) qBodega.executeUnique ();
        long productoEliminados = (long) qProducto.executeUnique ();
        long sucursalEliminados = (long) qSucursal.executeUnique ();
        long supermercadoEliminados = (long) qSupermercado.executeUnique ();

        return new long[] {transaccionEliminados, promocionEliminados, facturaEliminadas, clienteEliminadas,
        		ofrecenEliminados, subpedidoEliminados, pedidoEliminados, proveedorEliminados, vendeEliminados, estanteEliminados,
        		bodegaEliminados, productoEliminados, sucursalEliminados, supermercadoEliminados};
	}

}
