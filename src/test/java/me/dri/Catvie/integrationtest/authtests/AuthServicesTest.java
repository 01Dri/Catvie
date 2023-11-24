package me.dri.Catvie.integrationtest.authtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.dri.Catvie.domain.enums.UserRole;
import me.dri.Catvie.domain.models.dto.auth.LoginDTO;
import me.dri.Catvie.domain.models.dto.auth.RegisterDTO;
import me.dri.Catvie.domain.ports.interfaces.auth.AuthenticationPort;
import me.dri.Catvie.domain.ports.interfaces.auth.TokenServicesPort;
import me.dri.Catvie.infra.adapters.AuthenticationAdapter;
import me.dri.Catvie.infra.adapters.EncoderPasswordAdapter;
import me.dri.Catvie.infra.adapters.mapper.MapperEntityAdapter;
import me.dri.Catvie.infra.ports.EncoderPassword;
import me.dri.Catvie.infra.ports.MapperUserPort;
import me.dri.Catvie.infra.ports.UserRepositoryJPA;
import me.dri.Catvie.infra.tokens.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthServicesTest {

    TokenServicesPort tokenServicesPort;
    UserRepositoryJPA repositoryJPA;
    MapperUserPort mapperUserPort;
    EncoderPassword encoderPassword;
    AuthenticationManager authenticationManager;

    AuthenticationPort authenticationPort;


    @BeforeEach
     void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/auth/v1/";
        RestAssured.port = 8080;
        tokenServicesPort = new TokenService();
        mapperUserPort = new MapperEntityAdapter();
        encoderPassword = new EncoderPasswordAdapter();
        authenticationPort = new AuthenticationAdapter(tokenServicesPort, repositoryJPA, mapperUserPort, encoderPassword, authenticationManager);
    }

    @Test
    void registerTest() {
        RegisterDTO obj = new RegisterDTO("diego", "henrique", "diego@gmail.com", "123", UserRole.USER);
        given().when().contentType(ContentType.JSON).body(obj).when().post("register")
                .then().statusCode(201).body("user", equalTo(obj.email()));
    }

    @Test
    void loginTest() {
        LoginDTO loginDTO = new LoginDTO("heenrikk3@gmail.com", "codigo123");
        given().when().contentType(ContentType.JSON).body(loginDTO).post("login")
                .then().statusCode(201);
    }


}
