import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest2 {

    private void TestTest2Helper(String methodName, String threadCount, String totalIterations) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException, IOException {
        PrintStream stdOut = System.out;
        PrintStream stdErr = System.err;

        PipedOutputStream myOut = new PipedOutputStream();
        PipedOutputStream myErr = new PipedOutputStream();

        System.setOut(new PrintStream(myOut, true));
        System.setErr(new PrintStream(myErr, true));

        PipedInputStream pisOut = new PipedInputStream(myOut);
        BufferedReader brOut = new BufferedReader(new InputStreamReader(pisOut));

        PipedInputStream pisErr = new PipedInputStream(myErr);
        BufferedReader brErr = new BufferedReader(new InputStreamReader(pisErr));

        String[] args = new String[]{methodName, threadCount, totalIterations};
        edu.vt.ece.Test2.main(args);

        String line1 = assertTimeoutPreemptively(ofSeconds(10), brOut::readLine);
        assertTrue(line1.startsWith("Average time per thread is"));
        String line2 = assertTimeoutPreemptively(ofSeconds(10), brOut::readLine);
        assertTrue(line2.startsWith("Average time per thread is"));

        assertFalse(brErr.ready());

        System.setOut(stdOut);
        System.setOut(stdErr);
        System.out.println(line1);
        System.out.println(line1);
    }

    @Test
    void TestTest2_Peterson() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("Peterson", "2", "10");
    }

    @Test
    void TestTest2_Filter() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("Filter", "2", "10");
    }

    @Test
    void TestTest2_Bakery() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("Bakery", "2", "10");
    }

    @Test
    void TestTest2_TreePeterson() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("TreePeterson", "2", "10");
    }

    @Test
    void TestTest2_Filter_8Thread() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("Filter", "8", "32000");
    }

    @Test
    void TestTest2_Bakery_8Thread() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("Bakery", "8", "32000");
    }

    @Test
    void TestTest2_TreePeterson_8Thread() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, InterruptedException {
        TestTest2Helper("TreePeterson", "8", "32000");
    }

}
