package ke.co.banit.instagramprofile.data

import androidx.annotation.DrawableRes


data class ProfileStat(
    val title: String,
    val stat: String,
)

data class People(
    val name: String,
    @DrawableRes val profilePicture: Int,
    val connection: String
)