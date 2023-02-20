#version 400 core

in vec2 fragTextureCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main(void) {
    vec2 UVcoord = vec2((fragTextureCoord.x / 12), fragTextureCoord.y);
    vec4 texture = texture(textureSampler, fragTextureCoord);

    if(texture.a != 1)
        discard;

    fragColor = texture;
}
