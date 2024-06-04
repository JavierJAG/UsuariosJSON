package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.AplicacionUsuarios;

public class VentanaCrearUsuario extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JLabel etiquetaCrearUsuario;
    private JLabel etiquetaNombre;
    private JLabel etiquetaContraseña;
    private JLabel etiquetaEdad;
    private JLabel etiquetaCorreo;
    private JTextField textoNombre;
    private JTextField textoContraseña;
    private JTextField textoEdad;
    private JTextField textoCorreo;
    private JButton btnCrear;
    private JButton btnCancelar;
    private AplicacionUsuarios app;

    public VentanaCrearUsuario(AplicacionUsuarios app) {
        this.app = app;
        setTitle("Aplicación usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 322, 385);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        etiquetaNombre = new JLabel("Nombre:");
        etiquetaNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaNombre.setBounds(65, 56, 68, 14);
        contentPane.add(etiquetaNombre);

        etiquetaCrearUsuario = new JLabel("CREAR USUARIO");
        etiquetaCrearUsuario.setBounds(83, 11, 154, 20);
        etiquetaCrearUsuario.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(etiquetaCrearUsuario);

        textoNombre = new JTextField();
        textoNombre.setBounds(65, 81, 214, 20);
        contentPane.add(textoNombre);
        textoNombre.setColumns(10);

        etiquetaContraseña = new JLabel("Contraseña:");
        etiquetaContraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaContraseña.setBounds(65, 112, 68, 14);
        contentPane.add(etiquetaContraseña);

        textoContraseña = new JTextField();
        textoContraseña.setColumns(10);
        textoContraseña.setBounds(65, 137, 214, 20);
        contentPane.add(textoContraseña);

        etiquetaEdad = new JLabel("Edad:");
        etiquetaEdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaEdad.setBounds(65, 168, 68, 14);
        contentPane.add(etiquetaEdad);

        textoEdad = new JTextField();
        textoEdad.setColumns(10);
        textoEdad.setBounds(65, 193, 214, 20);
        contentPane.add(textoEdad);

        etiquetaCorreo = new JLabel("Correo electrónico:");
        etiquetaCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaCorreo.setBounds(65, 224, 214, 14);
        contentPane.add(etiquetaCorreo);

        textoCorreo = new JTextField();
        textoCorreo.setColumns(10);
        textoCorreo.setBounds(65, 249, 214, 20);
        contentPane.add(textoCorreo);

        btnCrear = new JButton("Crear");
        btnCrear.setBounds(172, 299, 89, 23);
        btnCrear.addActionListener(this);
        contentPane.add(btnCrear);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(39, 299, 89, 23);
        btnCancelar.addActionListener(this);
        contentPane.add(btnCancelar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == btnCrear) {
            if (
                    textoNombre.getText() != null && !textoNombre.getText().isBlank() &&
                            textoContraseña.getText() != null && !textoContraseña.getText().isBlank() &&
                            textoEdad.getText() != null && !textoEdad.getText().isBlank() &&
                            textoCorreo.getText() != null && !textoCorreo.getText().isBlank()) {
                app = new AplicacionUsuarios();
                app.crearUsuario(textoNombre.getText(), textoContraseña.getText(), textoEdad.getText(), textoCorreo.getText());
                this.dispose();
                VentanaInicioSesion inicio = new VentanaInicioSesion(new AplicacionUsuarios());
                inicio.setVisible(true);
            }else {
                JOptionPane.showMessageDialog(this,"No puede haber campos en blanco");
            }
        } else if (e.getSource() == btnCancelar) {
            this.dispose();
            VentanaInicioSesion inicio = new VentanaInicioSesion(new AplicacionUsuarios());
            inicio.setVisible(true);

        }

    }
}
