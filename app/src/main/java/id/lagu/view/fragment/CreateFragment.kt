package id.lagu.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.lagu.R

class CreateFragment : Fragment() {

    companion object {
        fun newInstance(): CreateFragment {
            var fragment = CreateFragment()
            var args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_create, container, false)
        return rootView
    }
}