package com.iflytek.vcp.voice.engine.tts.utils;


import com.iflytek.vcp.voice.engine.tts.domain.AudioParams;

public final class WavUtils {

    public static AudioParams createAudio(int sampleRate, int length) {
        return new AudioParams(sampleRate * 1000,
                AudioParams.CHANNEL_MONO, AudioParams.ENCODING_PCM_16BIT,
                length);
    }
}