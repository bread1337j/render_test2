import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Window {
    static boolean sameSide(Vector3 A, Vector3 B, Vector3 C, Vector3 p){
        Vector3 V1V2 = new Vector3(B.x - A.x,B.y - A.y,B.z - A.z);
        Vector3 V1V3 = new Vector3(C.x - A.x,C.y - A.y,C.z - A.z);
        Vector3 V1P = new Vector3(p.x - A.x,p.y - A.y,p.z - A.z);

        // If the cross product of vector V1V2 and vector V1V3 is the same as the one of vector V1V2 and vector V1p, they are on the same side.
        // We only need to judge the direction of z
        double V1V2CrossV1V3 = V1V2.x * V1V3.y - V1V3.x * V1V2.y;
        double V1V2CrossP = V1V2.x * V1P.y - V1P.x * V1V2.y;

        return V1V2CrossV1V3 * V1V2CrossP >= 0;
    }
    List<Triangle> triangles = new ArrayList<>();
    int anglex;
    int angley;
    double zoom = 1.01;
    JFrame fr = new JFrame();
    JPanel pn = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());


            double heading = Math.toRadians(anglex);
            Matrix3 headingTransform = new Matrix3(new double[]{
                    Math.cos(heading), 0, -Math.sin(heading),
                    0, 1, 0,
                    Math.sin(heading), 0, Math.cos(heading)
            });
            double pitch = Math.toRadians(angley);
            Matrix3 pitchTransform = new Matrix3(new double[]{
                    1, 0, 0,
                    0, Math.cos(pitch), Math.sin(pitch),
                    0, -Math.sin(pitch), Math.cos(pitch)
            });
// Merge matrices in advance
            Matrix3 transform = headingTransform.multiply(pitchTransform);


            g2.translate(getWidth() / 2, getHeight() / 2);
            g2.setColor(Color.WHITE);
            for(Triangle bebra : triangles){
                Vector3 p1 = transform.transform(bebra.p1);
                Vector3 p2 = transform.transform(bebra.p2);
                Vector3 p3 = transform.transform(bebra.p3);
                Vector3 p4 = new Vector3(
                        (bebra.p1.x + bebra.p2.x + bebra.p3.x) / 3,
                        (bebra.p1.y + bebra.p2.y + bebra.p3.y) / 3,
                        (bebra.p1.z + bebra.p2.z + bebra.p3.z) / 3
                );
                System.out.println(p4);
                Path2D path = new Path2D.Double();
                path.moveTo(p1.x, p1.y);
                path.lineTo(p2.x, p2.y);
                path.lineTo(p3.x, p3.y);
                path.closePath();
                AffineTransform tx = new AffineTransform();
                tx.scale((double) 1 / zoom, (double) 1 / zoom);
                path.transform(tx);
                g2.draw(path);
            }


        }
    };

    public Window(){
        Container pane = fr.getContentPane();
        pane.setLayout(new BorderLayout());

        // panel to display render results
        pane.add(pn, BorderLayout.CENTER);

        fr.setSize(600, 600);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pn.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double yi = 180.0 / pn.getHeight();
                double xi = 180.0 / pn.getWidth();
                anglex = (int) (e.getX() * xi);
                angley = -(int) (e.getY() * yi);
                pn.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        fr.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                double yi = 180.0 / pn.getHeight();
                double xi = 180.0 / pn.getWidth();
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    angley -= (int) (10 * xi);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    angley += (int) (10 * xi);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    anglex += (int) (10 * xi);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    anglex -= (int) (10 * xi);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    zoom += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL){
                    zoom -= 0.1;
                }
                /*if (e.getKeyCode() == KeyEvent.VK_W){
                    camz += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    camz -= 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    camx += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    camx -= 1;
                }*/
                pn.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

}
