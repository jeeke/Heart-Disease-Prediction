package me.gyanesh.hdp.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import com.shreyaspatil.MaterialDialog.MaterialDialog
import kotlinx.android.synthetic.main.fragment_test.*
import me.gyanesh.hdp.R
import me.gyanesh.hdp.data.Result
import me.gyanesh.hdp.data.model.TestReport
import me.gyanesh.hdp.databinding.FragmentTestBinding
import me.gyanesh.hdp.ui.BaseFragment
import me.gyanesh.hdp.ui.RootViewModel
import me.gyanesh.hdp.util.createViewModel
import me.gyanesh.hdp.util.hide
import me.gyanesh.hdp.util.show
import me.gyanesh.hdp.util.toastError
import org.kodein.di.direct
import org.kodein.di.generic.instance

class TestFragment : BaseFragment() {

    private val viewModel by lazy {
        createViewModel {
            RootViewModel(kodein.direct.instance())
        }
    }
    lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        submit.setOnClickListener {
            viewModel.predictRisk()
        }

        setCategoricalInputs()
        viewModel.testReport.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    progressBar2.hide()
                    showResult(it.data)
                }
                is Result.Loading -> {
                    progressBar2.show()
                }
                is Result.Error -> {
                    progressBar2.hide()
                    context.toastError(it.exception.message)
                }
            }
        }
    }

    private fun setCategoricalInputs() {

        setCategoricalAdapter(binding.gender, R.array.gender_values) {
            viewModel.testInput.sex = it
        }

        setCategoricalAdapter(binding.cp, R.array.cp_values) {
            viewModel.testInput.cp = it
        }

        setCategoricalAdapter(binding.fbs, R.array.fbs_values) {
            viewModel.testInput.fbs = it
        }

        setCategoricalAdapter(binding.restecg, R.array.restecg_values) {
            viewModel.testInput.restecg = it
        }

        setCategoricalAdapter(binding.exang, R.array.exang_values) {
            viewModel.testInput.exang = it
        }

        setCategoricalAdapter(binding.slope, R.array.slope_values) {
            viewModel.testInput.slope = it
        }

        setCategoricalAdapter(binding.ca, R.array.ca_values) {
            viewModel.testInput.ca = it
        }

        setCategoricalAdapter(binding.thal, R.array.thal_values) {
            viewModel.testInput.thal = it
        }

    }

    private fun setCategoricalAdapter(
        view: AutoCompleteTextView,
        arrayId: Int,
        valueSetter: (position: Int) -> Unit
    ) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.holder_dropdown_item,
            resources.getStringArray(arrayId)
        )
        view.setAdapter(adapter)
        view.setOnItemClickListener { _, _, position, _ ->
            valueSetter(position)
        }
    }

    private fun showResult(testReport: TestReport) {
        val title: String
        val resId: Int
        when (testReport.risk) {
            "1" -> {
                title = getString(R.string.risk_message)
                resId = R.raw.risk
            }
            "0" -> {
                title = getString(R.string.no_risk)
                resId = R.raw.no_risk
            }
            else -> {
                context.toastError(testReport.message)
                return
            }
        }
        MaterialDialog.Builder(requireActivity())
            .setTitle(title)
            .setAnimation(resId)
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog: com.shreyaspatil.MaterialDialog.interfaces.DialogInterface, _: Int ->
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .setCancelable(false)
            .build()
            .show()
    }
}