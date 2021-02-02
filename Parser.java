/**
 * Please review the class below and suggest improvements. How would
 * you refactor this class if it would be in a real-life project?
 * There are many problems here, both high-level design mistakes,
 * and low-level implementation bugs. We're interested to see high-level
 * problems first, since they are most critical. The more mistakes
 * you can spot, the better programmer you are.
 */
 
/**
 * This class is thread safe.
 */
@Data
public class Parser {
  private final File file;
 
  public String getContent() throws IOException {
    InputStream i = new FileInputStream(file);
    StringBuilder strBuilder = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      strBuilder.append((char) data);
    }
    return strBuilder.toString();
  }
 
  public String getContentWithoutUnicode() throws IOException {
    InputStream i = new FileInputStream(file);
    StringBuilder strBuilder = new StringBuilder();
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        strBuilder.append((char) data);
      }
    }
    return output;
  }
 
  public void saveContent(String content) throws IOException {
    OutputStream o = new FileOutputStream(file);
    // string to char? next get from array
    for (int i = 0; i < content.length(); i++) {
      o.write(content.charAt(i));
    }
  }
 
}
