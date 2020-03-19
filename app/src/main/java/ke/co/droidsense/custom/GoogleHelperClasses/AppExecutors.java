package ke.co.droidsense.custom.GoogleHelperClasses;

/**
 * Create AppExecutors class to handle multithreading using method synchronizing.
 */

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static volatile AppExecutors mInstance;
    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
    }

    private AppExecutors() {
        this( Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool( 3 ),
                new MainThreadExecutor() );
    }

    public static AppExecutors getInstance() {
        if (mInstance == null) {
            synchronized (AppExecutors.class) {
                mInstance = new AppExecutors();
            }
        }
        return mInstance;
    }

    public static void xDisk(Runnable command) {
        getInstance().diskIO().execute( command );
    }

    public static void xNet(Runnable command) {
        getInstance().networkIO().execute( command );
    }

    public static void xMain(Runnable command) {
        getInstance().mainThread().execute( command );
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler( Looper.getMainLooper() );

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post( command );
        }
    }
}
