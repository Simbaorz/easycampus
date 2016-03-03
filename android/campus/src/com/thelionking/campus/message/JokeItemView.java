package com.thelionking.campus.message;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;

/**
 * Created by the lion king on 2014/9/16.
 * adapter会复用该view，因此每次setJoke都调用refreshData方法，构造方法不会初始化Joke数据
 * 统一放到setJoke里
 */
public class JokeItemView extends FrameLayout{

    public static final String TAG = "JokeItemView";

    /**
     * view的句柄，方便使用
     */
    private ViewHolder holder;

    /**
     * 此view对应的数据
     */
    private Joke joke;

    public void setJoke(Joke joke){
        this.joke = joke;
        refreshDataAndView();
    }

    public Joke getJoke(){
        return this.joke;
    }

    public JokeItemView(Context context) {
        super(context);
        initView();
    }

    public JokeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public JokeItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        View root = inflate(getContext(), R.layout.joke_view_item, null);
        addView(root);
        holder = new ViewHolder();
        holder.contentTextView = (TextView)root.findViewById(R.id.content_text_view);
        holder.authorTextView = (TextView)root.findViewById(R.id.author_text_view);
        holder.contentImageView = (ImageView)root.findViewById(R.id.content_image_view);
//        holder.firstCommentTextView = (TextView)findViewById(R.id.first_comment_text_view);
//        holder.secondCommentTextView = (TextView)findViewById(R.id.second_comment_text_view);
        holder.timeTextView = (TextView)findViewById(R.id.time_text_view);

        holder.contentLoadingView = (TextView)findViewById(R.id.content_loading_view);
    }

    private static class ViewHolder {
        //主要内容
        TextView contentTextView;
        TextView authorTextView;
        TextView timeTextView;
        ImageView contentImageView;
//        TextView firstCommentTextView;
//        TextView secondCommentTextView;
        //for test
        TextView contentLoadingView;
    }

    private void refreshDataAndView() {
        if(joke == null) {
            Log.d(TAG, "have not inited a joke");
            return;
        }
        if(!joke.getContent().equals("")){
            holder.contentTextView.setText(Html.fromHtml(joke.getContent()));
            holder.contentTextView.setVisibility(View.VISIBLE);
        }else{
            holder.contentTextView.setVisibility(View.GONE);
        }
        holder.authorTextView.setText(joke.getAuthor());
        holder.timeTextView.setText(CommonUtil.getDate(joke.getDate()));

        //根据Imgurl的状态来设置Picture
        if(joke.getImgurl() == null || joke.getImgurl().equals("NULL") || joke.getImgurl().equals("")){
            holder.contentImageView.setVisibility(View.GONE);
            holder.contentLoadingView.setVisibility(View.GONE);
        }else{
            holder.contentImageView.setVisibility(View.VISIBLE);
            if(MainApp.getInstance().getImageManager().containPicture(joke.getImgurl())){
                holder.contentImageView.setImageBitmap(MainApp.getInstance().getImageManager().getPicture(joke.getImgurl()));
                Log.d(TAG, "picture in memory");
            }else{
                Bitmap bitmap = MainApp.getInstance().getImageManager().getPictureFromCache(joke.getImgurl());
                if(bitmap == null){
                    holder.contentImageView.setVisibility(View.GONE);
                    holder.contentLoadingView.setVisibility(View.VISIBLE);
                    MainApp.getInstance().getImageManager().addToWaitQueue(joke.getImgurl());
                }else{
                    holder.contentImageView.setVisibility(View.VISIBLE);
                    holder.contentLoadingView.setVisibility(View.GONE);
                    holder.contentImageView.setImageBitmap(bitmap);
                }
            }
        }

    }
}
