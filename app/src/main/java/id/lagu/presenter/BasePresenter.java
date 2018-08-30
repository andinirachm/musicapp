package id.lagu.presenter;

public interface BasePresenter<V> {
    void onAttachView(V view);
    void onDetachView();
}
