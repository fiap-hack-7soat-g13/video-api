package com.fiap.challenge.user.bdd;

import com.fiap.challenge.user.core.domain.User;
import com.fiap.challenge.user.util.DataHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;

    private User user;

    @Quando("criar um novo usuario")
    public void criar_um_novo_usuario() {
        response = given()
                .contentType(ContentType.JSON)
                .body(DataHelper.createUserRequest())
                .when()
                .post();
    }

    @Entao("deve retornar sucesso")
    public void deve_retornar_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Entao("deve retornar os dados do usuario")
    public void deve_retornar_os_dados_do_usuario() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/UserResponse.schema.json"));
    }

    @Dado("um usuario que existe")
    public void um_usuario_que_existe() {
        user = new User();
        user.setName("Nome 1");
        user.setEmail("teste@teste.com.br");
        user.setDocument("01234567890");
        user.setPassword("123456");
    }

    @Quando("obter o usuario")
    public void obter_o_usuario() {
        response = when()
                .get("/{id}", user.getId());
    }

    @Dado("um usuario que não existe")
    public void um_usuario_que_não_existe() {
        user = new User();
        user.setName("Nome 99");
        user.setEmail("teste99@teste99.com.br");
        user.setDocument("99999999999");
        user.setPassword("999999");
    }

    @Entao("deve retornar não encontrado")
    public void deve_retornar_não_encontrado() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
