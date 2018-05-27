package com.example.xiewujie.cqupthand.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.activity.CurriculumActivity;
import com.example.xiewujie.cqupthand.activity.FindActivity;
import com.example.xiewujie.cqupthand.activity.MineActivity;
import com.example.xiewujie.cqupthand.activity.QAHomeActivity;

public class BottomFragment extends Fragment implements View.OnClickListener {
    private ImageView curriculumView;
    private TextView curriculumText;
    private ImageView communityView;
    private TextView communityText;
    private ImageView findView;
    private TextView findText;
    private ImageView mineView;
    private TextView mineText;
    private View view;
    private String activityName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_layout,container,false);
        curriculumView = (ImageView)view.findViewById(R.id.curriculum_view);
        curriculumText = (TextView)view.findViewById(R.id.curriculum_text);
        communityView = (ImageView)view.findViewById(R.id.community_view);
        communityText = (TextView)view.findViewById(R.id.community_text);
        findView = (ImageView)view.findViewById(R.id.find_view);
        findText = (TextView)view.findViewById(R.id.find_text);
        mineView = (ImageView)view.findViewById(R.id.mine_view);
        mineText = (TextView)view.findViewById(R.id.mine_text);
        curriculumView.setOnClickListener(this);
        communityView.setOnClickListener(this);
        findView.setOnClickListener(this);
        mineView.setOnClickListener(this);
        activityName = this.getActivity().getLocalClassName();
        initView();
        return view;
    }
    private void initView(){
        if (activityName.equals("activity.CurriculumActivity")){
            curriculumView.setSelected(true);
            curriculumText.setVisibility(View.VISIBLE);
        }else if (activityName.equals("activity.QAHomeActivity")){
            communityView.setSelected(true);
            communityText.setVisibility(View.VISIBLE);
         }else if (activityName.equals("activity.FindActivity")){
        findView.setSelected(true);
        findText.setVisibility(View.VISIBLE);
     }else if (activityName.equals("activity.MineActivity")){
        mineView.setSelected(true);
        mineText.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        curriculumView.setSelected(false);
        curriculumText.setVisibility(View.GONE);
        communityView.setSelected(false);
        communityText.setVisibility(View.GONE);
        findView.setSelected(false);
        findText.setVisibility(View.GONE);
        mineView.setSelected(false);
        mineText.setVisibility(View.GONE);
        switch (v.getId()){
            case R.id.curriculum_view:
                v.setSelected(true);
                curriculumText.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                if (!this.getActivity().getLocalClassName().equals("activity.CurriculumActivity")) {
                    intent.setClass(getActivity(), CurriculumActivity.class);
                    startActivity(intent);
                }
                Log.d("BottomFragment",this.getActivity().getLocalClassName());
                break;
            case R.id.community_view:
                v.setSelected(true);
                communityText.setVisibility(View.VISIBLE);
                if (!this.getActivity().getLocalClassName().equals("activity.QAHomeActivity")) {
                    Intent intentQA = new Intent();
                    intentQA.setClass(getActivity(), QAHomeActivity.class);
                    startActivity(intentQA);
                }
                break;
            case R.id.find_view:
                v.setSelected(true);
                findText.setVisibility(View.VISIBLE);
                if (!this.getActivity().getLocalClassName().equals("activity.FindActivity")) {
                    Intent intentFind = new Intent();
                    intentFind.setClass(getActivity(), FindActivity.class);
                    startActivity(intentFind);
                }
                break;
            case R.id.mine_view:
                v.setSelected(true);
                mineText.setVisibility(View.VISIBLE);
                if (!this.getActivity().getLocalClassName().equals("activity.MineActivity")) {
                    Intent intentMe = new Intent();
                    intentMe.setClass(getActivity(), MineActivity.class);
                    startActivity(intentMe);
                }
                break;
                default:
                    break;
        }
    }
}
