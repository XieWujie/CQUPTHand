package com.example.xiewujie.cqupthand.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QaQuestionResolve {
    public static List<QaHeadQuestion> resolveHeadData(JSONObject object)throws Exception {
        List<QaHeadQuestion> list = new ArrayList<>();
        JSONArray array = object.getJSONArray("data");
        for (int i = 0;i<array.length();i++){
            JSONObject js = array.getJSONObject(i);
            QaHeadQuestion question = new QaHeadQuestion();
            question.setTitle(js.getString("title"));
            question.setDescription(js.getString("description"));
            question.setKind(js.getString("kind"));
            question.setGender(js.getString("gender"));
            question.setAnswer_num(js.getString("answer_num"));
            question.setCreated_at(js.getString("created_at"));
            question.setDisappear_at(js.getString("disappear_at"));
            question.setReward(js.getString("reward"));
            question.setPhoto_thumbnail_src(js.getString("photo_thumbnail_src"));
            question.setId(js.getString("id"));
            list.add(question);
        }
        return list;
    }
}
