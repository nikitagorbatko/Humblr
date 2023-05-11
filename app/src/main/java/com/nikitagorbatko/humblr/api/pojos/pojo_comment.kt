package com.nikitagorbatko.humblr.api.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentDto(
    @Json(name = "kind") var kind: String?,
    @Json(name = "data") var data: CommentDataDto?
)

@JsonClass(generateAdapter = true)
data class CommentsResponseDto(
    @Json(name = "kind") var kind: String?,
    @Json(name = "data") var dataDto: DataDto?
)

@JsonClass(generateAdapter = true)
data class DataDto(
    @Json(name = "after") var after: String?,
    @Json(name = "dist") var dist: String?,
    @Json(name = "modhash") var modhash: String?,
    //@Json(name = "geo_filter", ignore = true) var geoFilter: String? = null,
    @Json(name = "children") var children: List<CommentDto>?,
    @Json(name = "before") var before: String?
)

@JsonClass(generateAdapter = true)
data class GildingsDto(
    @Json(name = "kind") var kind: String?,
)

@JsonClass(generateAdapter = true)
data class CommentDataDto(
    @Json(name = "subreddit_id") var subredditId: String?,
    @Json(name = "approved_at_utc") var approvedAtUtc: String?,
    @Json(name = "author_is_blocked") var authorIsBlocked: Boolean?,
    @Json(name = "comment_type") var commentType: String?,
    @Json(name = "link_title") var linkTitle: String?,
    @Json(name = "mod_reason_by") var modReasonBy: String?,
    @Json(name = "banned_by") var bannedBy: String?,
    @Json(name = "ups") var ups: Int?,
    @Json(name = "num_reports") var numReports: String?,
    @Json(name = "author_flair_type") var authorFlairType: String?,
    @Json(name = "total_awards_received") var totalAwardsReceived: Int?,
    @Json(name = "subreddit") var subreddit: String?,
    @Json(name = "link_author") var linkAuthor: String?,
    @Json(name = "likes") var likes: String?,
    @Json(name = "replies") var repliesDto: Any?,
    @Json(ignore = true) var parsedReplies: CommentsResponseDto? = null,
    @Json(name = "user_reports") var userReports: List<String>?,
    @Json(name = "saved") var saved: Boolean?,
    @Json(name = "id") var id: String?,
    @Json(name = "banned_at_utc") var bannedAtUtc: String?,
    @Json(name = "mod_reason_title") var modReasonTitle: String?,
    @Json(name = "gilded") var gilded: Int?,
    @Json(name = "archived") var archived: Boolean?,
    @Json(name = "collapsed_reason_code") var collapsedReasonCode: String?,
    @Json(name = "no_follow") var noFollow: Boolean?,
    @Json(name = "author") var author: String?,
    @Json(name = "num_comments") var numComments: Int?,
    @Json(name = "can_mod_post") var canModPost: Boolean?,
    @Json(name = "send_replies") var sendReplies: Boolean?,
    @Json(name = "parent_id") var parentId: String?,
    @Json(name = "score") var score: Int?,
    @Json(name = "author_fullname") var authorFullname: String?,
    @Json(name = "over_18") var over18: Boolean?,
    @Json(name = "report_reasons") var reportReasons: String?,
    @Json(name = "removal_reason") var removalReason: String?,
    @Json(name = "approved_by") var approvedBy: String?,
    @Json(name = "controversiality") var controversiality: Int?,
    @Json(name = "body") var body: String?,
    @Json(name = "edited") var edited: Any?,
    @Json(name = "top_awarded_type") var topAwardedType: String?,
    @Json(name = "downs") var downs: Int?,
    @Json(name = "author_flair_css_class") var authorFlairCssClass: String?,
    @Json(name = "is_submitter") var isSubmitter: Boolean?,
    @Json(name = "collapsed") var collapsed: Boolean?,
    //@Json(name = "author_flair_richtext"           ) var authorFlairRichtext          : List<String>,
    @Json(name = "author_patreon_flair") var authorPatreonFlair: Boolean?,
    @Json(name = "body_html") var bodyHtml: String?,
    @Json(name = "gildings") var gildingsDto: GildingsDto?,
    @Json(name = "collapsed_reason") var collapsedReason: String?,
    @Json(name = "distinguished") var distinguished: String?,
    @Json(name = "associated_award") var associatedAward: String?,
    @Json(name = "stickied") var stickied: Boolean?,
    @Json(name = "author_premium") var authorPremium: Boolean?,
    @Json(name = "can_gild") var canGild: Boolean?,
    @Json(name = "link_id") var linkId: String?,
    @Json(name = "unrepliable_reason") var unrepliableReason: String?,
    @Json(name = "author_flair_text_color") var authorFlairTextColor: String?,
    @Json(name = "score_hidden") var scoreHidden: Boolean?,
    @Json(name = "permalink") var permalink: String?,
    @Json(name = "subreddit_type") var subredditType: String?,
    @Json(name = "link_permalink") var linkPermalink: String?,
    @Json(name = "name") var name: String?,
    @Json(name = "author_flair_template_id") var authorFlairTemplateId: String?,
    @Json(name = "subreddit_name_prefixed") var subredditNamePrefixed: String?,
    @Json(name = "author_flair_text") var authorFlairText: String?,
    @Json(name = "treatment_tags") var treatmentTags: List<String>?,
    @Json(name = "created") var created: Int?,
    @Json(name = "created_utc") var createdUtc: Int?,
    @Json(name = "awarders") var awarders: List<String>?,
    //@Json(name = "all_awardings"                   ) var allAwardings                 : List<String>,
    @Json(name = "locked") var locked: Boolean?,
    @Json(name = "author_flair_background_color") var authorFlairBackgroundColor: String?,
    @Json(name = "collapsed_because_crowd_control") var collapsedBecauseCrowdControl: String?,
    @Json(name = "mod_reports") var modReports: List<String>?,
    @Json(name = "quarantine") var quarantine: Boolean?,
    @Json(name = "mod_note") var modNote: String?,
    @Json(name = "link_url") var linkUrl: String?
)

@JsonClass(generateAdapter = true)
data class RepliesDto(
    @Json(name = "kind") val kind: String?,
    @Json(name = "data") val data: DataDto?
)

