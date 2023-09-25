import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private Imagen modelo;
    private boolean rgbflag;
    private boolean hexflag;
    private boolean opflag;
    private final String OPERACION_REGEX =
            "^\\s*([a-zA-Z]+)\\s+([0-9]{1,3}|[a-zA-Z]+)*\\s*$";
    private final String Operacion_regex_rgb =
                   "^\\s*(rgb)\\s+([0-9]{1,3}),([0-9]{1,3}),([0-9]{1,3})\\s*$";
    private final String Operacion_regex_hex =
            "^\\s*(hex)\\s+(0x[A-Fa-f0-9]{8})\\s*$";
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

        if (rgbflag) {
            Pattern patronExpRegRGB = Pattern.compile(Operacion_regex_rgb);
            Matcher RGB = patronExpRegRGB.matcher(comandoEntrada);
            RGB.find();
            int r= (Integer.parseInt(RGB.group(2))<=255)? Integer.parseInt(RGB.group(2)) : 255;
            int g= (Integer.parseInt(RGB.group(3))<=255)? Integer.parseInt(RGB.group(3)) : 255;
            int b= (Integer.parseInt(RGB.group(4))<=255)? Integer.parseInt(RGB.group(4)) : 255;
            String hexa = "00";
                    String hexadecimal = Integer.toHexString(r);
                    hexa = hexa + hexadecimal;
                    hexadecimal = Integer.toHexString(g);
                    hexa = hexa + hexadecimal;
                    hexadecimal = Integer.toHexString(b);
                    hexa = hexa + hexadecimal;
                    modelo.setColorHex(Integer.parseInt(hexa,16));
                    rgbflag= false;
        }
        if (hexflag) {
           Pattern patronExpRegHEX = Pattern.compile(Operacion_regex_hex);
            Matcher HEX = patronExpRegHEX.matcher(comandoEntrada);
            HEX.find();
            String resultString = HEX.group(2).substring(2);
            if (modelo.CheckHex("0x"+resultString)){
                try {
                    modelo.setColorHex(Integer.parseInt(resultString,16));
                }catch (NumberFormatException e){
                }
            }
            System.out.println(HEX.group(2));
            hexflag= false;
        }
        if(opflag) {
            Pattern patronExpReg = Pattern.compile(OPERACION_REGEX);
            Matcher operacion = patronExpReg.matcher(comandoEntrada);
            operacion.find();
            String AvoLap = operacion.group(1);
            String arg1 = null;
            int arg1int = 0;
            if (AvoLap.equals("Lapiz")) {
                arg1 = operacion.group(2);
            } else {
                arg1int = Integer.parseInt(operacion.group(2));
            }
            if (AvoLap.equalsIgnoreCase("Lapiz") && arg1.equalsIgnoreCase("arriba"))
                modelo.setPintable(false);
            logger.info("El lapiz subio");
            if (AvoLap.equalsIgnoreCase("Lapiz") && arg1.equalsIgnoreCase("abajo"))
                modelo.setPintable(true);
            logger.info("El lapiz bajo");
            if (AvoLap.equalsIgnoreCase("Arriba"))
                modelo.movArriba(arg1int);
            logger.info("La tortuga se movio : " + arg1int + " Hacia arriba");
            if (AvoLap.equalsIgnoreCase("Abajo"))
                modelo.movAbajo(arg1int);
            logger.info("La tortuga se movio : " + arg1int + " Hacia abajo");
            if (AvoLap.equalsIgnoreCase("Izquierda"))
                modelo.movIzquierda(arg1int);
            logger.info("La tortuga se movio : " + arg1int + " Hacia la izquierda");
            if (AvoLap.equalsIgnoreCase("Derecha"))
                modelo.movDerecha(arg1int);
            logger.info("La tortuga se movio : " + arg1int + " Hacia la derecha");
            opflag=false;
            return "Accion valida";
        }
        return "Ingrese color";
    }
    private boolean validarEntrada() {
        if (comandoEntrada.matches(Operacion_regex_rgb)){
            System.out.println("Aprobado rgb");
            rgbflag = true;
            return true;
        }
        if (comandoEntrada.matches(Operacion_regex_hex)){
            System.out.println("Aprobado hex");
            hexflag = true;
            return true;
        }
        if (comandoEntrada.matches(OPERACION_REGEX) ) {
            opflag = true;
            return true;
        }
        return false;
    }

}