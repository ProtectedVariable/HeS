#version 330 core

layout (location = 0) in vec3 vertex;
//layout (location = 2) in vec2 inTexCoord;
//layout (location = 3) in vec4 color;

//out vec2 texCoord;

uniform mat4 ml_mat;
uniform mat4 vw_mat;
uniform mat4 pr_mat;

void main() {
	gl_PointSize = 4.0;
	gl_Position = vec4(vertex,1.0) * ml_mat * vw_mat * pr_mat;
	//texCoord = inTexCoord;
}