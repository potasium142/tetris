package game.UI;

import org.lwjgl.opengl.GL40;

import engine.Object;

public class UIRender {

    protected UIShader shader;
    Object UI;

    public UIRender(UIShader shader, Object uI) {
        this.shader = shader;
        UI = uI;
    }

    public void render() {

        shader.start();
        // GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);
        GL40.glBindVertexArray(UI.meshID);

        GL40.glEnableVertexAttribArray(0);
        GL40.glEnableVertexAttribArray(1);

        GL40.glEnable(GL40.GL_BLEND);
        GL40.glBlendFunc(GL40.GL_SRC_ALPHA, GL40.GL_ONE_MINUS_SRC_ALPHA);

        GL40.glActiveTexture(GL40.GL_TEXTURE);
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, UI.textureID);

        renderUI();

        GL40.glDisable(GL40.GL_BLEND);

        GL40.glDisableVertexAttribArray(0);
        GL40.glDisableVertexAttribArray(1);

        GL40.glBindVertexArray(0);
        shader.stop();
    }

    public void renderUI() {
        GL40.glDrawElements(GL40.GL_TRIANGLES, UI.vertexCount, GL40.GL_UNSIGNED_INT, 0);
    };
}
