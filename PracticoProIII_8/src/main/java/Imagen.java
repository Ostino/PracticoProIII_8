import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static java.lang.Thread.sleep;

public class Imagen implements IDibujador {
    private int alto;
    private int ancho;
    private int[][] pixeles;
    private PropertyChangeSupport observado;
    private boolean pintable;
    private int LapizFinalX = 256;
    private int LapizFinalY = 256;
    private int tempo =10;
    private int contador;

    public Imagen(int w, int h) {
        ancho = w;
        alto = h;
        pixeles = new int[ancho][alto];
        observado = new PropertyChangeSupport(this);
        pintable = true;

    }

    @Override
    public void dibujar(Graphics g) {
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                g.setColor(new Color(pixeles[i][j]));
                g.drawLine(i,j,i,j);
            }
        }
    }
    public void imagenBlanca(){
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                pixeles[i][j] = 0x00FFFFFF;
            }
        }
    }
    public void addObserver(PropertyChangeListener listener) {
        observado.addPropertyChangeListener(listener);
    }

    public void achicar(int t) {
        int[][] nuevosPixeles = new int[ancho/t][alto/t];

        for (int i = 0; i < ancho; i+=t) {
            for (int j = 0; j < alto; j+=t) {
                nuevosPixeles[i/2][j/2] = pixeles[i][j];
            }
        }
        pixeles = nuevosPixeles;
        ancho = ancho/t;
        alto = alto /t;
        observado.firePropertyChange("IMAGEN",true, false);
    }

    public void Agrandar(int t) {
        int[][] nuevosPixeles = new int[ancho*t][alto*t];
        for (int i = 0; i < ancho*t; i++) {
            for (int j = 0; j < alto*t; j++) {
                nuevosPixeles[i][j] = pixeles[i/t ][j/t];
            }
        }
        pixeles = nuevosPixeles;
        ancho = ancho*t;
        alto = alto *t;
        observado.firePropertyChange("IMAGEN",true, false);
    }

    public void aplicarMatriz(MatrizDeTransformacion m) {
        int[][] nuevosPixeles = new int[ancho][alto];
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Vectores entrada = new Vectores(i,j);
                Vectores salida = m.aplicarMatriz(entrada);
                if ((int)salida.getX() >= 0 &&
                        (int)salida.getX() < ancho &&
                        (int)salida.getY() >= 0 &&
                        (int)salida.getY() < alto) {
                    nuevosPixeles[(int)salida.getX()][(int)salida.getY()] =
                            pixeles[i][j];
                }
            }
        }
        pixeles = nuevosPixeles;
        observado.firePropertyChange("IMAGEN",true, false);
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int[][] getPixeles() {
        return pixeles;
    }

    public void setLapizFinalY(int y){ LapizFinalY=y;
    }
    public void setLapizFinalX(int x){LapizFinalX=x;
    }
    public void Airededor(int x, int y){
        pixeles[x][y]=0;
        contador=0;
        if (pixeles[x][y-1]==0x00FFFFFF ){
            pixeles[x][y-1] = 0x00000000;
            observado.firePropertyChange("IMAGEN",true, false);
            Airededor(x,y-1);
        }else{
            contador++;}
        if (pixeles[x-1][y]==0x00FFFFFF ){
            pixeles[x-1][y] = 0x00000000;
            observado.firePropertyChange("IMAGEN",true, false);
            Airededor(x-1,y);

        }else{
            contador++;}
        if (pixeles[x][y+1]==0x00FFFFFF ){
            pixeles[x][y+1] = 0x00000000;
            observado.firePropertyChange("IMAGEN",true, false);
            Airededor(x,y+1);
        }else{
            contador++;}
        if (pixeles[x+1][y]==0x00FFFFFF ){
            pixeles[x+1][y] = 0x00000000;
            observado.firePropertyChange("IMAGEN",true, false);
            Airededor(x+1,y);
        }else{
            contador++;}
        if(contador == 4){
            return ;
        }



    }
    public void movArriba(int num) throws InterruptedException {
        if (isPintable()) {
            for (int i = 0; i < num; i++) {
                pixeles[LapizFinalX][LapizFinalY] = 0;
                LapizFinalY--;
                observado.firePropertyChange("IMAGEN", true, false);
                sleep(tempo);
            }
        }
    }
    public void movAbajo(int num) throws InterruptedException {
        if (isPintable()) {
            for (int i = 0; i < num; i++) {
                pixeles[LapizFinalX][LapizFinalY] = 0;
                LapizFinalY++;
                observado.firePropertyChange("IMAGEN",true, false);
                sleep(tempo);
            }
        }
    }
    public void movIzquierda(int num) throws InterruptedException {
        if (isPintable()) {
            for (int i = 0; i < num; i++) {
                pixeles[LapizFinalX][LapizFinalY] = 0;
                LapizFinalX--;
                observado.firePropertyChange("IMAGEN",true, false);
                sleep(tempo);
            }
        }
    }
    public void movDerecha(int num) throws InterruptedException {
        if (isPintable()) {
            for (int i = 0; i < num; i++) {
                pixeles[LapizFinalX][LapizFinalY] = 0;
                LapizFinalX++;
                observado.firePropertyChange("IMAGEN",true, false);
                sleep(tempo);
            }
        }
    }
    public void punto(int x, int y, int tamano) {

        for (int i = x - tamano / 2; i < x + tamano / 2; i++) {
            for (int j = y - tamano / 2; j < y + tamano / 2; j++) {
                if (i >= 0 && i < ancho &&
                        j >= 0 && j < alto) {
                    pixeles[i][j] = 0;
                }
            }
        }
        observado.firePropertyChange("IMAGEN", true, false);
    }

    public boolean isPintable() {
        return pintable;
    }

    public void setPintable(boolean pintable) {
        this.pintable = pintable;
    }
}