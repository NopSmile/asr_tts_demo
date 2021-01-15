package com.iflytek.vcp.voice.engine.tts.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public final class AudioParams implements Serializable {
    private static final int MIN_HEADER_LENGTH = 44;
    public static final int SAMPLE_RATE_16K = 16000;
    public static final int SAMPLE_RATE_8K = 8000;
    public static final int CHANNEL_MONO = 1;
    public static final int CHANNEL_STEREO = 2;
    public static final int ENCODING_PCM_16BIT = 16;
    public static final int ENCODING_PCM_8BIT = 8;
    public static final int LINEAR_ENCODING = 1;
    public int sampleRate;
    public int channel;
    public int format;
    public int encodeMode;
    public int frameSize;
    public int frameRate;
    public int pcmLen;

    AudioParams() {
    }

    public AudioParams(int sampleRate, int channel, int format, int pcmLen) {
        this(sampleRate, channel, format, 1, channel * (format / 8), channel * sampleRate / (format / 8), pcmLen);
    }

    public AudioParams(int sampleRate, int channel, int format, int encodeMode, int frameSize, int frameRate, int pcmLen) {
        this.sampleRate = sampleRate;
        this.channel = channel;
        this.format = format;
        this.encodeMode = encodeMode;
        this.frameSize = frameSize;
        this.frameRate = frameRate;
        this.pcmLen = pcmLen;
    }

    public byte[] createWAVHeader() {
        byte[] header = new byte[44];
        header[0] = 82;
        header[1] = 73;
        header[2] = 70;
        header[3] = 70;
        int riffChunkSize = this.pcmLen + header.length - 8;
        header[4] = (byte) (riffChunkSize & 255);
        header[5] = (byte) (riffChunkSize >> 8 & 255);
        header[6] = (byte) (riffChunkSize >> 16 & 255);
        header[7] = (byte) (riffChunkSize >> 24 & 255);
        header[8] = 87;
        header[9] = 65;
        header[10] = 86;
        header[11] = 69;
        header[12] = 102;
        header[13] = 109;
        header[14] = 116;
        header[15] = 32;
        boolean minFmtChunkSize = true;
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = (byte) (this.encodeMode & 255);
        header[21] = (byte) (this.encodeMode >> 8 & 255);
        header[22] = (byte) (this.channel & 255);
        header[23] = (byte) (this.channel >> 8 & 255);
        header[24] = (byte) (this.sampleRate & 255);
        header[25] = (byte) (this.sampleRate >> 8 & 255);
        header[26] = (byte) (this.sampleRate >> 16 & 255);
        header[27] = (byte) (this.sampleRate >> 24 & 255);
        header[28] = (byte) (this.frameRate & 255);
        header[29] = (byte) (this.frameRate >> 8 & 255);
        header[30] = (byte) (this.frameRate >> 16 & 255);
        header[31] = (byte) (this.frameRate >> 24 & 255);
        header[32] = (byte) (this.frameSize & 255);
        header[33] = (byte) (this.frameSize >> 8 & 255);
        header[34] = (byte) (this.format & 255);
        header[35] = (byte) (this.format >> 8 & 255);
        header[36] = 100;
        header[37] = 97;
        header[38] = 116;
        header[39] = 97;
        header[40] = (byte) (this.pcmLen & 255);
        header[41] = (byte) (this.pcmLen >> 8 & 255);
        header[42] = (byte) (this.pcmLen >> 16 & 255);
        header[43] = (byte) (this.pcmLen >> 24 & 255);
        return header;
    }

    public static AudioParams parseParamsFrom(InputStream wav, OutputStream origin) {
        AudioParams p = null;

        try {
            byte[] flag = new byte[12];
            fixedRead(wav, flag, origin);
            String riff = getString(flag, 0, 4).trim();
            String wave = getString(flag, 8, 4).trim();
            if ("RIFF".equalsIgnoreCase(riff) && "WAVE".equalsIgnoreCase(wave)) {
                p = new AudioParams();
                parseChunk(wav, p, origin);
            } else {
                System.out.println("[AudioParams] >> can't parse audio header ! skip");
            }
        } catch (Exception var6) {
            System.out.println("[AudioParams] >> can't parse audio header ! skip");
        }

        return p;
    }

    private static void parseChunk(InputStream wav, AudioParams p, OutputStream origin) throws Exception {
        byte[] chunk = new byte[8];
        fixedRead(wav, chunk, origin);
        String name = getString(chunk, 0, 4).trim();
        int size = getInt(chunk, 4, 4);
        if ("fmt".equalsIgnoreCase(name)) {
            byte[] info = new byte[size];
            fixedRead(wav, info, origin);
            p.encodeMode = getInt(info, 0, 2);
            p.channel = getInt(info, 2, 2);
            p.sampleRate = getInt(info, 4, 4);
            p.frameRate = getInt(info, 8, 4);
            p.frameSize = getInt(info, 12, 2);
            p.format = getInt(info, 14, 2);
            parseChunk(wav, p, origin);
        } else if ("data".equalsIgnoreCase(name)) {
            p.pcmLen = size;
        } else {
            if (size > 0) {
                fixedRead(wav, new byte[size], origin);
            }

            parseChunk(wav, p, origin);
        }

    }

    private static int getInt(byte[] buf, int start, int len) {
        int target = 0;

        for (int index = 0; index < len; ++index) {
            target += (buf[start + index] & 255) << 8 * index;
        }

        return target;
    }

    private static String getString(byte[] buf, int start, int len) {
        byte[] data = new byte[len];
        System.arraycopy(buf, start, data, 0, len);
        return new String(data);
    }

    private static void fixedRead(InputStream source, byte[] buf, OutputStream origin) throws IOException {
        int len = source.read(buf);
        if (len > 0 && null != origin) {
            origin.write(buf, 0, len);
        }

        System.out.println("[AudioParams] >> header read: " + len + "/" + buf.length);
    }

    public final AudioParams copy() {
        return new AudioParams(this.sampleRate, this.channel, this.format, this.encodeMode, this.frameSize, this.frameRate, this.pcmLen);
    }

    public final long getDuration() {
        return (long) ((float) this.pcmLen * 1.0F / (float) (this.sampleRate * this.channel * (this.format / 8)) * 1000.0F);
    }

    @Override
    public String toString() {
        return "AudioParams{sampleRate=" + this.sampleRate + ", channel=" + this.channel + ", format=" + this.format + ", encodeMode=" + this.encodeMode + ", frameSize=" + this.frameSize + ", frameRate=" + this.frameRate + ", pcmLen=" + this.pcmLen + '}';
    }
}

