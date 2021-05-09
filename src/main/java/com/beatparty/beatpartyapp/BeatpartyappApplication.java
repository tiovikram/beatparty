package com.beatparty.beatpartyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class runs the SpringBoard application that builds the BeatParty platform.
 */
@SpringBootApplication
public class BeatpartyappApplication {

    /**
     * Application main method.
     *
     * @param args - default parameters for main method
     */
    public static void main(String[] args) {
        SpringApplication.run(BeatpartyappApplication.class, args);
    }

}
