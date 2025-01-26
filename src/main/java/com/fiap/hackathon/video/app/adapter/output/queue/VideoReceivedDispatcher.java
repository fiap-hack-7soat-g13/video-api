package com.fiap.hackathon.video.app.adapter.output.queue;

import com.fiap.hackathon.video.core.domain.Video;

public interface VideoReceivedDispatcher {

    void dispatch(Video video);

}

