package com.fiap.hackathon.video.app.adapter.output.persistence.repository;

import com.fiap.hackathon.video.app.adapter.output.persistence.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

}
