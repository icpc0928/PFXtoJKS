import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

public class PFXToJKSConvert {

    public static final String PKCS12 = "PKCS12";   //使用方式
    public static final String JKS = "JKS";         //匯出方式
    public static final String INPUT_KEYSTORE_FILE = "C:/Users/Leo/Desktop/star.va-game.site.pfx";  //輸入文件
    public static final String KEYSTORE_PASSWORD = "123456";    //密碼
    public static final String OUTPUT_KEYSTORE_FILE = "C:/Users/Leo/Desktop/star.va-game.site.jks"; //輸出文件


    public static void main(String[] args) {
        try {
            KeyStore inputKeyStore = KeyStore.getInstance(PKCS12);
            FileInputStream fis = new FileInputStream(INPUT_KEYSTORE_FILE);
            char[] nPassword = null;
            if ((KEYSTORE_PASSWORD == null)
                    || KEYSTORE_PASSWORD.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = KEYSTORE_PASSWORD.toCharArray();
            }

            inputKeyStore.load(fis, nPassword);
            fis.close();
            KeyStore outputKeyStore = KeyStore.getInstance(JKS);
            outputKeyStore.load(null, "123456".toCharArray());
            Enumeration e = inputKeyStore.aliases();
            while (e.hasMoreElements()) { // we are read in just one certificate.
                String keyAlias = (String) e.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
                if (inputKeyStore.isKeyEntry(keyAlias)) {
                    Key key = inputKeyStore.getKey(keyAlias, nPassword);
                    Certificate[] certChain = inputKeyStore
                            .getCertificateChain(keyAlias);
                    outputKeyStore.setKeyEntry("outkey", key, "111111"
                            .toCharArray(), certChain);
                }
            }
            FileOutputStream out = new FileOutputStream(OUTPUT_KEYSTORE_FILE);
            outputKeyStore.store(out, nPassword);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
