package ua.udevapp.libraries.sample.presentation.recycler.view

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.udevapp.libraries.R
import ua.udevapp.libraries.databinding.FragmentRecyclerListBinding

class RecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {
    private val binding by viewBinding(FragmentRecyclerListBinding::bind)
}