import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Ventana extends JFrame {

    private static Imagen modelo;

    Panel panel = new Panel(modelo);

    public Ventana() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());

        modelo = new Imagen(512, 512);
        //modelo.imagen4x4();
        modelo.imagenBlanca();

        Panel panel = new Panel(modelo);
        modelo.addObserver(panel);
        this.getContentPane().add(panel, BorderLayout.CENTER);

       /* JButton btn = new JButton("Achicar");
        btn.addActionListener(e -> {
            btnAchicar_clicked();
        });


        this.getContentPane().add(btn, BorderLayout.NORTH);

        btn = new JButton("Matriz de Transformacion");
        btn.addActionListener(e -> {
            btnTransformacion_clicked();

        });
        this.getContentPane().add(btn, BorderLayout.SOUTH);
        btn = new JButton("Agrandar");
        btn.addActionListener(e -> {
            btnAgrandar_clicked();
        });
        this.getContentPane().add(btn, BorderLayout.EAST);
*/

        this.setVisible(true);
        this.pack();

    }


    private void btnTransformacion_clicked() {
        modelo.setPintable(false);
        MatrizDeTransformacion m = new MatrizDeTransformacion();
        m.traslacion(modelo.getAncho(), 0);
        m.rotacion(-90);
        modelo.aplicarMatriz(m);
    }

    private void btnAchicar_clicked() {
        modelo.achicar(2);
    }

    private void btnAgrandar_clicked() {
        modelo.Agrandar(2);
    }


    public static void main(String[] args) throws Exception {
        new Ventana();

        ServerSocket srv = new ServerSocket(6969);
        System.out.println("Listo para escuchar en puerto 6969");
        Socket clt = srv.accept();
        System.out.println("Alguien se conecto y ahora tenemos un socket de comunicaion");
        BufferedReader entrada =
                new BufferedReader(new InputStreamReader(clt.getInputStream()));
        System.out.println("Creo objeto de entrada con socket");
        PrintWriter salida =
                new PrintWriter(clt.getOutputStream());
        System.out.println("Creo objeto de salida con socket");
        String linea = entrada.readLine();
        Test protocolo = new Test(modelo);
        while (!linea.equals("fin")) {
            System.out.println("<<< " + linea);
            protocolo.entrada(linea);
            String respuesta = protocolo.procesarSalida();
            salida.println(respuesta);
            salida.flush();
            System.out.println(">>> " + respuesta);
            linea = entrada.readLine();
        }
        clt.close();
        srv.close();
        System.out.println("Cerrado todo y comunicacion concluida");
    }
}