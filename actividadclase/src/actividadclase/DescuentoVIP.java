package actividadclase;

public class DescuentoVIP implements Strategy {

	@Override
	public double calcularDescuento(double precio) {
		return precio * 0.7;
	}

}
