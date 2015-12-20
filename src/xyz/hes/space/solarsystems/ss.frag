#version 330 core

out vec4 outColor;

in TO_FRAG {
  vec3 normal;
  vec3 toLight;
} lightIn;

uniform int sun;

void main()
{
  vec3 unitNormal = normalize(lightIn.normal);
  vec3 unitLightVector = normalize(lightIn.toLight);
  float nDl = dot(unitNormal, unitLightVector)+0.2;
  float brightness = max(nDl, 0.1);
  vec3 diffuse = vec3(1.0) * brightness;
  if(sun == 0) {
    outColor = vec4(diffuse, 1.0) * vec4(1.0);
  } else {
    outColor = vec4(1,1,0,1);
  }
}
