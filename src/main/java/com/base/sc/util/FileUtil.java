package com.base.sc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    /**
     * 파일의 문자열을 조회
     * @param fileName
     * @return
     */
    public static String getFileText(String fileName) { 
        if ( StrUtil.isEmpty(fileName) ) return "";
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");        
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * 특정 폴더안의 파일 목록 조회
     * @param path
     * @return
     */
    public static List<String> getFileList(String path) {
        if ( StrUtil.isEmpty(path) ) return new ArrayList<>();
        File file = new File(path);
        return Arrays.asList(file.list());
    }

    /**
     * 특정 폴더 하위 포함 파일 목록 조회
     * @param path
     * @return
     */
    public static List<String> getAllFileList(String path) {
        List<String> fileList = new ArrayList<>();
        if (StrUtil.isEmpty(path)) return fileList;

        File rootFile = new File(path);        
        File[] files = rootFile.listFiles(); 
            
        for( File file : files ) { 
            if ( file.isFile() ) { 
                fileList.add(file.getPath());
            } else if( file.isDirectory() ) { 
                List<String> list = getAllFileList(file.getPath());  // 재귀함수 호출 
                fileList.addAll(list);
            } 
        }
        return fileList;
    }

}
