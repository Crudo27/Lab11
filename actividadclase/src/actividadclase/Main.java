package actividadclase;

public class Main {

	public static void main(String[] args) {
		
		Pedido pedido = new Pedido(200, new DescuentoVIP());
		
		pedido.suscribir(new Cliente());
		pedido.suscribir(new Administrador());
		
		GestorPedido gestor = new GestorPedido();
		
		gestor.ejecutarComando(new ConfirmarPedido(pedido));
		
		
		gestor.ejecutarComando(new CancelarPedido(pedido));
		
		

	}

}
