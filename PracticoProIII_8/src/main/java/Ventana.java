import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.getContentPane().add(panel, BorderLayout.WEST);
        JMenuBar bar = new JMenuBar();

        JMenu menu = new JMenu("Operaciones");
        JMenuItem item = new JMenuItem("Achicar");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAchicar_clicked();
            }
        });
        menu.add(item);
        item = new JMenuItem("Matriz de Transformacion");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTransformacion_clicked();
            }
        });
        menu.add(item);
        item = new JMenuItem("Agrandar");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAgrandar_clicked();
            }
        });
        menu.add(item);
        item = new JMenuItem("Limpiar");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLimpiar();
            }
        });
        menu.add(item);
        bar.add(menu);

        menu = new JMenu("Colores");
        item = new JMenuItem("Rojo");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRojo();
            }
        }));
        menu.add(item);
        item = new JMenuItem("Azul");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAzul();
            }
        }));
        menu.add(item);
        item = new JMenuItem("Verde");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnVerde();
            }
        }));
        menu.add(item);
        bar.add(menu);
        item = new JMenuItem("Rellenar");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRellenar();
            }
        }));
        menu.add(item);
        bar.add(menu);
        item = new JMenuItem("Selec. Color Hex");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEscogerColor();
            }
        }));
        menu.add(item);
        bar.add(menu);
        item = new JMenuItem("Selec. Color RGB");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEscogerColorRGB();
            }
        }));
        menu.add(item);
        bar.add(menu);
        item = new JMenuItem("Borrador");
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBorrador();
            }
        }));
        menu.add(item);
        bar.add(menu);

        this.setJMenuBar(bar);

       /*JButton btnR = new JButton("Rojo");
        btnR.addActionListener(e -> {
           btnRojo();
        });
        //btnR.setBounds(520,40,60,24);
        panelBotones.add(btnR);
        JButton btnA = new JButton("Azul");
        btnA.addActionListener(e -> {
            btnAzul();
        });
        panelBotones.add(btnA);
        JButton btnV = new JButton("Verde");
        btnV.addActionListener(e -> {
            btnVerde();
        });
        panelBotones.add(btnV);
        //this.getContentPane().add(panel, BorderLayout.EAST);
        //this.getContentPane().add(btnV, BorderLayout.EAST);
        /*
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

    private void btnEscogerColorRGB() {
        String valorHexadecimal = JOptionPane.showInputDialog("Por favor los valores rgb");
        valorHexadecimal.replaceAll("\\s","");
        String[] valores = valorHexadecimal.split(",");
        String hexadecimal= "00";
        for (String valor : valores) {
            try {
                int numero = Integer.parseInt(valor);
                 hexadecimal =hexadecimal+ Integer.toHexString(numero);
            } catch (NumberFormatException e) {
            }
        }
        modelo.setColorHex(Integer.parseInt(hexadecimal,16));
    }

    private void btnRellenar() {
        modelo.setBaldePintura(!modelo.isBaldePintura());
    }

    private void btnTransformacion_clicked() {
        //modelo.setPintable(false);
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

    private void btnRojo() {modelo.setColorHex(0x00FF0000);}
    private void btnVerde() {
        modelo.setColorHex(0x0000FF00);
    }
    private void btnAzul() {modelo.setColorHex(0x000000FF);}
    private void btnEscogerColor() {
        String valorHexadecimal = JOptionPane.showInputDialog("Por favor, ingresa un valor hexadecimal:");
        String resultString = valorHexadecimal.substring(2);
    if (modelo.CheckHex("0x"+resultString)){
        try {
            System.out.println(resultString+"  Este");
        modelo.setColorHex(Integer.parseInt(resultString,16));
        }catch (NumberFormatException e){
         }
    }
    }
    private  void btnBorrador(){
        modelo.setBorrador(!modelo.isBorrador());
    }
    private void btnLimpiar(){
        System.out.println("entre");
        modelo.imagenBlanca();
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