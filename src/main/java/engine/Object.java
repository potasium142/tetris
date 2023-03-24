package engine;

public class Object {
    public int meshID;
    public int vertexCount;
    public int textureID;
    public int vboID;

    public Object(int ID, int vertexCount) {
        this.meshID = ID;
        this.vertexCount = vertexCount;
    }

    public Object(int ID, int vertexCount, int vboID) {
        this.meshID = ID;
        this.vertexCount = vertexCount;
        this.vboID = vboID;
    }

}
