package actividadclase;

public class Administrador implements Observador{
	@Override
	public void actualizar(String mensaje)
	{
		System.out.println("Administrador: "+mensaje);
	}
}
