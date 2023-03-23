#version 400 core

out vec4 fragColor;

in vec2 fragTextureCoord;
uniform sampler2D textureSampler;

void main(void) {
    fragColor = texture(textureSampler, fragTextureCoord);
}
