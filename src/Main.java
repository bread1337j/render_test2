import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void addplane(int x1, int y1, int z1, int x2, int y2, int z2, Window w){
        w.triangles.add(new Triangle(
                        new Vector3(x1, y1, z1),
                        new Vector3(x2, y1, z1),
                        new Vector3(x2, y2, z2)
                )
        );
        w.triangles.add(new Triangle(
                        new Vector3(x1, y1, z1),
                        new Vector3(x1, y2, z2),
                        new Vector3(x2, y2, z2)
                )
        );
    }
    public static void addSquare(int x1, int y1, int z1, int x2, int y2, int z2, Window w){
        addplane(x1,y1,z1,x2,y2, z1, w);
        addplane(x1,y1,z2,x2,y2, z2, w);

        addplane(x1,y1,z1, x2, y1, z2, w);
        addplane(x1,y2,z1, x2, y2, z2, w);

        addplane(x1, y1,z1, x1, y2, z2, w);
        addplane(x2, y1,z1, x2, y2, z2, w);
    }

    public static void main(String[] args){
        Window w = new Window();

        addSquare(0,0,0,100,100,100, w);
        addSquare(-100,0,-100,0,100,0, w);
        addSquare(100,0,100,200,100,200, w);

        addSquare(0,100,0,100,200,100, w);
        addSquare(-100,100,-100,0,200,0, w);
        addSquare(100,100,100,200,200,200, w);

        addSquare(0,-100,0,100,0,100, w);
        addSquare(-100,-100,-100,0,0,0, w);
        addSquare(100,-100,100,200,0,200, w);


        addSquare(0,-100,500,100,0,600, w);

        for(int i=0; i<1000; i++){
            addSquare(i*100, 0, 0, i*100+100, -100, 0, w);
        }

    }
}
