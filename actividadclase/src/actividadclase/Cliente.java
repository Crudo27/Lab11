package actividadclase;

public class Cliente implements Observador{
	@Override
	public void actualizar(String mensaje)
	{
		System.out.println("Cliente: "+ mensaje);
	}
}
