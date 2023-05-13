package com.nikitagorbatko.humblr.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gg.gapo.treeviewlib.GapoTreeView
import com.gg.gapo.treeviewlib.model.NodeViewData
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.databinding.FragmentSinglePostBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SinglePostFragment : Fragment() {
    private var _binding: FragmentSinglePostBinding? = null
    private val binding get() = _binding!!

    private val args: SinglePostFragmentArgs by navArgs()
    private val viewModel: SinglePostViewModel by viewModel()

    private lateinit var treeView: GapoTreeView<CommentUi>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSinglePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adapter = CommentsAdapter(
//            onItemClick = {
//                val action = SinglePostFragmentDirections.actionSinglePostFragmentToUserFragment(it)
//                findNavController().navigate(action)
//            },
//            onVoteDown = {
//                viewModel.voteDown(it)
//            },
//            onVoteUp = {
//                viewModel.voteUp(it)
//            },
//            saveComment = {
//                viewModel.saveComment(it)
//            },
//            unsaveComment = {
//                viewModel.unsaveComment(it)
//            }
//        )

        binding.toolbarSinglePost.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //binding.recyclerComments.adapter = adapter

        binding.toolbarSinglePost.title = args.post.data.title

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getComments(args.post.data.id)
            viewModel.comments.collect {
                if (it != null) {
                    configure(it)
                    //adapter.addAll(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    SinglePostViewModel.State.PRESENT -> {
                        binding.recyclerComments.visibility = View.VISIBLE
                        binding.progressSinglePost.visibility = View.GONE
                        binding.textSinglePostError.visibility = View.GONE
                    }
                    SinglePostViewModel.State.ERROR -> {
                        binding.recyclerComments.visibility = View.GONE
                        binding.progressSinglePost.visibility = View.GONE
                        binding.textSinglePostError.visibility = View.VISIBLE
                    }
                    SinglePostViewModel.State.LOADING -> {
                        binding.recyclerComments.visibility = View.GONE
                        binding.progressSinglePost.visibility = View.VISIBLE
                        binding.textSinglePostError.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun configure(list: List<CommentUi>) {
        treeView = GapoTreeView.Builder.plant<CommentUi>(requireContext())
            .withRecyclerView(binding.recyclerComments)
            .withLayoutRes(R.layout.test_comment_item)
            .setListener(object : GapoTreeView.Listener<CommentUi> {
                override fun onBind(
                    holder: View,
                    position: Int,
                    item: NodeViewData<CommentUi>,
                    bundle: Bundle?
                ) {
                    val comment = item.getData()
                    val timeCreated = comment.created?.let { getDateTime(it) }
                    val saved = comment.saved == true

                    holder.findViewById<TextView>(R.id.text_name).text = comment.author
                    holder.findViewById<TextView>(R.id.text_body).text = comment.body
                    holder.findViewById<TextView>(R.id.text_time).text = timeCreated ?: "-:-"

                    holder.findViewById<ImageView>(R.id.image_save).setImageResource(
                        if (saved) {
                            R.drawable.ic_favourited
                        } else {
                            R.drawable.ic_favourite
                        }
                    )

                    holder.findViewById<ImageView>(R.id.image_vote_down).setOnClickListener {
                        comment.name?.let { it1 -> viewModel.voteDown(it1) }
                        Toast.makeText(requireContext(), "vote down", Toast.LENGTH_SHORT).show()
                    }
                    holder.findViewById<ImageView>(R.id.image_vote_up).setOnClickListener {
                        comment.name?.let { it1 -> viewModel.voteUp(it1) }
                        Toast.makeText(requireContext(), "vote up", Toast.LENGTH_SHORT).show()
                    }

                    val commentsAmount = comment.replies.size
                    holder.findViewById<TextView>(R.id.text_comments_amount).text =
                        "${commentsAmount ?: ""}"

                    val imageSave = holder.findViewById<ImageView>(R.id.image_save)
                    imageSave.setOnClickListener {
                        if (comment.saved == true) {
                            imageSave.setImageResource(R.drawable.ic_favourite)
                            comment.saved = false
                            comment.name?.let { it1 -> viewModel.saveComment(it1) }
                        } else {
                            imageSave.setImageResource(R.drawable.ic_favourited)
                            comment.saved = true
                            comment.name?.let { it1 -> viewModel.saveComment(it1) }
                        }

                    }
                    holder.findViewById<TextView>(R.id.text_name).setOnClickListener {
                        comment.author?.let { it1 ->
                            val action =
                                SinglePostFragmentDirections.actionSinglePostFragmentToUserFragment(
                                    it1
                                )
                            findNavController().navigate(action)
                        }
                    }
                    //toggle node
                    holder.setOnClickListener {
                        if (item.isExpanded) {
                            treeView.collapseNode(item.nodeId)
                        } else {
                            treeView.expandNode(item.nodeId)
                        }
                    }
                }

                override fun onNodeSelected(
                    node: NodeViewData<CommentUi>,
                    child: List<NodeViewData<CommentUi>>,
                    isSelected: Boolean
                ) {
                    TODO("Not yet implemented")
                }

            })
            .setData(list)
            .build()
    }

    private fun getDateTime(s: Int): String? {
        return try {
            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}