package com.fenq1.study.video;

import com.fenq1.study.video.jcv.ffmpeg.FFmpeg4VideoImageGrabber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("video")
public class VideoController {

    @GetMapping
    public String shot(@RequestParam("url") String url) throws IOException {
        FFmpeg4VideoImageGrabber grabber = new FFmpeg4VideoImageGrabber(url);
        return grabber.getBase64Image(url);
    }
}
