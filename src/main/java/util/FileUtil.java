package util;

import configuration_and_constant.Constant;
import entity.FileEnum;
import entity.FileModel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * @类名 FileUtil
 * @描述 进行文件的相关操作
 * @作者 heguicai
 * @创建日期 2020/7/3 下午1:37
 **/
@Slf4j
@NoArgsConstructor
public class FileUtil {

    public static ArrayList<FileModel> getFileList(String filePath) {
        ArrayList<FileModel> list = new ArrayList<FileModel>();
        File fatherFile = new File(filePath);
        if (fatherFile.isFile()) {
            return list;
        }
        File[] fileList = fatherFile.listFiles();
        for (File kidFile : fileList) {
            FileModel fileCol = FileModel.builder().fileName(kidFile.getName())
                    .filePath(kidFile.getAbsolutePath())
                    .changeTime(getChangeTime(kidFile))
                    .build();
            if (kidFile.isFile()) {
                fileCol.setFileType(judgeFileType(kidFile.getAbsolutePath()));
                fileCol.setFileSize(String.valueOf(kidFile.length()));
            } else {
                fileCol.setFileType(FileEnum.DIR);
                fileCol.setFileSize("<DIR>");
            }
            list.add(fileCol);
        }
        return list;
    }

    public static String getChangeTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());
        String time = sdf.format(cal.getTime());
        return time;
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(getFileLine("C:\\Download\\test.html"));
//    }

    /**
     * Linux下判断文件类型
     */
    public static FileEnum judgeFileTypeInLinux(String filePath) {
        try {
            String command = "file -i " + filePath;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = br.readLine();
            final String[] s = result.split(" ");
            result = s[1];
            int status = process.waitFor();
            if (status != 0) {
                System.err.println("Failed to call shell's command and the return status's is: " + status);
            }
            if (result == null) {
                return null;
            }
            if (result.startsWith("text")) {
                return FileEnum.TEXT;
            } else if (result.startsWith("inode")) {
                return null;
            } else {
                return FileEnum.BINARY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return judgeFileTypeGenerally(filePath);
        }
    }

    public static String getFatherDir(String filePath) {
        File file = new File(filePath);
        if (file.getParent().equals(Constant.DEFAULT_FILE_PATH)) {
            return Constant.DEFAULT_FILE_PATH;
        } else {
            return new File(file.getParent()).getParent();
        }
    }

    public static final HashMap<String, String> textFileHead = new HashMap<>();
    public static final HashMap<String, String> binaryFileHead = new HashMap<>();

    static {
        textFileHead.put("3C3F786D", "xml");
        textFileHead.put("75736167", "txt");
        textFileHead.put("68746D6C", "html");
        textFileHead.put("EFBBBF", "utf8 with bom");
        textFileHead.put("FEFF", "utf16 big endian");
        textFileHead.put("FFFE", "utf16 little endian");
        textFileHead.put("002a2a2a", "log");

        binaryFileHead.put("89504E47", "png");//png
        binaryFileHead.put("47494638", "gif");//gif
        binaryFileHead.put("49492A00", "tif");//tif
        binaryFileHead.put("D0CF11E0", "xls_or_doc");//xls_or_doc
        binaryFileHead.put("89504E47", "png");
        binaryFileHead.put("47494638", "gif");
        binaryFileHead.put("49492A00", "tif");
        binaryFileHead.put("41433130", "dwg"); //CAD
        binaryFileHead.put("38425053", "psd");
        binaryFileHead.put("7B5C7274", "rtf"); //日记本
        binaryFileHead.put("44656C69", "eml"); //邮件
        binaryFileHead.put("D0CF11E0", "doc");
        binaryFileHead.put("5374616E", "mdb");
        binaryFileHead.put("25215053", "ps");
        binaryFileHead.put("25504446", "pdf");
        binaryFileHead.put("504B0304", "zip");
        binaryFileHead.put("52617221", "rar");
        binaryFileHead.put("57415645", "wav");
        binaryFileHead.put("41564920", "avi");
        binaryFileHead.put("2E524D46", "rm");
        binaryFileHead.put("000001BA", "mpg");
        binaryFileHead.put("000001B3", "mpg");
        binaryFileHead.put("6D6F6F76", "mov");
        binaryFileHead.put("3026B275", "asf");
        binaryFileHead.put("4D546864", "mid");
        binaryFileHead.put("1F8B08", "gz");
        binaryFileHead.put("FFD8FF", "jpg");
        binaryFileHead.put("424D", "bmp");
    }

    public static FileEnum judgeFileTypeGenerally(String filePath) {
        log.info("invoke the mehtod judgeFileTypeGenerally, the parameter is {}", filePath);
        final String fileHead = getFileHead(filePath);
        log.info("file head is {}", fileHead);
        if (fileHead != null) {
            if (textFileHead.containsKey(fileHead) || textFileHead.containsKey(fileHead.substring(0, fileHead.length() - 2)) || textFileHead.containsKey(fileHead.substring(0, fileHead.length() - 4))) {
                return FileEnum.TEXT;
            }
            if (binaryFileHead.containsKey(fileHead) || binaryFileHead.containsKey(fileHead.substring(0, fileHead.length() - 2)) || binaryFileHead.containsKey(fileHead.substring(0, fileHead.length() - 4))) {
                return FileEnum.BINARY;
            }
        }
        switch (filePath.substring(filePath.lastIndexOf('.') + 1)) {
            case "txt":
            case "ini":
            case "log":
            case "iml":
            case "md":
                return FileEnum.TEXT;
            default:
                return FileEnum.BINARY;
        }
    }

    public static String getFileHead(String filePath) {
        log.info("invoke getFileHead");
        try (FileInputStream fileInputStream = new FileInputStream(filePath);) {
            final byte[] bytes = new byte[4];
            fileInputStream.read(bytes, 0, 4);
            for (byte b : bytes) {
                System.out.print((int) (b & 0xFF) + " ");
            }
            return bytesToHexStrig(bytes);
        } catch (FileNotFoundException e) {
            log.error("文件不存在");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public static String bytesToHexStrig(byte[] bytes) {
        log.info("change bytes to hex string");
        StringBuilder result = new StringBuilder();
        String string;
        final char[] chars = "0123456789ABCDEF".toCharArray();
        for (byte b : bytes) {
            // 取出这个字节的高4位，与0x0f与运算，得到一个0-15之间的数据，通过十六进制字符数组chars[0-15]即为16进制数
            result.append(chars[((b >> 4) & 0x0f)]);
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过十六进制字符数组chars[0-15]即为16进制数
            result.append(chars[(b & 0x0f)]);
        }
        return result.toString();
    }

    public static FileEnum judgeFileType(String filePath) {
        String osName = System.getProperty("os.name");
        switch (osName) {
            case "Linux":
                return judgeFileTypeInLinux(filePath);
            default:
                return judgeFileTypeGenerally(filePath);
        }
    }

    public static int getFileLine(String filePath) throws IOException {
        File file=new File(filePath);
        if(file.length()==0) {
            return 0;
        }
        FileReader fileReader=new FileReader(file);
        LineNumberReader lineNumberReader=new LineNumberReader(fileReader);
        lineNumberReader.skip(Long.MAX_VALUE);
        return   lineNumberReader.getLineNumber();
    }
}
