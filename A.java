
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class A {

    // Prevent infinite hanging
    private static final long PROCESS_TIMEOUT_SECONDS = 5;

    public static void main(String[] args) {

        // Simulated user input
        String userInput = args.length > 0 ? args[0] : "dir";

        try {

            // ==============================
            // BUG 1: Command Injection
            // ==============================
            // Unsafe:
            // Runtime.getRuntime().exec(userInput);

            // ==============================
            // BUG 2: OS Dependency
            // ==============================
            List<String> command;

            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                command = Arrays.asList("cmd", "/c", userInput);
            } else {
                command = Arrays.asList("sh", "-c", userInput);
            }

            // ==============================
            // BUG 3: PATH Hijacking
            // ==============================
            // If "dir", "ls", etc. are malicious binaries
            // placed earlier in PATH, attacker code executes.

            ProcessBuilder pb = new ProcessBuilder(command);

            // ==============================
            // BUG 4: Environment Variable Injection
            // ==============================
            pb.environment().put("CUSTOM_VAR", "unsafe");

            Process process = pb.start();

            // ==============================
            // BUG 5 + 11:
            // stdout/stderr deadlock
            // ==============================
            StreamReader stdout =
                    new StreamReader(process.getInputStream(), "STDOUT");

            StreamReader stderr =
                    new StreamReader(process.getErrorStream(), "STDERR");

            stdout.start();
            stderr.start();

            // ==============================
            // BUG 6:
            // Infinite process hanging
            // ==============================
            boolean finished =
                    process.waitFor(
                            PROCESS_TIMEOUT_SECONDS,
                            TimeUnit.SECONDS
                    );

            if (!finished) {

                process.destroyForcibly();

                System.out.println(
                        "Process killed due to timeout."
                );

                return;
            }

            int exitCode = process.exitValue();

            stdout.join();
            stderr.join();

            // ==============================
            // BUG 7:
            // Silent failures
            // ==============================
            if (exitCode != 0) {

                System.out.println(
                        "Process failed with exit code: "
                                + exitCode
                );

            } else {

                System.out.println(
                        "Process completed successfully."
                );
            }

            // ==============================
            // BUG 8:
            // Encoding issues
            // ==============================
            // Using UTF-8 here, but Windows terminals
            // may use Cp1252/Cp850.

            // ==============================
            // BUG 9:
            // Resource exhaustion possibility
            // ==============================
            // If this code is looped thousands of times,
            // system resources may be exhausted.

            // ==============================
            // BUG 10:
            // Argument parsing issue
            // ==============================
            // Example:
            // mkdir "test folder"
            //
            // Runtime.exec(String) splits badly.
            // ProcessBuilder(List<String>) is safer.

        } catch (IOException e) {

            System.out.println(
                    "IOException occurred:"
            );

            e.printStackTrace();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Thread interrupted."
            );

        } catch (Exception e) {

            // ==============================
            // BUG 12:
            // Generic catch-all
            // ==============================
            e.printStackTrace();
        }
    }

    // ==============================
    // Stream Reader Thread
    // ==============================
    static class StreamReader extends Thread {

        private final InputStream inputStream;
        private final String streamName;

        StreamReader(
                InputStream inputStream,
                String streamName
        ) {

            this.inputStream = inputStream;
            this.streamName = streamName;
        }

        @Override
        public void run() {

            try (
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(
                                            inputStream,
                                            StandardCharsets.UTF_8
                                    )
                            )
            ) {

                String line;

                while ((line = br.readLine()) != null) {

                    System.out.println(
                            "[" + streamName + "] " + line
                    );
                }

            } catch (IOException e) {

                System.out.println(
                        "Error reading "
                                + streamName
                                + ": "
                                + e.getMessage()
                );
            }
        }
    }
}

