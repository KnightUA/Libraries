package ua.udevapp.libraries.sample.presentation.navigation.view

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.udevapp.libraries.R
import ua.udevapp.libraries.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {
    private val binding by viewBinding(ActivityNavigationBinding::bind)
}