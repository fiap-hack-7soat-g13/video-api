package com.fiap.challenge.video.app.adapter.output.persistence.repository;

import com.fiap.challenge.video.app.adapter.output.persistence.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    List<VideoEntity> findByName(String name);

}
