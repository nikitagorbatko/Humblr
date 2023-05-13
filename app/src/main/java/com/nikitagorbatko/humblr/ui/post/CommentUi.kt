package com.nikitagorbatko.humblr.ui.post

import android.os.Bundle
import com.gg.gapo.treeviewlib.model.NodeData

data class CommentUi(
    override val nodeViewId: String,
    var id: String?,
    var subreddit: String?,
    var selftext: String?,
    var saved: Boolean?,
    var title: String?,
    var downs: Int?,
    var name: String?,
    var upvoteRatio: Double?,
    var body: String?,
    var authorFullname: String?,
    //var likes: String?,
    var author: String?,
    var createdUtc: Int?,
    var created: Int?,
    var numComments: Int?,
    var replies: List<CommentUi>,
    var url: String?,
    var isVideo: Boolean?,
) : NodeData<CommentUi> {
    override fun areContentsTheSame(item: NodeData<CommentUi>): Boolean {
        return if (item !is CommentUi) {
            false
        } else {
            item.body == body
        }
    }

    override fun areItemsTheSame(item: NodeData<CommentUi>): Boolean {
        return if (item !is CommentUi) {
            false
        } else {
            nodeViewId == item.nodeViewId
        }
    }

    override fun getChangePayload(item: NodeData<CommentUi>): Bundle {
        return Bundle()
    }

    override fun getNodeChild(): List<NodeData<CommentUi>> {
        return replies
    }

}
