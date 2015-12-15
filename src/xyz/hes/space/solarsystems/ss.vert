#version 330 core

layout (location = 0) in vec3 vertex;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 texCoord;

uniform mat4 ml_mat, vw_mat, pr_mat;

void main()
{
  gl_Position = vec4(vertex, 1.0) * ml_mat * vw_mat * pr_mat;
}
