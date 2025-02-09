package com.fiap.hackathon.video.bdd;

import com.fiap.hackathon.video.core.domain.Video;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;

    private Video video;

    @Quando("carregar um novo video")
    public void carregar_um_novo_video() {
        File file = new File("video/mp4/teste.mp4");
        response = given()
                .contentType(ContentType.JSON)
                .body(file)
                .when()
                .post();
    }

    @Entao("deve retornar sucesso")
    public void deve_retornar_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Entao("deve retornar os dados do video")
    public void deve_retornar_os_dados_do_video() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/VideoResponse.schema.json"));
    }

    @Dado("um video que existe")
    public void um_video_que_existe() {
        video = new Video();
        video.setId(1L);
        video.setName("Nome 1");
        video.setContentType("video/mp4");
        video.setSize(1000L);
        video.setCreatedAt(LocalDateTime.now().minusDays(10));
        video.setCreatedBy(1L);
        video.setCreatedByEmail("teste@teste.com.br");
    }

    @Quando("obter o video")
    public void obter_o_video() {
        response = when()
                .get("/{id}", video.getId());
    }

    @Dado("um video que não existe")
    public void um_video_que_não_existe() {
        video = new Video();
        video.setId(0L);
        video.setName("Nome 1");
        video.setContentType("video/mp4");
        video.setSize(1000L);
        video.setCreatedAt(LocalDateTime.now().minusDays(10));
        video.setCreatedBy(1L);
        video.setCreatedByEmail("teste@teste.com.br");
    }

    @Entao("deve retornar não encontrado")
    public void deve_retornar_não_encontrado() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
