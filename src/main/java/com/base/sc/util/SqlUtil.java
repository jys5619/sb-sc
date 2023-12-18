package com.base.sc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;

public class SqlUtil {
    
    public static String getSql(String id) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        StringBuffer sb = new StringBuffer();
        try {
            ClassPathResource resource = new ClassPathResource("static/sql/" + id + ".sql");
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            br.lines().forEach(s -> sb.append(s).append("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( br != null ) try { br.close(); } catch ( Exception e ) {}
            if ( isr != null ) try { isr.close(); } catch ( Exception e ) {}
            if ( br != null ) try { br.close(); } catch ( Exception e ) {}
        }

        return sb.toString();
    }
}