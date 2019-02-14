package com.swein.firebaselyticsdemo.framework.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by seokho on 19/12/2016.
 */

public class ThreadUtil {

    //fixed number thread pool, can reuse
    static private ExecutorService executor           = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() );

    //only one thread can run at one time
    static private ExecutorService executorSequential = Executors.newSingleThreadExecutor();

    public static void createThreadWithoutUI(final Runnable runnable) {

        new Thread(){
            public void run(){

                runnable.run();

            }
        }.start();
    }

    public static void createThreadWithUI(final int delayMillis, final Runnable runnable) {

        final Handler handle = new Handler(Looper.getMainLooper());

        new Thread(){
            public void run(){

                handle.postDelayed(runnable , delayMillis);

            }
        }.start();
    }

    @Override
    protected void finalize() throws Throwable {
        if ( executor != null && executor.isShutdown() ) {
            executor.shutdown();
        }
        if ( executorSequential != null && executorSequential.isShutdown() ) {
            executorSequential.shutdown();
        }
        super.finalize();
    }


    private static Handler handle = new Handler( Looper.getMainLooper() );

    public static Handler startUIThread(int delayMillis, Runnable runnable ) {

        handle.postDelayed( runnable, delayMillis );

        return handle;
    }

    public static Future< ? > startThread(final Runnable runnable) {
        Future< ? > future = executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                }
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
            }
        });

        return future;
    }

    //***************** [Do not delete this part] **********************
    //    public static Future< ? > startThreadWithExceptionReport( Runnable r, Context context ) {
    //
    //        Future< ? > future = executor.submit( r );
    //
    //        try {
    //            future.get();
    //        }
    //        catch ( InterruptedException | ExecutionException exception ) {
    //           TrackerManager.sendExceptionReport( context, exception, true, true );
    //        }
    //
    //        return future;
    //    }

    //    public static Future< ? > startSeqThread( Runnable r ) {
    //        return executorSequential.submit( r );
    //    }

    public static Future< ? > startSeqThread(final Runnable runnable ) {
        Future< ? > future = executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                }
                catch ( Exception exception ) {
                    exception.printStackTrace();
                }
            }
        });

        return future;
    }

    //***************** [Do not delete this part] **********************
    //    public static Future< ? > startSeqThreadWithExceptionReport( Runnable r, Context context ) {
    //
    //        Future< ? > future = executorSequential.submit( r );
    //
    //        try {
    //            future.get();
    //        }
    //        catch ( InterruptedException | ExecutionException exception ) {
    //            TrackerManager.sendExceptionReport( context, exception, true, true );
    //        }
    //
    //        return future;
    //    }
}
