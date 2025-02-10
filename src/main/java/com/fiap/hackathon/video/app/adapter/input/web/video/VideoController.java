package com.fiap.hackathon.video.app.adapter.input.web.video;

import com.fiap.hackathon.video.app.adapter.input.web.video.dto.VideoResponse;
import com.fiap.hackathon.video.app.adapter.input.web.video.mapper.VideoResponseMapper;
import com.fiap.hackathon.video.core.common.exception.NotFoundException;
import com.fiap.hackathon.video.core.domain.User;
import com.fiap.hackathon.video.core.domain.Video;
import com.fiap.hackathon.video.core.usecase.*;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
public class VideoController {

    private final VideoCreateUseCase videoCreateUseCase;
    private final VideoGetUseCase videoGetUseCase;
    private final VideoListUseCase videoListUseCase;
    private final ThumbnailDownloadUseCase thumbnailDownloadUseCase;
    private final VideoResponseMapper videoResponseMapper;
    private final SendMailUseCase sendMailUseCase;

    @PostMapping
    public VideoResponse create(@RequestParam MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User u = (User) authentication.getPrincipal();

            Video video = videoCreateUseCase.execute(file, u);
            return videoResponseMapper.toVideoResponse(video);
        } else {
            throw new UnauthorizedUserException("Usuário não autenticado");
        }
    }

    @GetMapping("/{id}")
    public VideoResponse get(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User u = (User) authentication.getPrincipal();
            Video video = videoGetUseCase.execute(id);
            if (!Objects.equals(video.getCreatedBy(), u.getId()))
                throw new NotFoundException();

            return videoResponseMapper.toVideoResponse(video);
        } else {
            throw new UnauthorizedUserException("Usuário não autenticado");
        }
    }

    @GetMapping
    public List<VideoResponse> list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User u = (User) authentication.getPrincipal();
            List<Video> videos = videoListUseCase.execute(u.getId());
            return videos.stream().map(videoResponseMapper::toVideoResponse).toList();
        } else {
            throw new UnauthorizedUserException("Usuário não autenticado");
        }
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<InputStreamSource> thumbnailDownload(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {

            User u = (User) authentication.getPrincipal();
            Video video = videoGetUseCase.execute(id);
            if (!Objects.equals(video.getCreatedBy(), u.getId()))
                throw new NotFoundException();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("thumbnail.zip").build().toString())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(thumbnailDownloadUseCase.execute(id));
        } else {
            throw new UnauthorizedUserException("Usuário não autenticado");
        }
    }

    @GetMapping("/sendmail")
    public ResponseEntity sendmail() throws MessagingException {
        sendMailUseCase.execute("grazielagoedert@gmail.com", 1L);
        return ResponseEntity.status(OK)
                .body("E-mail enviado com Sucess!!!");
    }

}
