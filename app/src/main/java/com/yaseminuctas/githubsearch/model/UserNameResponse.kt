package com.yaseminuctas.githubsearch.model

import com.google.gson.annotations.SerializedName

class UserNameResponse {
    @SerializedName("total_count")
    var totalCount: Int? = 0

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = false

    @SerializedName("items")
    var items: List<Item>? = emptyList()

    data class Item(
        @SerializedName("login") var login: String? = null,

        @SerializedName("id") var id: Int? = null,

        @SerializedName("node_id") var nodeId: String? = null,

        @SerializedName("avatar_url") var avatarUrl: String? = null,

        @SerializedName("gravatar_id") var gravatarId: String? = null,

        @SerializedName("url") var url: String? = null,

        @SerializedName("html_url") var htmlUrl: String? = null,

        @SerializedName("repos_url") var reposUrl: String? = null
    )
}


