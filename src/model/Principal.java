package model;

public class Principal {

    public static void main(String[] args) {

        AplicacionUsuarios aplicacion = new AplicacionUsuarios();
        aplicacion.crearFicheroJson();
        aplicacion.ejecutar();
    }
}
