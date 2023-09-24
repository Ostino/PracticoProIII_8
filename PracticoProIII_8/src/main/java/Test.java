import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private Imagen modelo;
    private final String OPERACION_REGEX =
            "^\\s*([a-zA-Z]+)\\s+([0-9]{1,3}|[a-zA-Z]+)\\s*$";
    private String comandoEntrada;
    private static Logger logger = LogManager.getLogger(Test.class);
    public Test(Imagen img) {
        modelo = img;
    }

    public void entrada(String linea) {
        comandoEntrada = linea;
    }

    public String procesarSalida() throws InterruptedException {
        boolean valido = validarEntrada();
        if (!valido) {
            return "ERROR: No entiendo la operacion";
        }

        Pattern patronExpReg = Pattern.compile(OPERACION_REGEX);
        Matcher m = patronExpReg.matcher(comandoEntrada);

        m.find();
        String AvoLap = m.group(1);
        String arg1 = null;
        int arg1int = 0;
        if (AvoLap.equals("Lapiz")) {
            arg1 = m.group(2);
        } else {
            arg1int = Integer.parseInt(m.group(2));
        }

        if (AvoLap.equals("Lapiz") && arg1.equals("arriba"))
            modelo.setPintable(false);
        logger.info("El lapiz subio");
        if (AvoLap.equals("Lapiz")&& arg1.equals("abajo"))
            modelo.setPintable(true);
        logger.info("El lapiz bajo");
        if (AvoLap.equals("Arriba"))
            modelo.movArriba(arg1int);
        logger.info("La tortuga se movio : "+arg1int+" Hacia arriba");
        if (AvoLap.equals("Abajo"))
            modelo.movAbajo(arg1int);
        logger.info("La tortuga se movio : "+arg1int+" Hacia abajo");
        if (AvoLap.equals("Izquierda"))
            modelo.movIzquierda(arg1int);
        logger.info("La tortuga se movio : "+arg1int+" Hacia la izquierda");
        if (AvoLap.equals("Derecha"))
            modelo.movDerecha(arg1int);
        logger.info("La tortuga se movio : "+arg1int+" Hacia la derecha");
        return "Accion valida";
    }

    private boolean validarEntrada() {

        if (comandoEntrada.matches(OPERACION_REGEX)) {
            return true;
        }
        return false;
    }
}