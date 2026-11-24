package actividadclase;

public class CancelarPedido implements Comando {
	private Pedido pedido;
	
	public CancelarPedido(Pedido pedido)
	{
		this.pedido = pedido;
	}
	@Override
	public void ejecutar() 
	{
		pedido.cambiarEstado("Cancelado");
	}

}
