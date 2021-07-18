import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {


        klasorOlustur(new File("Audios"));

        belgeoku();


    }


    public static String klasorOlustur(File file) {

        if (file.exists()) //Klasör varmı diye kontrol ettiriyoruz.
        {
            return "Bu klasör ile aynı isimli bir klasör var.";
        } else {
            try {
                if (file.mkdir()) //Buradaki mkdir klasörü belirtir.
                {
                    return "Onaylandı";
                } else {
                    return "Hata oluştu";
                }
            } catch (Exception e) {
                return "Hata oluştu: " + e;
            }
        }
    }

    private static void belgeoku() {
        File file = new File("deutschen_wörter.txt");
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                String dosyaadi = line.replace('?', ' ')
                        .replace(',', ' ')
                        .replace('.', ' ')
                        .replace('!', ' ');

                String translate = line.replace(' ', '+')
                        .replace('?', ' ');

                if (dosyaadi.length() > 50) {
                    dosyaadi = dosyaadi.substring(0, 49);
                }

                donustur(dosyaadi, translate);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void donustur(String dosyaadi, String translate) {

        URL url = null;
        try {
            url = new URL("https://translate.google.com/translate_tts?ie=UTF-8&tl=de-DE&client=tw-ob&q=" + translate);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println(url);
        HttpURLConnection httpcon = null;
        try {
            httpcon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpcon.addRequestProperty("User-Agent", "anything");
        try {
            IOUtils.copy(httpcon.getInputStream(), new FileOutputStream("Audios\\" + dosyaadi + ".mp3"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}