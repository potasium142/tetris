#version 400 core

in vec2 position;
in vec2 textureCoord;

out vec2 fragTextureCoord;

uniform mat4 pos;


void main(void) {

    gl_Position = pos * vec4(position, 1, 1.0);

    fragTextureCoord = textureCoord;
}
