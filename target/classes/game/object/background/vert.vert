#version 400 core

in vec3 position;
in vec2 textureCoord;

out vec2 fragTextureCoord;

void main(void) {

    gl_Position = vec4(position.xyz, 1.0);

    fragTextureCoord = textureCoord;
}
