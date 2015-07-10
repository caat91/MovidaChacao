package com.reto.chacao.postdetail.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.beans.DetailPost;
import com.reto.chacao.beans.Post;
import com.reto.chacao.beans.PostComment;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.model.Comment;
import com.reto.chacao.model.Event;
import com.reto.chacao.postdetail.adapter.DetailPostAdapter;
import com.reto.chacao.postdetail.holder.DetailPostCommentsHolder;
import com.reto.chacao.postdetail.holder.DetailPostMainHolder;
import com.reto.chacao.util.AppUtil;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo Luttinger on 27/05/2015.
 */
public class PostDetailScreenFragment extends AppFragment implements View.OnClickListener {

    public static final String TAG = "PostDetailScreen";

    private static RecyclerView mRecyclerView;

    private DetailPostAdapter mDetailPostAdapter;

    private Button mBuyButton;

    private static RelativeLayout mChatContainer;

    private static EditText mDetailCommentChatText;

    private Button mDetailCommentChatSendButton;

    private ImageView mAddCommentButton;

    private ImageView mBackButton;

    private ToggleButton mFlagButton;

    private List<DetailPost> mListDetailPostItems;

    private List<PostComment> mPostCommentList;

    private static Context sContext;

    private Post mPost;

    private Event mPostEvent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle data = this.getArguments();



        //Desde la otra vista pasan un objeto post serializable por eso se hace asi

        mPost = (Post) data.getSerializable(Post.TAG);
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        mPostEvent = dbHelper.getEvent(mPost.getPost_id());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        List<Comment> cm = dbHelper.getCommentByEvent(seria.getPost_id());
//        DetailPostCommentsHolder delta = new DetailPostCommentsHolder(inflater.inflate(R.layout.fragment_post_detail_comment, container, false),cm);

        //TODO: esto es data de prueba, quitar y hacer el llamado al API para recuperar el detalle y los comentarios
//        TextView mPostUserName = (TextView) postDetailView.findViewById(R.id.commentOwnerText);
//        mPostUserName.setText('h');

//
//        View postDetailView2 = inflater.inflate(R.layout.fragment_post_detail, container, false);
//        TextView mPostUserName2 = (TextView) postDetailView2.findViewById(R.id.commentOwnerText);
//        mPostUserName2.setText("dfg");

        View postDetailView = inflater.inflate(R.layout.fragment_post_detail_screen, container, false);

        setViews(postDetailView);

        mListDetailPostItems = new ArrayList<>(testGenerateDetailPost());




        mPostCommentList = new ArrayList<>(testGeneratePostComment());


        mDetailPostAdapter = new DetailPostAdapter(getActivity(), mListDetailPostItems, mPostCommentList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mDetailPostAdapter);

        sContext = getActivity();

        if ( data.getBoolean("COMMENT") ) {
            Log.w(TAG,"COMMENT");
            mRecyclerView.scrollToPosition(1);
        }

        return postDetailView;
    }


    private List<DetailPost> testGenerateDetailPost() {

        List<DetailPost> list = new ArrayList<>();

        DetailPost post = new DetailPost();
        post.setPostOwnerComment(mPost.getDescription());
        post.setPostOwnerName(mPost.getTitle());

        int id = getActivity().getResources().getIdentifier(mPostEvent.getPhoto(), "drawable",
                getActivity().getApplicationContext().getPackageName());

        post.setPostImages(new int[]{id,R.drawable.css_forum2,R.drawable.css_forum3});

        list.add(post);

        return list;
    }

    private List<PostComment> testGeneratePostComment() {

        List<PostComment> list = new ArrayList<>();

        PostComment comment = new PostComment();
        list.add(comment);

        comment = new PostComment();
        list.add(comment);

        return list;
    }


    private void setViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.reciclerView);
        mBuyButton = (Button) view.findViewById(R.id.detailPostBuyButton);
        mBuyButton.setOnClickListener(this);
        mChatContainer = (RelativeLayout) view.findViewById(R.id.detailPostchatLayout);
        mDetailCommentChatText = (EditText) view.findViewById(R.id.addCommentText);
        mDetailCommentChatSendButton = (Button) view.findViewById(R.id.sendCommentButton);
        mDetailCommentChatSendButton.setOnClickListener(this);
        mAddCommentButton = (ImageView) view.findViewById(R.id.fragment_toolbar_comment_button);
        mAddCommentButton.setOnClickListener(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public static void showChat(Context context) {
        mRecyclerView.smoothScrollToPosition(mRecyclerView.getBottom());
        mChatContainer.setVisibility(View.VISIBLE);
        mDetailCommentChatText.requestFocus();
        mDetailCommentChatText.setText("");
        AppUtil.showKeyboard(context, mDetailCommentChatText);
    }

    private void addDetailPostComment() {

        if (!"".equals(mDetailCommentChatText.getText().toString())) {

            AppUtil.hideKeyboard(getActivity(), mDetailCommentChatText);

            PostComment comment = new PostComment();
            comment.setComment(mDetailCommentChatText.getText().toString());

            mDetailPostAdapter.addItemToRecyclerView(comment);
            mDetailPostAdapter.notifyDataSetChanged();

            mChatContainer.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fragment_toolbar_comment_button:
                showChat(getActivity());
                break;
            case R.id.sendCommentButton:
                addDetailPostComment();
                break;
            case R.id.detail_top_toolabar_back_button :
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public String getMyTag() {
        return TAG;
    }

    @Override
    protected AppFragmentListener getFragmentListener() {
        return (MovidaMainActivity) getActivity();
    }

    @Override
    protected boolean onBackPressed() {
        boolean flag = true;
        if (mChatContainer.getVisibility() == View.VISIBLE) {
            mChatContainer.setVisibility(View.GONE);
            flag = false;
        }
        return flag;
    }


}
