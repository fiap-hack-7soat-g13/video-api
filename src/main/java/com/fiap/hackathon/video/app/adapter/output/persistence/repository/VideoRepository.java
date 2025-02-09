package com.fiap.hackathon.video.app.adapter.output.persistence.repository;

import com.fiap.hackathon.video.app.adapter.output.persistence.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

	@Query(value = "SELECT v FROM VideoEntity v WHERE v.createdBy = :userId ")
	List<VideoEntity> findAllByCreatedBy(Long userId);

}
