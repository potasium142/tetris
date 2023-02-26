package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL40;
import org.lwjgl.system.MemoryUtil;

public class Window {
    private double lastFrametime;
    private double deltaFrametime;
    private int width, height;
    private String title;
    protected long window;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init() {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW");
        System.out.println("GLFW initialized");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create GLFW window");

        // GLFW.glfwSetFramebufferSizeCallback(window, ((window, width, height) -> {
        // this.width = width;
        // this.height = height;
        // }));

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (vidMode.width() - width) >> 1, (vidMode.height() - height) >> 1);

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        // GL40.glEnable(GL40.GL_DEPTH_TEST);
        GL40.glEnable(GL40.GL_STENCIL_TEST);
        GL40.glEnable(GL40.GL_CULL_FACE);
        GL40.glCullFace(GL40.GL_BACK);

        System.out.println("\nWindow created");
        System.out.println("Resolution : " + this.width + "x" + this.height);
    }

    public void update() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();

        double currentFrametime = getCurrentTime();
        deltaFrametime = currentFrametime - lastFrametime;
        lastFrametime = currentFrametime;
    }

    public void cleanup() {

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        System.out.println("\nWindow cleanup successfully");
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    private double getCurrentTime() {
        return GLFW.glfwGetTime();
    }

    public double getDeltaFrametimeMS() {
        return deltaFrametime;
    }

    public double getDeltaFrametimeS() {
        return deltaFrametime * 1000;
    }

}
