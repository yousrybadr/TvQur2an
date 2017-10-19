package com.pentavalue.tvquran.network.managers;


import com.pentavalue.tvquran.application.ApplicationController;
import com.pentavalue.tvquran.network.listeneres.OnEntityListReciveListner;
import com.pentavalue.tvquran.network.listeneres.OnEntityReceivedListener;
import com.pentavalue.tvquran.network.listeneres.OnSuccessVoidListener;
import com.pentavalue.tvquran.network.listeneres.UiListener;

import java.util.ArrayList;
import java.util.List;


/**
 * An abstraction of any business manager which should be parameterized with the
 * type of entity this manager deals with, this entity should be a subclass of
 *
 * @author Passant Mohamed
 */
public abstract class BusinessManager<T> {

    private Class<T> mClass;

    /**
     * list of all listener used across the application as a centralized place
     * add your listener to this list
     */
    private List<UiListener> Uilisteners;

    /**
     * Acts as a default constructor which should be called in all constructors
     * of children managers.
     *
     * @param clazz Class instance of the entity upon which the manager is based
     *              on.
     */
    protected BusinessManager(Class<T> clazz) {
        this.mClass = clazz;
        Uilisteners = new ArrayList<UiListener>();
    }

    /**
     * add your listener to UiListeners list using this method
     *
     * @param listener
     */
    public void addListener(UiListener listener) {
        if (!Uilisteners.contains(listener))
            Uilisteners.add(listener);
    }

    /**
     * remove your listener from UiListeners using this method
     *
     * @param listener
     */
    public void removeListener(UiListener listener) {
        if (!ApplicationController.appEnterBackGround) {
            if (Uilisteners.contains(listener))
                Uilisteners.remove(listener);
        }
    }

    /**
     * A method to get UiListeners list
     *
     * @return
     */
    protected List<UiListener> getListeners() {
        return Uilisteners;
    }

    protected <D extends OnSuccessVoidListener> void notifyVoidSuccess(
            final Class<D> listenerClass) {
        ApplicationController.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (UiListener listener : getListeners())
                    if (listenerClass.isInstance(listener))
                        ((OnSuccessVoidListener) listener).onSuccess();
            }
        });
    }

    @SuppressWarnings("hiding")
    protected <T, D extends OnEntityReceivedListener<?>> void notifyEntityReceviedSuccess(
            final T entity, final Class<D> listenerClass) {
        ApplicationController.getInstance().runOnUiThread(new Runnable() {
            // Added by Mahmoud Elmorabea
            // A warning of unchecked casting is suppressed here
            // because the compiler cannot know for sure that the passed
            // listener type's generic T
            // is of the same one that is being cast to
            // So you're just gonna have to trust me on that one :)
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                for (UiListener listener : getListeners())
                    if (listenerClass.isInstance(listener))
                        ((OnEntityReceivedListener<T>) listener)
                                .onSuccess(entity);
            }
        });
    }

    /**
     * Same as {@link #notifyEntityReceviedSuccess(Object, Class)} but instead
     * of an entity object, it returns a {@link List} of entities.
     *
     * @param entityList    The received entities list.
     * @param listenerClass {@link Class} object of the desired listeners to be notified.
     */
    @SuppressWarnings("hiding")
    protected <T, D extends OnEntityListReciveListner<?>> void notifyEntityListReceviedSuccess(
            final List<T> entityList, final Class<D> listenerClass) {
        ApplicationController.getInstance().runOnUiThread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                for (UiListener listener : getListeners())
                    if (listenerClass.isInstance(listener))
                        ((OnEntityListReciveListner<T>) listener)
                                .onSuccess(entityList);
            }
        });
    }

    /**
     * Same as {@link #notifyEntityReceviedSuccess(Object, Class)} but notifies
     * the listeners of an exception that has occurred.
     *
     * @param e
     * @param listenerClass
     */
    @SuppressWarnings("hiding")
    protected <T extends UiListener> void notifyRetrievalException(
            final Exception e, final Class<T> listenerClass) {
        ApplicationController.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (UiListener listener : getListeners())
                    if (listenerClass.isInstance(listener))
                        ((UiListener) listener).onException(e);
            }
        });
    }

   /* public <D extends BusinessObject> List<D> handleObjectListServerResponse(
            BaseResponse response, Class<D> clazz) throws AppException {
        if (response.getStatus() == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
            if (response.getData().equals("[]")) {// TODO remove
                throw new AppException(AppException.NO_DATA_EXCEPTION);
            }
            return (List<D>) DataHelper.deserializeList(response.getData(),
                    clazz);
        } else
            throw new AppException(BaseResponse.getExceptionType(response
                    .getStatus()));
    }*/
}
