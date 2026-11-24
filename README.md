# Laboratorio 11 - Lenguajes de Programacion III

Practica de los patrones Observer, Strategy y Command, con aplicaciones independientes, ejercicios adicionales y una aplicacion integradora de carrito de compras con separacion MVC.

## Archivos principales

- `src/lab11/Main.java`: carrito de compras con los tres patrones y MVC.
- `src/lab11/ejercicios/ObserverNotificaciones.java`: suscripcion y desuscripcion de usuarios a promociones.
- `src/lab11/ejercicios/EstrategiasPromocion.java`: estrategias SinDescuento, DescuentoFijo (10 %), DescuentoPorcentual (30 % segunda unidad igual) y DescuentoPorcentualAcumulado (50 % al menor de tres).
- `src/lab11/ejercicios/ControlTelevisor.java`: cinco funciones para televisor con Command.
- `src/lab11/ejercicios/ControlDispositivos.java`: Luz, Ventilador, Aire Acondicionado, encendido, apagado y deshacer.
- `src/lab11/ejercicios/BebidasPatrones.java`: Factory Method y Decorator.
- `actividadclase/src/actividadclase/`: ejercicio original de pedidos, conservado al trasladar el laboratorio.

## Compilar y ejecutar (JDK 17 o superior)

```sh
javac -encoding UTF-8 -d out $(find src tests actividadclase/src -name '*.java' ! -name 'module-info.java')
java -cp out lab11.Main
java -cp out lab11.Pruebas
java -cp out lab11.PruebasAdicionales
java -cp out lab11.ejercicios.ObserverNotificaciones
java -cp out lab11.ejercicios.EstrategiasPromocion
java -cp out lab11.ejercicios.ControlTelevisor
java -cp out lab11.ejercicios.ControlDispositivos
java -cp out lab11.ejercicios.BebidasPatrones
```

En Eclipse se puede importar el proyecto como Java Project con `src` y `actividadclase/src` como carpetas fuente. Las pruebas no requieren dependencias externas.
