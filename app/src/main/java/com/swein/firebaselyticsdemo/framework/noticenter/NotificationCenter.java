package com.swein.firebaselyticsdemo.framework.noticenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/*

 Key Value Observing 패턴 클래스
 Singleton
 */

public class NotificationCenter {

    public static String DATA_KEY = "DATA";
    private static NotificationCenter                                   instance = new NotificationCenter();
    private HashMap< String, ArrayList< NotificationObserver > > map      = new HashMap< String, ArrayList< NotificationObserver > >();

    private NotificationCenter() {
    }

    public static NotificationCenter getDefaultCenter() {
        return instance;
    }

    public static HashMap< String, Object > dataWithObject(Object obj ) {
        HashMap< String, Object > data = new HashMap<>();

        data.put( DATA_KEY, obj );

        return data;
    }

    public static Object objFromData(HashMap< String, Object > obj ) {
        return obj.get( DATA_KEY );
    }

    public void addObserverForKey(String key, Object delegate, NotificationCenterRunnable runnable ) {
        NotificationObserver noti = new NotificationObserver( key, delegate, runnable );

        getListOfNotificationObserverForKey( key ).add( noti );
    }

    public void addObserverForKeyIgnoreIfExist(String key, Object delegate, NotificationCenterRunnable runnable ) {
        NotificationObserver noti = new NotificationObserver( key, delegate, runnable );

        if ( false == getListOfNotificationObserverForKey( key ).contains( delegate ) ) {
            getListOfNotificationObserverForKey( key ).add( noti );
        }
    }

    public void addObserverForKeys(Object delegate, NotificationCenterRunnable runnable, String... keys ) {
        for ( String key : keys ) {
            getListOfNotificationObserverForKey( key ).add( new NotificationObserver( key, delegate, runnable ) );
        }
    }

    public void removeAllObserver( Object delegate ) {

        ArrayList< NotificationObserver > itemToDelete = new ArrayList< NotificationObserver >();
        for ( ArrayList< NotificationObserver > arr : map.values() ) {
            itemToDelete.clear();
            for ( NotificationObserver observer : arr ) {
                if ( observer.delegate.get() == delegate ) {
                    itemToDelete.add( observer );
                }
            }
            arr.removeAll( itemToDelete );
        }
    }

    public void removeObserverForKey(String key, Object delegate ) {

        ArrayList< NotificationObserver > result = getListOfNotificationObserverForKey( key );

        ArrayList< NotificationObserver > itemToDelete = new ArrayList< NotificationObserver >();

        Object delegateRef;
        for ( NotificationObserver notificationObserver : result ) {
            delegateRef = notificationObserver.delegate.get();
            if ( delegateRef == delegate ) {
                itemToDelete.add( notificationObserver );
            }
        }

        for ( NotificationObserver notificationObserver : itemToDelete ) {
            result.remove( notificationObserver );
        }
    }

    public void postNotification(String key, Object poster, HashMap< String, Object > data ) {
        ArrayList< NotificationObserver > result = getListOfNotificationObserverForKey( key );
        for ( NotificationObserver notificationObserver : result ) {
            if ( notificationObserver.runnable != null ) {
                notificationObserver.runnable.run( key, poster, data );
            }
        }
    }

    ArrayList< NotificationObserver > getListOfNotificationObserverForKey(String key ) {
        ArrayList< NotificationObserver > result = map.get( key );

        if ( result == null ) {
            result = new ArrayList< NotificationObserver >();
            map.put( key, result );
        }

        return result;
    }

    public interface NotificationCenterRunnable {
        void run(String key, Object poster, HashMap<String, Object> data);
    }


    public class NotificationObserver {
        public String key;
        public WeakReference< Object > delegate;
        public NotificationCenterRunnable runnable;

        public NotificationObserver(String key, Object delegate, NotificationCenterRunnable runnable ) {
            this.key = key;
            this.delegate = new WeakReference< Object >( delegate );
            this.runnable = runnable;
        }

    }

}
