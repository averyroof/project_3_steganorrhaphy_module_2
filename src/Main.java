import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static Charset w1251 = Charset.forName("Windows-1251");

    public static String readFile(Path path) throws IOException {
        byte[] bytesOfFile = Files.readAllBytes(path);
        String fileStr = new String(bytesOfFile, w1251);
        return fileStr;
    }

    public static String recovery(String text) {
        String result = "";
        char[] chText = text.toCharArray();
        for (int i = 0; i < chText.length; i++) {
            if (chText[i] == ' ' && i < (chText.length - 1)) {
                i++;
                if (chText[i] == ' ') {
                    result = result + "1";
                } else {
                    result = result + "0";
                }
            }
        }
        return result;
    }

    public static String createHidden(String str) throws UnsupportedEncodingException {
        String hidden = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < str.length(); i += 8) {
            String sub = str.substring(i, i + 8);
            if (sub.equals("00000000")) {
                return hidden;
            }
            int value = Integer.parseInt(str.substring(i, i + 8), 2);
            baos.write(value);
            hidden = baos.toString("Cp1251");
        }
        return hidden;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        // C:\Projects\project_2_steganorrhaphy\files\result1.txt
        System.out.println("\nВведите путь к результирующему файлу, в котором находится текст со скрытой информацией: ");
        Path pathResult = Path.of(bf.readLine());
        String textString = readFile(pathResult);

        String str = recovery(textString);
        String result = createHidden(str);

        System.out.println("\nИзвлеченная сокрытая информация: " + result);
    }
}
