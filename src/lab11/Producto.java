package lab11;

import java.math.BigDecimal;

public record Producto(int codigo, String nombre, BigDecimal precio) {
    public Producto {
        if(codigo <= 0 || nombre == null || nombre.isBlank() || precio == null || precio.signum() < 0) {
            throw new IllegalArgumentException("Los datos del producto no son validos.");
        }
    }
}
