#version 330

layout (location = 0) vec2 vertex;
layout (location = 1) vec2 tex;

out vec2 texCoord;

void main()
{
  gl_Position = vec4(vertex, 0, 1);
  texCoord = tex;
}
