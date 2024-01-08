package com.javascape;

/** A class containing all of the variables that are used for the settings */
public class Settings {

    public static String version = "a0.0.1";

    /** Boolean that determines whether or not to display the terminal */
    public static boolean showTerminal = true;

    /** Boolean that determines if the Logger is enabled */
    public static boolean loggerEnabled = true;

    /** Determines if the Logger prints to the terminal in app */
    public static boolean loggerTerminalOutput = true;
    /** Determines if the Logger prints to the debug console */
    public static boolean loggerConsole = true;
    /** Determines if the Logger prints to a log file */
    public static boolean loggerFile = true;

    /** Boolean that determines if the Logger is enabled */
    public static boolean debugEnabled = true;

    /** Determines if the Logger prints the debug to the terminal in app */
    public static boolean debugTerminalOutput = true;
    /** Determines if the Logger prints the debug to the debug console */
    public static boolean debugConsole = true;
    /** Determines if the Logger prints the debug to a log file */
    public static boolean debugFile = true;

    /** The location of the data files */
    public static String storageLocation = "./data/";

    /** The port that the server runs on */
    public static int port = 19;


    /**
     * How long the serverThread will wait for a response.
     */
    public static int timeOut = 15;

    /** Whether or not to automagically login to the application */
    public static boolean autoLogin = true;

    public static String loggedInEmailAuto = "admin";
}
