package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedsContract;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by studio08 on 5/4/2016.
 * In order to understand this, you must first understand how BaseAdapter works, since CursorAdapter is a subclass of BaseAdapter.

    Android maintains a pool of views for a ListView which it will give to you so you can reuse it instead of creating a new view each time.

    In BaseAdapter, you will have a function called getView(), to which one of the parameters is a View object named convertView.
    Basically, this convertView will be null if the list is being loaded for the first time, and it will not be null once you start sliding the list.
    Therefore, in the getView() method of your BaseAdapter, you will check if convertView is null. If yes, you will inflate it.
    Then you can use the view and set its elements as normal. This will improve the scrolling performance of a listview tremendously.

    A CursorAdapter makes it easy to use when the data source of a listview is a database.
    In a cursor adapter, however, Android takes care of checking whether the convertView is null or not.
    In the newView() method, you simply inflate the view and return it.
    In the bindView() method, you set the elements of your view.

    As an example, imagine a listview on a device which can show upto 11 list items on the screen.
    In this case, newView() will be called upto 11 times.
    However, bindView() will be called many times whenever you scroll the list view.
    The 11 views you created in your newView method will be reused again and again as you scroll the list.
 */
public class FeedCursorAdapter extends SimpleCursorAdapter {

    public FeedCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    static public FeedCursorAdapter FeedCursorAdapterFactory(Context context, Cursor c) {
        String[] from = {FeedsContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedsContract.FeedEntry.COLUMN_NAME_CREATOR};
        int[] to = {R.id.title_textView,
                R.id.creator_textView};
        int layout = R.layout.main_row;
        int flags = CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
        return new FeedCursorAdapter(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // view: Existing view, returned earlier by newView
        // cursor: The cursor from which to get the data. The cursor is already moved to the correct position.
        super.bindView(view, context, cursor);

        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail_imageView);
         String imageHref = cursor.getString(cursor.getColumnIndex(FeedsContract.FeedEntry.COLUMN_NAME_THUMBNAIL));
        // This cursor is from the projection in the cursor of FeedSelectorFragment
         Picasso.with(context).
                 load(imageHref).
                 transform(new CropSquareTransformation()).
                 placeholder(R.drawable.ic_image_black_24dp).
                 error(R.drawable.ic_broken_image_black_24dp).
                 into(imageView);
    }

    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() { return "square()"; }
    }
}
