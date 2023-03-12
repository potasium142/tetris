#version 400 core

in vec2 fragTextureCoord;
in float saturation;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main(void) {
    fragColor = texture(textureSampler, fragTextureCoord);
    fragColor.rgb *= saturation;
}
