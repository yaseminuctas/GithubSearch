package com.yaseminuctas.githubsearch.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepoNameResponse {

    @SerializedName("items")
    var items: List<ItemRepoName>? = emptyList()


    data class ItemRepoName(
        @SerializedName("id")
        var id: Int? = 0,

        @SerializedName("name")
        var name: String? = "",

        @SerializedName("full_name")
        var fullName: String? = "",

        @SerializedName("description")
        @Expose
        var description: String? = ""
    )
}

