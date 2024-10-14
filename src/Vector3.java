public class Vector3 {
    double x;
    double y;
    double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(int x, int y, int z){
        this.x = (double)x;
        this.y = (double)y;
        this.z = (double)z;
    }

    public String toString() {
        return "<" + this.x + " , " + this.y + " , " + this.z + ">";
    }
}
