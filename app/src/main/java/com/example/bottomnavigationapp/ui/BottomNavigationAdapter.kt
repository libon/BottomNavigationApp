package com.example.bottomnavigationapp.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.bottomnavigationapp.R
import com.example.bottomnavigationapp.ui.dashboard.DashboardFragment
import com.example.bottomnavigationapp.ui.home.HomeFragment
import com.example.bottomnavigationapp.ui.notifications.NotificationsFragment

class BottomNavigationAdapter(private val fm: FragmentManager) {
    companion object {
        private val tabIds = listOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications,
        )
    }

    fun createAllTabs() {
        val transaction = fm.beginTransaction()
        tabIds.forEach { tabId ->
            val fragment: Fragment = getItem(tabId) ?: (createFragment(tabId).also {
                transaction.add(R.id.selected_tab_content, it, getTag(tabId))
            })
            transaction.setMaxLifecycle(fragment, Lifecycle.State.CREATED)
            transaction.hide(fragment)
        }
        transaction.commitAllowingStateLoss()
    }

    fun openTab(@IdRes selectedTabId: Int) {
        val transaction = fm.beginTransaction()
        tabIds.forEach { tabId ->
            fm.findFragmentByTag(getTag(tabId))?.let { fragment ->
                if (tabId != selectedTabId) {
                    transaction.hide(fragment)
                    transaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                } else {
                    transaction.show(fragment)
                    transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun createFragment(@IdRes tabId: Int) =
        when (tabId) {
            R.id.navigation_home -> HomeFragment()
            R.id.navigation_dashboard -> DashboardFragment()
            else /*R.id.navigation_notifications */ -> NotificationsFragment()
        }

    private fun getItem(@IdRes tabId: Int): Fragment? = fm.findFragmentByTag(getTag(tabId))

    private fun getTag(@IdRes tabId: Int) =
        when (tabId) {
            R.id.navigation_home -> "home"
            R.id.navigation_dashboard -> "dashboard"
            else /*R.id.navigation_notifications*/ -> "notifications"
        }
}
