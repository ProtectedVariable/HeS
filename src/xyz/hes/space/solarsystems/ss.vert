#version 330 core

layout (location = 0) in vec3 vertex;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 texCoord;

uniform mat4 ml_mat, vw_mat, pr_mat;

uniform vec3 lightPos;

out TO_FRAG {
  vec3 normal;
  vec3 toLight;
} lightIn;

void main()
{
  vec4 position = vec4(vertex, 1.0) * ml_mat;
  gl_Position = position * vw_mat * pr_mat;
  lightIn.normal = (vec4(normal, 0.0) * ml_mat).xyz;
  lightIn.toLight = lightPos-position.xyz;
}
