package ua.udevapp.samples.ui.navigation.view

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ua.udevapp.libraries.R
import ua.udevapp.libraries.databinding.ActivityNavigationBinding

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {
    private val binding by viewBinding(ActivityNavigationBinding::bind)
}