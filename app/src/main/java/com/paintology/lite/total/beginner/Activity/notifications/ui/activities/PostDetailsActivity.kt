package com.paintology.lite.total.beginner.Activity.notifications.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paintology.lite.total.beginner.Activity.BaseActivity
import com.paintology.lite.total.beginner.Activity.notifications.adapter.CommunityPostAdapterNotification
import com.paintology.lite.total.beginner.Activity.notifications.models.CommunityPostNotification
import com.paintology.lite.total.beginner.Activity.utils.hide
import com.paintology.lite.total.beginner.Activity.utils.onSingleClick
import com.paintology.lite.total.beginner.Activity.utils.showToast
import com.paintology.lite.total.beginner.R
import com.paintology.lite.total.beginner.databinding.ActivityPostDetailsBinding
import com.paintology.lite.total.beginner.util.MarginDecoration
import com.paintology.lite.total.beginner.util.StringConstants

class PostDetailsActivity : BaseActivity() {

    private val binding by lazy {
        ActivityPostDetailsBinding.inflate(layoutInflater)
    }

    private var postNotification = mutableListOf<CommunityPostNotification>()
    private var mAdapter: CommunityPostAdapterNotification? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initRv()
        notificationsViewModel.fetchCommunityPost(intent.getStringExtra("post_id") ?: return, {
            Log.w("fetchCommunityPost", "onCreate: $it")
            postNotification.add(it)
            mAdapter?.notifyDataSetChanged()
        }, {
            showToast("$it")
        })
    }

    private fun initRv() {
        mAdapter = CommunityPostAdapterNotification(postNotification, this@PostDetailsActivity)
        var type = 1
        try {
            type = (if (StringConstants().getInt(StringConstants().formatType, this@PostDetailsActivity) == 0) 1 else StringConstants().getInt(StringConstants().formatType, this@PostDetailsActivity))
        } catch (e: Exception) {
        }

        val mLayoutManager = GridLayoutManager(this@PostDetailsActivity, type, RecyclerView.VERTICAL, false)
        binding.rvCommunityDetail.setLayoutManager(mLayoutManager)
        binding.rvCommunityDetail.addItemDecoration(MarginDecoration(this@PostDetailsActivity))
        binding.rvCommunityDetail.setHasFixedSize(true)
        binding.rvCommunityDetail.setAdapter(mAdapter)
        mAdapter!!.notifyList(type)
    }

    private fun initToolbar() {
        binding.customToolbar.apply {
            imgMenu.onSingleClick {
                onBackPressedDispatcher.onBackPressed()
            }
            tvTitle.text = getString(R.string.post_detail)
            imgFav.hide()
        }
    }

}