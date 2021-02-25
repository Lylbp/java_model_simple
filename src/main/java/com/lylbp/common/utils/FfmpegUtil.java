package com.lylbp.common.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class FfmpegUtil {
    //设置图片大小
    private final static String IMG_SIZE = "1920x1080";

    /**
     * 视频截图方法
     *
     * @param videoPath
     * @param imagePath
     * @param timePoint
     * @return
     */
    public static boolean ffmpegToImage(String ffPath, String videoPath, String imagePath, int timePoint) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffPath);
        commands.add("-ss");
        commands.add(timePoint + "");//这个参数是设置截取视频多少秒时的画面
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add(IMG_SIZE); //这个参数是设置截取图片的大小
        commands.add(imagePath);
        startCommand(commands, new ProcessBuilder());
        return true;
    }

    /**
     * ffmpeg -y -i /Users/alex/Desktop/1586247326559874.mp4 -vcodec copy -acodec copy -vbsf h264_mp4toannexb /Library/WebServer/java_project/audioVideoCache/1586247326559874.ts
     * @param ffPath
     * @param videoPath
     * @param savePath
     * @return
     */
    public static boolean file2Ts(String ffPath, String videoPath, String savePath) {
        List<String> commands = new ArrayList<>();
        commands.add(ffPath);
        commands.add("-y");
        commands.add("-i");
        commands.add(videoPath);
//        commands.add("-vcodec");
//        commands.add("copy");
//        commands.add("-acodec");
//        commands.add("copy");
//        commands.add("-vbsf");
//        commands.add("h264_mp4toannexb");
        commands.add(savePath);

        Process process = startCommand(commands, new ProcessBuilder());
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * ffmpeg -i appdemos/mydemo.ts -c copy -map 0 -f segment -segment_list appdemos/mydemo.m3u8 -segment_time 15 appdemos/mydemo%03d.ts
     *
     * ffmpeg -i /Library/WebServer/java_project/audioVideoCache/1586247326559874.ts -c copy -map 0 -f segment -segment_list /Library/WebServer/java_project/audioVideoCache/1586247326559874.m3u8 -segment_time 15 /Library/WebServer/java_project/audioVideoCache/1586247326559874%03d.ts
     *
     *
     */
    public static boolean splitTs(String ffPath, String tsPath, String m3u8Path, int time, String tsName) {
        List<String> commands = new ArrayList<>();
        commands.add(ffPath);
        commands.add("-i");
        commands.add(tsPath);
        commands.add("-c");
        commands.add("copy");
        commands.add("-map");
        commands.add("0");
        commands.add("-f");
        commands.add("segment");
        commands.add("-segment_list");
        commands.add(m3u8Path);
        commands.add("-segment_time");
        commands.add(time + "");
        commands.add(tsName);

        startCommand(commands, new ProcessBuilder());
        return true;
    }

    ////////////////////////////////////

    /**
     * 文件是否能被ffmpeg解析
     *
     * @param fileName
     * @return
     */
    public static int checkFileType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                .toLowerCase();
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        } else if (type.equals("png")) {
            return 1;
        } else if (type.equals("jpg")) {
            return 1;
        } else if (type.equals("jpeg")) {
            return 1;
        }
        return 9;
    }

    /**
     * @Description 获取视频时长
     */
    public static int getVideoTime(String ffPath, String video_path) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffPath);
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();
            String errMessage = readErr(p.getErrorStream());
            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(errMessage);
            if (m.find()) {
                int time = getTimelen(m.group(1));
                System.out.println(video_path + ",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //格式:"00:00:10.68"
    public static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    /**
     * 秒转化成 hh:mm:ss
     *
     * @param duration
     * @return
     */
    public static String convertInt2Date(long duration) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(cal.getTimeInMillis() + duration * 1000);
    }

    /**
     * 视频抽取音频文件
     *
     * @param videoPath
     * @param type
     * @param audioPath
     * @return
     */
    public static boolean ffmpegToAudio(String ffPath, String videoPath, String type, String audioPath) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffPath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-f");
        commands.add(type);
        commands.add("-vn");
        commands.add("-y");
        commands.add("-acodec");
        if ("wav".equals(type)) {
            commands.add("pcm_s16le");
        } else if ("mp3".equals(type)) {
            commands.add("mp3");
        }
        commands.add("-ar");
        commands.add("16000");
        commands.add("-ac");
        commands.add("1");
        commands.add(audioPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("抽离成功:" + audioPath);

            // 1. start
            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            // 1. end
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * wav 转 mp3
     *
     * @param wavPath
     * @param mp3Path
     * @return
     */
    //wav转mp3命令：ffmpeg -i test.wav -f mp3 -acodec libmp3lame -y wav2mp3.mp3
    public static boolean ffmpegOfwavTomp3(String ffPath, String wavPath, String mp3Path) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffPath);
        commands.add("-i");
        commands.add(wavPath);
        commands.add("-f");
        commands.add("mp3");
        commands.add("-acodec");
        commands.add("libmp3lame");
        commands.add("-y");
        commands.add(mp3Path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("转换成功:" + mp3Path);

            // 1. start
            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            // 1. end
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String readErr(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return IoUtil.read(br);
    }


    private static Process startCommand(List<String> commands, ProcessBuilder builder) {
        builder.command(commands);
        try {
            Process p = builder.start();
            String errMsg = readErr(p.getErrorStream());
            if (ObjectUtil.isNotEmpty(errMsg)) {
                log.info(errMsg);
            }
            return p;
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
