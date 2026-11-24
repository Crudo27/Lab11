package actividadclase;

public class ConfirmarPedido implements Comando {
	private Pedido pedido;
	
	public ConfirmarPedido(Pedido pedido)
	{
		this.pedido = pedido;
	}
	
	@Override
	public void ejecutar() 
	{
		System.out.println("Total: "+pedido.obtenerTotal());
		pedido.cambiarEstado("Confirmado");
	}

}
