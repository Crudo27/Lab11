package actividadclase;

public class DescuentoNormal implements Strategy {

	@Override
	public double calcularDescuento(double precio) {
		return precio * 0.9;
	}

}
