package com.mayank.carrental;



//Swagger & OpenAPI imports

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.info.Contact;

import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.servers.Server;

import io.swagger.v3.oas.models.security.SecurityRequirement;

import io.swagger.v3.oas.models.security.SecurityScheme;

import io.swagger.v3.oas.models.security.SecurityScheme.In;

import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

/**

* SwaggerConfig sets up OpenAPI (Swagger UI) documentation.

*

* ✅ Explains the API project

* ✅ Adds server URLs (local/dev/prod)

* ✅ Enables JWT Bearer authorization button

* ✅ Shows title, description, version, contact, and license info

*/

@Configuration // Marks this as a configuration class for Spring Boot

public class SwaggerConfig {

/**

* Defines the OpenAPI specification for Swagger UI.

*/

@Bean

public OpenAPI customOpenAPI() {

//1️ Define SecurityScheme for JWT Bearer Token

	 SecurityScheme bearerAuth = new SecurityScheme()

     .type(Type.HTTP)         // Use HTTP auth

     .scheme("bearer")        // Bearer authentication

     .bearerFormat("JWT")     // Format hint for Swagger UI

     .in(In.HEADER)           // Token will be sent via Authorization header

     .name("Authorization");  // Header name

//2️ Add Security Requirement so Swagger knows to apply Bearer to endpoints

SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

//3️ Add Info section (project metadata)

Info apiInfo = new Info()

     .title("JWT Authentication API") // Swagger UI main title

     .version("1.0.0") // API version

     .description("""

             This project demonstrates how to implement JWT-based Authentication and Authorization

             in Spring Boot 3 using Spring Security and Springdoc OpenAPI 2.x.

             **Endpoints Overview**

             - `/auth/register` → Public, creates a new user.

             - `/auth/login` → Public, authenticates user and returns a JWT token.

             - All other endpoints → Require Bearer token in Authorization header.

             **Usage:**

             1. Register a user via `/auth/register`

             2. Login via `/auth/login` to receive a JWT token

             3. Click "Authorize" in Swagger UI and paste the token (without quotes)

             4. Access secured endpoints.

              """)

     .contact(new Contact()

             .name("Your Name or Organization")

             .url("https://yourwebsite.com")

             .email("you@example.com"))

     .license(new License()

             .name("Apache 2.0")

             .url("https://www.apache.org/licenses/LICENSE-2.0.html"));

//4️ Define Server URLs for Swagger UI dropdown

Server localServer = new Server()

     .url("http://localhost:8080")

     .description("Local Development Server");

Server prodServer = new Server()

     .url("https://api.yourdomain.com")

     .description("Production Server");

//5️ Return the OpenAPI object with all components combined

return new OpenAPI()

     .info(apiInfo)

     .addServersItem(localServer)

     .addServersItem(prodServer)

     .addSecurityItem(securityRequirement)

     .schemaRequirement("bearerAuth", bearerAuth);

}

}


