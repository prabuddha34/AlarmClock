
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String location = "D:\\New_Java_IDE\\Indian_Airways_2024_Management_System\\Inheritance_Java_Practice\\FiguringItOut\\src\\Seagull - Telecasted (1).wav";
        File file = new File(location);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime alarmTime = null;

        // Step 1: Ask for the alarm time
        while (alarmTime == null) {
            try {
                System.out.println("Enter the Alarm Time (format HH:mm:ss): ");
                String time = scanner.nextLine();
                alarmTime = LocalTime.parse(time, formatter);
                System.out.println("Alarm set for: " + alarmTime.format(formatter));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Format. Please use HH:mm:ss");
            }
        }

        scanner.close();

        // Step 2: Wait until alarm time
        System.out.println("Waiting for the alarm time...");

        while (true) {
            LocalTime currentTime = LocalTime.now().withNano(0); // Remove nanoseconds for accurate comparison
            System.out.println("Current time: " + currentTime.format(formatter));

            if (currentTime.equals(alarmTime)) {
                System.out.println("⏰ Wake up! It's " + currentTime.format(formatter));
                playSound(file); // Play sound once alarm triggers
                break;
            }

            try {
                Thread.sleep(1000); // Wait 1 second
            } catch (InterruptedException e) {
                System.out.println("Alarm checking interrupted.");
                break;
            }
        }
    }

    // Method to play audio
    public static void playSound(File soundFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Optional: wait until the sound completes
            while (clip.isRunning()) {
                Thread.sleep(100);
            }

            clip.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format.");
        } catch (IOException e) {
            System.out.println("I/O error while playing the sound.");
        } catch (LineUnavailableException e) {
            System.out.println("Audio line unavailable.");
        } catch (InterruptedException e) {
            System.out.println("Playback interrupted.");
        }
    }
}
