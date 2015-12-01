#version 330 core

uniform vec3 color;
//in vec2 texCoord;

out vec4 outColor;

uniform sampler2D tex;

void main() {
	//outColor = texture(tex, texCoord);
	outColor = vec4(color, 1.0);
}
