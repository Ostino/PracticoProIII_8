import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrizDeTransformacion {
    private double[][] matriz;
    private static Logger logger = LogManager.getLogger(MatrizDeTransformacion.class);

    public MatrizDeTransformacion() {
        matriz = new double[3][3];

        matriz[0][0] = 1;
        matriz[0][1] = 0;
        matriz[0][2] = 0;

        matriz[1][0] = 0;
        matriz[1][1] = 1;
        matriz[1][2] = 0;

        matriz[2][0] = 0;
        matriz[2][1] = 0;
        matriz[2][2] = 1;
    }

    public Vectores aplicarMatriz(Vectores xy) {
        double[] vector3 = new double[3];
        vector3[0] = xy.getX();
        vector3[1] = xy.getY();
        vector3[2] = 1.0;

        double[] resultado = new double[3];
        resultado[0] = matriz[0][0] * vector3[0] +
                matriz[0][1] * vector3[1] +
                matriz[0][2] * vector3[2];
        resultado[1] = matriz[1][0] * vector3[0] +
                matriz[1][1] * vector3[1] +
                matriz[1][2] * vector3[2];
        resultado[2] = matriz[2][0] * vector3[0] +
                matriz[2][1] * vector3[1] +
                matriz[2][2] * vector3[2];

        return new Vectores(resultado[0], resultado[1]);
    }

    public void rotacion(int grados) {
        double r=grados*Math.PI/180.0;
        matriz[0][0] = Math.cos(r);
        matriz[0][1] = Math.sin(r);
        matriz[1][0] = -Math.sin(r);
        matriz[1][1] = Math.cos(r);
        //System.out.println("La imagen se rotó :"+grados+" grados");
        logger.info("La imagen se rotó :"+r+"º");
    }

    public void traslacion(int x, int y) {
        matriz[0][2] = (double)x;
        matriz[1][2] = (double)y;
        logger.info("La imagen se movio");
    }
}