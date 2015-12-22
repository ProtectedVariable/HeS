#version 330 core

layout (location = 0)out vec4 FragColor;
layout (location = 1)out vec4 BrightColor;

in TO_FRAG {
  vec3 normal;
  vec3 toLight;
} lightIn;

uniform int source;

void main()
{
	if(source == 0) {
		FragColor = vec4(20,20,0,20);
	} else {
		FragColor = vec4(200.0);
	}
	float brightness = dot(FragColor.rgb, vec3(0.2126, 0.7152, 0.0722));
    if(brightness > 1.0)
        BrightColor = vec4(FragColor.rgb, 1.0);
}
