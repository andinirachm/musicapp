package id.lagu.presenter

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import id.lagu.interfaces.FindView
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class FindPresenterTest {
    private lateinit var presenter: FindPresenter
    private lateinit var view: FindView

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = FindPresenter()
        view = mock()
        presenter.onAttachView(view)
    }

    @Test
    fun searchTrackWithEmptyQuery() {
        presenter.searchTrack("")
        verify(view, never()).onFailure("Please set query.")
    }
}