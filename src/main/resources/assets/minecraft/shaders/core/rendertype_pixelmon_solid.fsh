#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 col = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    col.rgb = mix(overlayColor.rgb, col.rgb, overlayColor.a);
    col *= lightMapColor;
    col = linear_fog(col, vertexDistance, FogStart, FogEnd, FogColor);

    vec4 outlineCol = vec4(0, 1, 0, 0.8);

    float CameraFacingPercentage = dot(gl_FragCoord, normal);
    fragColor = col * CameraFacingPercentage + outlineCol * (1.0 - CameraFacingPercentage);
}