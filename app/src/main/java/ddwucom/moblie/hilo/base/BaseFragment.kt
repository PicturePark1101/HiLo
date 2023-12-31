package ddwucom.moblie.hilo.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutRes: Int) : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        Log.d("순서확인", "BaseFragment의 onCreateView")
        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    override fun onResume() {
        super.onResume()
        reLoadData()
    }

    override fun onPause() {
        super.onPause()
        doOnPause()
    }

    protected open fun initView() {}
    protected open fun setView() {}

    protected open fun doOnPause() {}
    protected open fun doDestroyView() {}
    protected open fun reLoadData() {}

    override fun onDestroyView() {
        _binding = null
        doDestroyView()
        super.onDestroyView()
    }
}
