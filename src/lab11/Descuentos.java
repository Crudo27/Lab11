package lab11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Descuentos {
    public interface Estrategia {
        BigDecimal calcular(List<Producto> productos);
        String nombre();
    }

    private static BigDecimal dinero(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal subtotal(List<Producto> productos) {
        return productos.stream().map(Producto::precio).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static class SinDescuento implements Estrategia {
        @Override
        public BigDecimal calcular(List<Producto> productos) {
            return dinero(subtotal(productos));
        }

        @Override
        public String nombre() {
            return "Sin descuento";
        }
    }

    public static class DiezPorCiento implements Estrategia {
        @Override
        public BigDecimal calcular(List<Producto> productos) {
            return dinero(subtotal(productos).multiply(new BigDecimal("0.90")));
        }

        @Override
        public String nombre() {
            return "10 % de descuento general";
        }
    }

    public static class Repetidos implements Estrategia {
        @Override
        public BigDecimal calcular(List<Producto> productos) {
            BigDecimal total = BigDecimal.ZERO;
            Set<Integer> codigos = new HashSet<>();
            for(Producto producto : productos) {
                BigDecimal precio = producto.precio();
                if(!codigos.add(producto.codigo())) {
                    precio = precio.multiply(new BigDecimal("0.70"));
                }
                total = total.add(precio);
            }
            return dinero(total);
        }

        @Override
        public String nombre() {
            return "30 % desde la segunda unidad igual";
        }
    }

    public static class TresProductos implements Estrategia {
        @Override
        public BigDecimal calcular(List<Producto> productos) {
            BigDecimal total = subtotal(productos);
            if(productos.size() >= 3) {
                BigDecimal minimo = productos.stream().map(Producto::precio).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                total = total.subtract(minimo.multiply(new BigDecimal("0.50")));
            }
            return dinero(total);
        }

        @Override
        public String nombre() {
            return "50 % al mas barato desde tres productos";
        }
    }
}
