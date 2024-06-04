package model;

import gui.VentanaBorrarUsuario;
import gui.VentanaCambiarContraseña;
import gui.VentanaCrearUsuario;
import gui.VentanaInicioSesion;
import gui.VentanaMenuUsuario;
import gui.VentanaVerUsuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;

public class AplicacionUsuarios {

    private final String RUTA_FICHERO = "fichero.json";
    private VentanaInicioSesion ventanaInicioSesion;
    private VentanaCrearUsuario ventanaCrearUsuario;
    private VentanaMenuUsuario ventanaMenuUsuario;
    private VentanaVerUsuario ventanaVerUsuario;
    private VentanaCambiarContraseña ventanaCambiarContraseña;
    private VentanaBorrarUsuario ventanaBorrarUsuario;

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public boolean isValidPass() {
        return validPass;
    }

    public void setValidPass(boolean validPass) {
        this.validPass = validPass;
    }

    public boolean validUser = false;
    public boolean validPass = false;

    File fUsuarios = new File(RUTA_FICHERO);

    /**
     * crea el fichero JSON si todavía no existe.
     */
    public void crearFicheroJson() {

        if (!fUsuarios.exists()) {
            try {
                fUsuarios.createNewFile();
                JSONObject datosUsuario = new JSONObject();
                JSONObject datosUsuario2 = new JSONObject();
                JSONArray listaUsuarios = new JSONArray();

                datosUsuario.put("nombre", "fernando");
                datosUsuario.put("contraseña", "password");
                datosUsuario.put("edad", "19");
                datosUsuario.put("correo", "fernando@correo.com");
                listaUsuarios.add(datosUsuario);
                datosUsuario2.put("nombre", "ana");
                datosUsuario2.put("contraseña", "12345");
                datosUsuario2.put("edad", "30");
                datosUsuario2.put("correo", "ana@correo.com");
                listaUsuarios.add(datosUsuario2);

                FileWriter fw = new FileWriter(fUsuarios);
                fw.write(listaUsuarios.toJSONString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * devuelve un JSONArray que contiene a todos los usuarios registrados en la aplicación.
     */
    private JSONArray obtenerUsuariosJson() {
        JSONArray listaUsuarios;
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader(RUTA_FICHERO);
            Object obj = parser.parse(fr);
            listaUsuarios = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return listaUsuarios;

    }

    /**
     * devuelve la posición de un usuario dentro del array de usuarios. Si el usuario no está en el array, devuelve -1.
     * <p>
     * Se puede usar para hacer más fácil la implementación de otros métodos en este archivo, pero daba lugar a muchas equivocaciones y acabé no usándolo
     */
    private int obtenerPosicionUsuario(String nombreUsuario, JSONArray usuarios) {

        int position = -1;

        for (int i = 0; i < usuarios.size(); i++) {


            JSONObject object = (JSONObject) usuarios.get(i);
            String nombre = (String) object.get("nombre");

            if (nombreUsuario.equals(nombre)) {
                position = i;
                break;
            } else {
                position = -1;
            }
        }
        return position;
    }

    /**
     * devuelve todos los datos de un usuario, en formato JSONObject. Si el usuario no existe, devuelve null.
     */
    private JSONObject obtenerUsuarioJson(String nombreUsuario) {

        JSONArray listaUsuarios = obtenerUsuariosJson();
        JSONObject usuarioRegistrado = new JSONObject();

        for (Object obj : listaUsuarios) {
            JSONObject persona = (JSONObject) obj;
            if (persona.get("nombre").toString().equals(nombreUsuario)) {
                usuarioRegistrado = persona;
                return usuarioRegistrado;
            } else usuarioRegistrado = null;
        }
        return usuarioRegistrado;
    }

    /**
     * ejecuta la ventana de inicio de sesión.
     */
    public void ejecutar() {

        VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion(new AplicacionUsuarios());
        ventanaInicioSesion.setVisible(true);
    }

    /**
     * inicia sesión en base al usuario y contraseña introducidos.
     */
    public void iniciarSesion(String nombreUsuario, String contraseñaUsuario) {
        JSONArray listaUsuarios = obtenerUsuariosJson();

        for (Object obj : listaUsuarios) {
            JSONObject usuarios = (JSONObject) obj;
            String nombre = (String) usuarios.get("nombre");
            String contraseña = (String) usuarios.get("contraseña");
            if (nombreUsuario.equals(nombre)) {
                setValidUser(true);
            }
            if (nombreUsuario.equals(nombre) && contraseña.equals(contraseñaUsuario)) {
                setValidPass(true);
            }
        }


    }

    /**
     * cierra la sesión y vuelve a la ventana de inicio.
     */
    public void cerrarSesion() {
        setValidUser(false);
        setValidPass(false);
        mostrarVentanaInicioSesion();

    }

    /**
     * registra un usuario en el fichero JSON en función de los datos pasados por parámetro.
     */
    public void crearUsuario(String nombre, String contraseña, String edad, String correo) {
        JSONArray usuarios = obtenerUsuariosJson();
        try {
            JSONObject usuario = this.obtenerUsuarioJson(nombre);
            if (usuario != null) {
                JOptionPane.showMessageDialog(null,"El nombre de usuario ya está cogido","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                JSONObject datosUsuario = new JSONObject();
                datosUsuario.put("nombre", nombre);
                datosUsuario.put("contraseña", contraseña);
                datosUsuario.put("edad", edad);
                datosUsuario.put("correo", correo);
                usuarios.add(datosUsuario);

                FileWriter fw = new FileWriter(RUTA_FICHERO);
                fw.write(usuarios.toJSONString());
                fw.flush();
                fw.close();

            }
        } catch (IOException e) {}
    }

    /**
     * cambia la contraseña del usuario en el fichero JSON.
     */
    public void cambiarContraseña(String nombreUsuario, String nuevaContraseña) {
        try {

            JSONParser parser = new JSONParser();
            JSONArray listaUsuarios = (JSONArray) parser.parse(new FileReader(RUTA_FICHERO));
            for (int i = 0; i < listaUsuarios.size(); i++) {
                JSONObject user = (JSONObject) listaUsuarios.get(i);
                String nombre = (String) user.get("nombre");
                if (nombre.equals(nombreUsuario)) {
                    user.put("contraseña", nuevaContraseña);
                    FileWriter fw = new FileWriter(RUTA_FICHERO);
                    fw.write(listaUsuarios.toJSONString());
                    fw.flush();
                    fw.close();
                }
            }
        } catch (IOException | ParseException e) {
        }
    }

    /**
     * borrar el usuario del fichero JSON y cierra sesión.
     */
    public void borrarUsuario(String nombreUsuario) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray listaUsuarios = (JSONArray) parser.parse(new FileReader(RUTA_FICHERO));

            for (int i = 0; i < listaUsuarios.size(); i++) {
                JSONObject usuario = (JSONObject) listaUsuarios.get(i);
                String nombre = (String) usuario.get("nombre");
                if (nombre.equals(nombreUsuario)) {
                    listaUsuarios.remove(i);
                    i--;

                }
            }
            FileWriter fw = new FileWriter(RUTA_FICHERO);
            fw.write(listaUsuarios.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException | ParseException e) {
        }
    }

    /**
     * abre la ventana para crear un nuevo usuario.
     */
    public void mostrarVentanaCrearUsuario() {

        VentanaCrearUsuario ventanaCrearUsuario = new VentanaCrearUsuario(new AplicacionUsuarios());
        ventanaCrearUsuario.setVisible(true);

    }

    /**
     * abre la ventana en la que se muestran los datos del usuario.
     */
    public void mostrarVentanaVerUsuario(String nombreUsuario) {
        JSONObject usuario = obtenerUsuarioJson(nombreUsuario);
        String edad = usuario.get("edad").toString();
        String correo = usuario.get("correo").toString();
        VentanaVerUsuario ventanaVerUsuario = new VentanaVerUsuario(new AplicacionUsuarios(), nombreUsuario, edad, correo);
        ventanaVerUsuario.setVisible(true);
    }

    /**
     * abre la ventana que permite introducir una nueva contraseña.
     */
    public void mostrarVentanaCambiarContraseña(String nombreUsuario) {
        VentanaCambiarContraseña ventanaCambiarContraseña = new VentanaCambiarContraseña(new AplicacionUsuarios(), nombreUsuario);
        ventanaCambiarContraseña.setVisible(true);
    }

    /**
     * abre la ventana para confirmar el borrado del usuario.
     */
    public void mostrarVentanaBorrarUsuario(String nombreUsuario) {
        VentanaBorrarUsuario ventanaBorrarUsuario = new VentanaBorrarUsuario(new AplicacionUsuarios(), nombreUsuario);
        ventanaBorrarUsuario.setVisible(true);
    }

    public void mostrarVentanaMenuUsuario(String nombreUsuario) {
        VentanaMenuUsuario ventanaMenuUsuario = new VentanaMenuUsuario(new AplicacionUsuarios(), nombreUsuario);
        ventanaMenuUsuario.setVisible(true);
    }

    public void mostrarVentanaInicioSesion() {
        VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion(new AplicacionUsuarios());
        ventanaInicioSesion.setVisible(true);

    }
}
