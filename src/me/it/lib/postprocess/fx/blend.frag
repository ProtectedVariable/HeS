#version 330 core
out vec4 FragColor;
in vec2 TexCoords;

//TEXTURE1
uniform sampler2D scene;
//TEXTURE0
uniform sampler2D bloomBlur;
float exposure = 0.2;

void main()
{             
    const float gamma = 2.2;
    vec3 hdrColor = texture(scene, TexCoords).rgb;      
    vec3 bloomColor = texture(bloomBlur, TexCoords).rgb;
    hdrColor += bloomColor; // additive blending
    // tone mapping
    vec3 result = vec3(1.0) - exp(-hdrColor * exposure);
    // Gamma correct    
    result = pow(result, vec3(1.0 / gamma));
	FragColor = vec4(result, 1.0f);
}