#version 330 core

out vec4 outColor;

in TO_FRAG {
  vec3 normal;
  vec3 toLight;
} lightInfo;

uniform int sun;

void main()
{
  vec3 unitNormal = normalize(lightInfo.normal);
  vec3 unitLightVector = normalize(lightInfo.toLight);
  float nDl = dot(unitNormal, unitLightVector);
  float brightness = max(nDl, 0.5);
  vec3 diffuse = vec3(1.0) * brightness;
  if(sun == 0) {
    outColor = vec4(diffuse, 1.0) * vec4(1.0);
  } else {
    outColor = vec4(1,1,0,1);
  }
}
