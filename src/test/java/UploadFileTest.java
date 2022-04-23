import org.junit.jupiter.api.Test;

public class UploadFileTest {
    @Test
    public void test(){

        String fileName="454478.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);

    }

}
