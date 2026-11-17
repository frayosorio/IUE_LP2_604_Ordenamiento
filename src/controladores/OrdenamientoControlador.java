package controladores;

import servicios.CronometroServicio;
import servicios.DocumentoServicio;
import vistas.OrdenamientoVista;

public class OrdenamientoControlador {

    private OrdenamientoVista vista;

    private boolean ejecutando;

    public OrdenamientoControlador(OrdenamientoVista vista) {
        this.vista = vista;

        //this.vista.mostrarDatos(DocumentoServicio.getEncabezados(), null);
        this.vista.setOrdenarBurbuja(evento -> ordenarBurbuja());
        this.vista.setOrdenarRapido(evento -> ordenarRapido());

        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/Datos.csv";
        DocumentoServicio.cargarDatos(nombreArchivo);
        mostrar();
    }

    private void mostrar() {
        vista.mostrarDatos(DocumentoServicio.getEncabezados(), DocumentoServicio.getDatos());
    }

    private void ordenarBurbuja() {
        if (vista.getCriterio() < 0)
            return;
        ejecutarProceso(() -> {
            DocumentoServicio.ordenarBurbuja(vista.getCriterio());
        });
    }

    private void ordenarRapido() {
        if (vista.getCriterio() < 0)
            return;
        ejecutarProceso(() -> {
            DocumentoServicio.ordenarRapido(vista.getCriterio());
        });
    }

    //metodo generico para todos los ordenamientos
    //que ejecuta 2 hilos:
    //1. El ordenamiento
    //2. El cronometro
    private void ejecutarProceso(Runnable accion) {
        CronometroServicio.iniciarCronometro();
        ejecutando = true;

        //hilo que ejecuta el ordenamiento
        new Thread(() -> {
            accion.run();
            ejecutando = false;
            mostrar();
        }).start();

        //hilo que ejecuta el cronometro
        new Thread(() -> {
            while (ejecutando) {
                CronometroServicio.pausarMilisegundos(50);
                vista.setTiempo(CronometroServicio.getTextoTiempoCronometro());
            }
        }).start();
    }

}
