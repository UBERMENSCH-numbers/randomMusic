package com.example.randommusic.mvp.contracts;

import com.example.randommusic.interfaces.IPresenter;
import com.example.randommusic.interfaces.IView;

public interface MainActivityContract {
    interface View extends IView {
        Boolean hasFragment();
    }

    interface Presenter extends IPresenter<View> {

    }

    interface Model{

    }
}