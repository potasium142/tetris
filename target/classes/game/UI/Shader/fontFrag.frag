#version 400 core

in vec2 fragTextureCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float visibility;

void main(void) {
    fragColor = texture(textureSampler, fragTextureCoord);
    fragColor.a *= visibility;
}
