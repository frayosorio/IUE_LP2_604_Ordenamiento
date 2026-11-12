package controladores;

import servicios.DocumentoServicio;
import vistas.OrdenamientoVista;

public class OrdenamientoControlador {

    private OrdenamientoVista vista;

    public OrdenamientoControlador(OrdenamientoVista vista) {
        this.vista = vista;

        //this.vista.mostrarDatos(DocumentoServicio.getEncabezados(), null);
        this.vista.setOrdenarBurbuja(evento -> ordenarBurbuja());
        this.vista.setOrdenarRapido(evento -> ordenarRapido());

        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/Datos.csv";
        DocumentoServicio.cargarDatos(nombreArchivo);
        mostrar();
    }

    private void mostrar(){
        vista.mostrarDatos(DocumentoServicio.getEncabezados(), DocumentoServicio.getDatos());
    }

    private void ordenarBurbuja() {

    }

    private void ordenarRapido() {

    }

}
