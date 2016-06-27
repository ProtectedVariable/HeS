#version 330

layout (location = 0) in vec2 vertex;
layout (location = 2) in vec2 inTexCoord;

uniform mat4 ml_mat;
uniform mat4 vw_mat;
uniform mat4 pr_mat;

out vec2 texCoord;

void main() {
  gl_Position = vec4(vertex, 0.0, 1.0) * ml_mat * vw_mat * pr_mat;
  texCoord = inTexCoord;
}
