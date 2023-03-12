#version 400 core

in vec2 position;
in vec2 textureCoord;

out vec2 fragTextureCoord;
out float saturation;

uniform mat4 titleLocation;
uniform float titleIndex;
uniform float visibility;

const float o12 = .083333333f;

void main(void) {

    saturation = visibility;

    gl_Position = titleLocation * vec4(position, 1, 1.0);

    fragTextureCoord = vec2(textureCoord.x + o12 * titleIndex, textureCoord.y);
}
