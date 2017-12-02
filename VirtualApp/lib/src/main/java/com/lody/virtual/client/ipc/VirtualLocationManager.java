package com.lody.virtual.client.ipc;

import android.os.IBinder;
import android.os.RemoteException;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.remote.vloc.VCell;
import com.lody.virtual.remote.vloc.VLocation;
import com.lody.virtual.server.IVirtualLocationManager;

import java.util.List;

/**
 * @author Lody
 */

public class VirtualLocationManager {

    private static final VirtualLocationManager sInstance = new VirtualLocationManager();
    public static final int MODE_CLOSE = 0;
    public static final int MODE_USE_GLOBAL = 1;
    public static final int MODE_USE_SELF = 2;
    private IVirtualLocationManager mRemote;


    public static VirtualLocationManager get() {
        return sInstance;
    }


    public IVirtualLocationManager getRemote() {
        if (mRemote == null ||
                (!mRemote.asBinder().isBinderAlive() && !VirtualCore.get().isVAppProcess())) {
            synchronized (this) {
                Object remote = getRemoteInterface();
                mRemote = LocalProxyUtils.genProxy(IVirtualLocationManager.class, remote);
            }
        }
        return mRemote;
    }

    private Object getRemoteInterface() {
        final IBinder binder = ServiceManagerNative.getService(ServiceManagerNative.VIRTUAL_LOC);
        if (VirtualCore.get().isMainProcess()) {
            try {
                binder.linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        mRemote = null;
                        getRemote();
                    }
                }, 0);
            } catch (RemoteException e) {
                //ignore
            }
        }
        return IVirtualLocationManager.Stub.asInterface(binder);
    }

    public int getMode(int userId, String pkg) {
        try {
            return getRemote().getMode(userId, pkg);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public void setMode(int userId, String pkg, int mode) {
        try {
            getRemote().setMode(userId, pkg, mode);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public void setCell(int userId, String pkg, VCell cell) {
        try {
            getRemote().setCell(userId, pkg, cell);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public void setAllCell(int userId, String pkg, List<VCell> cell) {
        try {
            getRemote().setAllCell(userId, pkg, cell);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public void setNeighboringCell(int userId, String pkg, List<VCell> cell) {
        try {
            getRemote().setNeighboringCell(userId, pkg, cell);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public VCell getCell(int userId, String pkg) {
        try {
            return getRemote().getCell(userId, pkg);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public List<VCell> getAllCell(int userId, String pkg) {
        try {
            return getRemote().getAllCell(userId, pkg);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public List<VCell> getNeighboringCell(int userId, String pkg) {
        try {
            return getRemote().getNeighboringCell(userId, pkg);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }


    public void setGlobalCell(VCell cell) {
        try {
            getRemote().setGlobalCell(cell);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public void setGlobalAllCell(List<VCell> cell) {
        try {
            getRemote().setGlobalAllCell(cell);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public void setGlobalNeighboringCell(List<VCell> cell) {
        try {
            getRemote().setGlobalNeighboringCell(cell);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public void setLocation(int userId, String pkg, VLocation loc) {
        try {
            getRemote().setLocation(userId, pkg, loc);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public VLocation getLocation(int userId, String pkg) {
        try {
            return getRemote().getLocation(userId, pkg);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public void setGlobalLocation(VLocation loc) {
        try {
            getRemote().setGlobalLocation(loc);
        } catch (RemoteException e) {
            VirtualRuntime.crash(e);
        }
    }

    public VLocation getGlobalLocation() {
        try {
            return getRemote().getGlobalLocation();
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    /**
     * @param userId
     * @param gpsListener gpslistener/gssnlistener
     */
    public void addGpsStatusListener(int userId, String packageName, Object gpsListener) {
    }

    /**
     * @param userId
     * @param gpsListener gpslistener/gssnlistener
     */
    public void removeGpsStatusListener(int userId, String packageName, Object gpsListener) {

    }
}
