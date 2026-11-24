package lab11.ejercicios;

import java.math.BigDecimal;

public class BebidasPatrones {
    public interface Bebida {
        String descripcion();
        BigDecimal precio();
    }

    public static class Cafe implements Bebida {
        @Override public String descripcion() { return "Cafe americano"; }
        @Override public BigDecimal precio() { return new BigDecimal("7.00"); }
    }

    public static class Te implements Bebida {
        @Override public String descripcion() { return "Te verde"; }
        @Override public BigDecimal precio() { return new BigDecimal("5.00"); }
    }

    public abstract static class FabricaBebidas {
        public abstract Bebida crear();
    }

    public static class FabricaCafe extends FabricaBebidas {
        @Override public Bebida crear() { return new Cafe(); }
    }

    public static class FabricaTe extends FabricaBebidas {
        @Override public Bebida crear() { return new Te(); }
    }

    public abstract static class Agregado implements Bebida {
        protected final Bebida base;
        public Agregado(Bebida base) { this.base=base; }
    }

    public static class Leche extends Agregado {
        public Leche(Bebida base) { super(base); }
        @Override public String descripcion() { return base.descripcion()+" + leche"; }
        @Override public BigDecimal precio() { return base.precio().add(new BigDecimal("1.50")); }
    }

    public static class Canela extends Agregado {
        public Canela(Bebida base) { super(base); }
        @Override public String descripcion() { return base.descripcion()+" + canela"; }
        @Override public BigDecimal precio() { return base.precio().add(new BigDecimal("0.50")); }
    }

    public static void main(String[] args) {
        FabricaBebidas fabrica=new FabricaCafe();
        Bebida pedido=new Canela(new Leche(fabrica.crear()));
        System.out.println(pedido.descripcion()+" -> S/ "+pedido.precio());
        fabrica=new FabricaTe();
        System.out.println(fabrica.crear().descripcion()+" -> S/ "+fabrica.crear().precio());
    }
}
