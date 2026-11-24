package lab11.ejercicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EstrategiasPromocion {
    public record Producto(String nombre, BigDecimal precio) {
        public Producto {
            if(nombre == null || nombre.isBlank() || precio == null || precio.signum() < 0) {
                throw new IllegalArgumentException("Producto invalido");
            }
        }
    }

    public interface Estrategia { BigDecimal calcular(List<Producto> productos); }

    public static class SinDescuento implements Estrategia {
        @Override public BigDecimal calcular(List<Producto> productos) { return total(productos); }
    }

    public static class DescuentoFijo implements Estrategia {
        @Override public BigDecimal calcular(List<Producto> productos) {
            return total(productos).multiply(new BigDecimal("0.90")).setScale(2,RoundingMode.HALF_UP);
        }
    }

    public static class DescuentoPorcentual implements Estrategia {
        @Override public BigDecimal calcular(List<Producto> productos) {
            BigDecimal finalTotal=BigDecimal.ZERO;
            List<String> visitados=new ArrayList<>();
            for(Producto producto:productos) {
                if(visitados.contains(producto.nombre())) finalTotal=finalTotal.add(producto.precio().multiply(new BigDecimal("0.70")));
                else { visitados.add(producto.nombre());finalTotal=finalTotal.add(producto.precio()); }
            }
            return finalTotal.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public static class DescuentoPorcentualAcumulado implements Estrategia {
        @Override public BigDecimal calcular(List<Producto> productos) {
            BigDecimal suma=total(productos);
            if(productos.size() >= 3) {
                BigDecimal menor=productos.stream().map(Producto::precio).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                suma=suma.subtract(menor.multiply(new BigDecimal("0.50")));
            }
            return suma.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public static class CalculadoraDePrecios {
        private Estrategia estrategia = new SinDescuento();
        public void setEstrategia(Estrategia nueva) { estrategia=nueva; }
        public BigDecimal calcular(List<Producto> productos) { return estrategia.calcular(productos); }
    }

    private static BigDecimal total(List<Producto> productos) {
        return productos.stream().map(Producto::precio).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        List<Producto> compra=List.of(new Producto("Cuaderno",new BigDecimal("40")),new Producto("Cuaderno",new BigDecimal("40")),new Producto("Lapiz",new BigDecimal("10")));
        CalculadoraDePrecios calculadora=new CalculadoraDePrecios();
        try(Scanner scanner=new Scanner(System.in)) {
            int opcion;
            do {
                System.out.println("1. Sin descuento  2. Descuento 10%  3. Segundo igual 30%  4. Tres productos: 50% del menor  0. Salir");
                System.out.print("Opcion: ");
                try { opcion=Integer.parseInt(scanner.nextLine()); }
                catch(NumberFormatException ex) { opcion=-1; }
                switch(opcion) {
                    case 1 -> calculadora.setEstrategia(new SinDescuento());
                    case 2 -> calculadora.setEstrategia(new DescuentoFijo());
                    case 3 -> calculadora.setEstrategia(new DescuentoPorcentual());
                    case 4 -> calculadora.setEstrategia(new DescuentoPorcentualAcumulado());
                    case 0 -> { continue; }
                    default -> { System.out.println("Opcion incorrecta"); continue; }
                }
                System.out.println("Total con promocion: S/ " + calculadora.calcular(compra));
            } while(opcion != 0);
        }
    }
}
