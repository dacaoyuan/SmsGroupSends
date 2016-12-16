package com.eveyoo.sendmessagedaemon;

import android.app.Application;
import android.os.Environment;

import com.elvishew.xlog.BuildConfig;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;

import java.io.File;

/**
 * Created by abc on 2016/12/16.
 */

public class InitApplication extends Application {

    public static Printer globalFilePrinter;

    @Override
    public void onCreate() {
        initXlog();
    }

    private void initXlog() {
        LogConfiguration config = new LogConfiguration.Builder()
                //.logLevel(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE)
                .logLevel(LogLevel.ALL)
                .tag("InitAppLog")                   // Specify TAG, default: "X-LOG"
                .t()                                                // Enable thread info, disabled by default
                //.st(2)                                              // Enable stack trace info with depth 2, disabled by default
                .b()                                                // Enable border, disabled by default
                // .jsonFormatter(new MyJsonFormatter())               // Default: DefaultJsonFormatter
                // .xmlFormatter(new MyXmlFormatter())                 // Default: DefaultXmlFormatter
                // .throwableFormatter(new MyThrowableFormatter())     // Default: DefaultThrowableFormatter
                // .threadFormatter(new MyThreadFormatter())           // Default: DefaultThreadFormatter
                // .stackTraceFormatter(new MyStackTraceFormatter())   // Default: DefaultStackTraceFormatter
                // .borderFormatter(new MyBoardFormatter())            // Default: DefaultBorderFormatter
                // .addObjectFormatter(AnyClass.class,                 // Add formatter for specific class of object
                //     new AnyClassObjectFormatter())                  // Use Object.toString() by default
                .addInterceptor(new BlacklistTagsFilterInterceptor(    // Add blacklist tags filter
                        "blacklist1", "blacklist2", "blacklist3"))
                // .addInterceptor(new WhitelistTagsFilterInterceptor( // Add whitelist tags filter
                //     "whitelist1", "whitelist2", "whitelist3"))
                // .addInterceptor(new MyInterceptor())                // Add a log interceptor
                .build();

        Printer androidPrinter = new AndroidPrinter();             // Printer that print the log using android.util.Log
        Printer filePrinter = new FilePrinter                      // Printer that print the log to the file system
                .Builder(new File(Environment.getExternalStorageDirectory(), "xlogsample").getPath())       // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())        // Default: ChangelessFileNameGenerator("log")
                // .backupStrategy(new MyBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)
                .logFlattener(new ClassicFlattener())                  // Default: DefaultFlattener
                .build();

        XLog.init(config);

        // For future usage: partial usage in MainActivity.
        globalFilePrinter = filePrinter;


    }
}


