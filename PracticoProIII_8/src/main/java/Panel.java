import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Panel extends JPanel implements PropertyChangeListener, MouseMotionListener , MouseListener {
    private Imagen modelo;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    private int posX;
    private int posY;
    public Panel(Imagen img) {

        modelo = img;
        this.addMouseMotionListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        modelo.dibujar(g);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (modelo.isPintable() == true)
            modelo.punto(e.getX(), e.getY(), 20);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
       // posX= e.getX();
       // posY= e.getY();
        //modelo.Airededor(posX,posY);
        System.out.println("Me hiciste click");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}