package engine;

import org.lwjgl.opengl.GL40;

public class Renderer {

    public void render(Object mesh) {
        GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);
        GL40.glBindVertexArray(mesh.meshID);

        GL40.glEnableVertexAttribArray(0);
        GL40.glEnableVertexAttribArray(1);

        GL40.glActiveTexture(GL40.GL_TEXTURE);
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, mesh.textureID);
        GL40.glDrawElements(GL40.GL_TRIANGLES, mesh.vertexCount, GL40.GL_UNSIGNED_INT, 0);

        GL40.glDisableVertexAttribArray(0);
        GL40.glDisableVertexAttribArray(1);

        GL40.glBindVertexArray(0);

    }
}
