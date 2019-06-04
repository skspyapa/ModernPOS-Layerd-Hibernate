import java.io.File;

public class FileTest {
    public static void main(String[] args) {
        File file = new File("D:\\Java\\MyPosSystemJPA\\PosSystem\\src\\lk\\ijse\\dep\\reports\\CustomerReport.jrxml");
        System.out.println(file.getAbsolutePath());
    }
}
