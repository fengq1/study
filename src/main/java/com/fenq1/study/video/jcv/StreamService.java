package com.fenq1.study.video.jcv;

import com.fenq1.study.video.jcv.ffmpeg.FFmpeg4VideoImageGrabber;

import java.io.IOException;

public class StreamService {

    public String shot(String url) {
        FFmpeg4VideoImageGrabber grabber = new FFmpeg4VideoImageGrabber(url);
        try {
            return grabber.getBase64Image(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
