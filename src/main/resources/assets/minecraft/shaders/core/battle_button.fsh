#version 150

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform vec4 ColorModulator;

in vec2 texCoord0;
in float gameTime;

out vec4 fragColor;

void main() {
    vec2 texCoords = texCoord0.xy * 6;
    texCoords.y = texCoords.y / 2.1;
    texCoords.x += gameTime * 3;

    vec4 color = texture(Sampler0, texCoord0);

    if (color.a > 0.9 && (color.x != 1.0 || color.y != 1.0 || color.z != 1.0)) {
        vec4 newColor = texture(Sampler1, texCoords);
        color = mix(color, newColor, newColor.a);
    }

    if (color.a == 0.0) {
        discard;
    }
    fragColor = color * ColorModulator;
}
