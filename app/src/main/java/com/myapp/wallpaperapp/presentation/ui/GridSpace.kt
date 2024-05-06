package com.myapp.wallpaperapp.presentation.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class GridSpace( //    NOTE, ITEMDECORATION IS THE CLASS IN THE RECYCLERVIEW CLASS THAT HELPS IN CUSTOMIZATION OF THE LAYOUT
    //    LIKE WE'RE DOING HERE, WE CAN DO WHATEVER WE WANT WITH THIS, WITH OUR LAYOUT, BY CALCULATING AND SHIT!
    private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean
) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position

//    int column = position % spanCount; calculates the column index of the current item being drawn in the grid. Here,
//    position is the position of the current item in the adapter, and spanCount is the number of columns in the grid layout.
//
//    The % operator in Java returns the remainder of dividing position by spanCount. For example, if spanCount is 3
//    and position is 5, then position % spanCount would be 2 because the remainder of dividing 5 by 3 is 2.
//
//    By calculating the column index of the item, the ItemDecoration can apply the
//    appropriate spacing for the item based on its position within the grid.
        val column = position % spanCount // item column

//     Rect is a class in Android that represents a rectangle with integer coordinates. In the getItemOffsets() method
//     of RecyclerView.ItemDecoration, the outRect parameter is used to define the offset of the item view's bounds from
//     its parent view. The method modifies the outRect object to add padding/margin to the item view's bounds based on the
//     item's position, column, and the values of the spanCount, spacing, and includeEdge parameters.
//
//     RecyclerView.State is a class in Android that represents the current state of the RecyclerView, such as whether it is
//     currently being scrolled, whether it has focus, etc. The state parameter of getItemOffsets() method is used to get the
//     current state of the RecyclerView.
        if (includeEdge) {
            outRect.left =
                spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}
