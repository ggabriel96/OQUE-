package fonte;
import java.io.*;
import java.util.*;
import java.util.regex.*;

class SourceScanner {
    public ArrayList<String> scan(File f) throws Exception {
        int i, comm;
        String line;
        Scanner sc = new Scanner(f);
        ArrayList<String> input = new ArrayList<String>();

        for (i = 1; sc.hasNext(); i++) {
            line = sc.nextLine().trim();

            if (line.startsWith("--")) {
                line = "";
            }
            else {
                comm = line.indexOf("--");
                if (comm >= 0) {
                    line = line.substring(0, comm).trim();
                }
            }

            // check syntax, translate to easier-to-execute format

            if (!line.equals("")) input.add(line);
        }
        sc.close();

        return input;
    }

    public String[] scanToStringArray(File f) throws Exception {
        String[] result;
        ArrayList<String> readCode = this.scan(f);
        result = new String[readCode.size()];
        readCode.toArray(result);
        // this.print(readCode);
        return result;
    }

    public void print(ArrayList<String> code) {
        int i, max = code.size();
        for (i = 0; i < max; i++) {
            System.out.println(code.get(i));
        }
    }
}
