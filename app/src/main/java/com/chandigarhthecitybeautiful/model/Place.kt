package com.chandigarhthecitybeautiful.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.chandigarhthecitybeautiful.R

@Entity(tableName = "places")
data class Place(
    @field:PrimaryKey val id:Int,
    val name:String,
    val description:String,
    val distance:String,
    val timing:String,
    val image:String
) {
    companion object {
        /**
         * A static method that loads image using glide into the ImageView
         * @param view ImageView to display the image
         * @param imageUrl URL of the image
         */
        @JvmStatic
        @BindingAdapter("placeImage")
        fun loadPlaceImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context).load("http://xdeveloper.esy.es/chd/images/$imageUrl").fitCenter()
                .into(view)
        }
    }
}